// ...existing code...
// nota: antes havia uma função buildUserPayload que não existe neste projeto;
// vamos usar os valores do formulário diretamente e normalizá-los abaixo.
import { RegisterUserFormData } from '@/schemas/registerUserSchema';
import { fileUriToBlob } from '@/utils/fileToBlob';
import * as FileSystem from 'expo-file-system';

const UPLOAD_URL = 'https://harppia-endpoints.onrender.com/v1/users/register';

// Normalize payload: remove undefined, convert Date -> ISO string
const normalizeUserPayload = (obj: Record<string, any>): Record<string, any> => {
  const out: Record<string, any> = {};
  Object.entries(obj).forEach(([k, v]) => {
    if (v === undefined) return;
    if (v === null) { out[k] = null; return; }
    if (v instanceof Date) { out[k] = v.toISOString(); return; }
    if (typeof v === 'object' && !Array.isArray(v)) {
      out[k] = normalizeUserPayload(v as Record<string, any>);
      return;
    }
    out[k] = v;
  });
  return out;
};

// Utility: send only user_data as JSON (no file, no Blob conversion)
export const registerUserDry = async (data: RegisterUserFormData): Promise<any> => {
  const { arquivo, ...userData } = data;
  const userPayload = normalizeUserPayload(userData as Record<string, any>);
  const json = JSON.stringify(userPayload);

  // helper to base64 encode string in various runtimes
  const base64Encode = (s: string) => {
    if (typeof btoa === 'function') return btoa(s);
    try {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      return (global as any).Buffer.from(s, 'utf8').toString('base64');
    } catch {
      return '';
    }
  };

  // Try multipart via Blob (preferred) or data-uri file fallback so server receives multipart
  try {
    const fd = new FormData();

    if (typeof Blob === 'function') {
      const blob = new Blob([json], { type: 'multipart/form-data' });
      fd.append('user_data', blob as any, 'user_data.json');
    } else {
      // fallback: data URI as file descriptor (React Native style)
      const b64 = base64Encode(json);
      if (!b64) throw new Error('Base64 encode not available');
      const dataUri = `data:multipart/form-data;base64,${b64}`;
      fd.append('user_data', { uri: dataUri, name: 'user_data.json', type: 'multipart/form-data' } as any);
    }

    // keep profile_photo field present but empty to match endpoint expectations
    fd.append('profile_photo', '');

    const res = await fetch(UPLOAD_URL, { method: 'POST', body: fd });
    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`HTTP error! status: ${res.status}; body: ${text}`);
    }
    return res.json();
  } catch (err) {
    console.warn('Could not send multipart for user_data, falling back to JSON POST:', err);
    // last-resort: send multipart/form-data (keeps previous behavior)
    const res = await fetch(UPLOAD_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'multipart/form-data' },
      body: JSON.stringify({ user_data: userPayload }),
    });
    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`HTTP error! status: ${res.status}; body: ${text}`);
    }
    return res.json();
  }
};

export const registerUser = async (data: RegisterUserFormData): Promise<any> => {
  const { arquivo, ...userData } = data;
  const userPayload = normalizeUserPayload(userData as Record<string, any>);

  // Helper para enviar FormData via fetch
  const sendFormData = async (form: FormData) => {
    const res = await fetch(UPLOAD_URL, { method: 'POST', body: form });
    if (!res.ok) {
      const text = await res.text().catch(() => '');
      throw new Error(`HTTP error! status: ${res.status}; body: ${text}`);
    }
    return res.json();
  };

  // Sem arquivo -> enviar apenas user_data
  if (!arquivo?.caminho) {
    const fd = new FormData();
    fd.append('user_data', JSON.stringify(userPayload));
    fd.append('profile_photo', '');
    return await sendFormData(fd);
  }

  const fileUri = arquivo.caminho;
  const mime = arquivo.mimeType || 'image/jpeg';
  const filename = arquivo.bucketArquivo?.nome || fileUri.split('/').pop() || 'photo.jpg';

  // 1) Tentar criar Blob (fileUriToBlob)
  try {
    const blob = await fileUriToBlob(fileUri, mime);

    console.log('Runtime Blob support:', typeof Blob === 'function');
    console.log('Created blob instanceof Blob:', blob instanceof Blob, 'type:', (blob as any).type, 'size:', (blob as any).size);

    if (typeof Blob === 'function' && blob instanceof Blob) {
      // garante tipo
      if (!((blob as any).type)) {
        try { (blob as any).type = mime; } catch {}
      }

      const fd = new FormData();
      fd.append('user_data', JSON.stringify(userPayload));
      // append com filename para forçar envio correto
      fd.append('profile_photo', blob as any, filename);
      return await sendFormData(fd);
    } else {
      throw new Error('Blob not usable in this runtime');
    }
  } catch (err) {
    console.warn('Blob path failed or not supported:', err);
  }

  // 2) Fallback: expo-file-system.uploadAsync (nativo, monta multipart)
  try {
    if (FileSystem && typeof FileSystem.uploadAsync === 'function') {
      console.debug('Attempting FileSystem.uploadAsync fallback (native multipart)');
      const params: Record<string, string> = { user_data: JSON.stringify(userPayload) };

      const uploadResult = await FileSystem.uploadAsync(
        UPLOAD_URL,
        fileUri,
        {
          httpMethod: 'POST',
          uploadType: (FileSystem as any).FileSystemUploadType?.MULTIPART ?? 'multipart',
          fieldName: 'profile_photo',
          mimeType: mime,
          parameters: params,
        }
      );

      if (uploadResult.status >= 200 && uploadResult.status < 300) {
        try { return JSON.parse(uploadResult.body); } catch { return { status: uploadResult.status, body: uploadResult.body }; }
      } else {
        throw new Error(`uploadAsync failed: ${uploadResult.status} - ${uploadResult.body}`);
      }
    } else {
      console.debug('expo-file-system.uploadAsync not available');
    }
  } catch (err) {
    console.warn('uploadAsync unavailable or failed:', err);
  }

  // 3) Último fallback: FormData com { uri, name, type }
  try {
    console.debug('Falling back to RN-style file descriptor (uri,name,type)');
    const fd2 = new FormData();
    fd2.append('user_data', JSON.stringify(userPayload));
    fd2.append('profile_photo', { uri: fileUri, name: filename, type: mime } as any);
    return await sendFormData(fd2);
  } catch (err) {
    console.error('All upload methods failed:', err);
    throw err;
  }
};

// Utility: send user_data as a Blob inside multipart/form-data (no image file)
export const registerUserDryAsBlob = async (data: RegisterUserFormData): Promise<any> => {
  const { arquivo, ...userData } = data;
  const userPayload = normalizeUserPayload(userData as Record<string, any>);
  const json = JSON.stringify(userPayload);

  try {
    if (typeof Blob === 'function') {
      const blob = new Blob([json], { type: 'multipart/form-data' });
      const fd = new FormData();
      // append the JSON as a file-like blob so server receives proper Content-Type
      fd.append('user_data', blob as any, 'user_data.json');
      // append empty profile_photo to keep same multipart shape as full upload
      fd.append('profile_photo', '');

      const res = await fetch(UPLOAD_URL, { method: 'POST', body: fd });
      if (!res.ok) {
        const text = await res.text().catch(() => '');
        throw new Error(`HTTP error! status: ${res.status}; body: ${text}`);
      }
      return res.json();
    } else {
      console.warn('Blob not available in this runtime; falling back to JSON POST');
      return await registerUserDry(data);
    }
  } catch (err) {
    console.warn('registerUserDryAsBlob failed, falling back to JSON POST:', err);
    return await registerUserDry(data);
  }
};
// ...existing code...