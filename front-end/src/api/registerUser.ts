import { buildUserPayload, RegisterUserFormData } from "@/schemas/registerUserSchema";

export const registerUser = async (data: RegisterUserFormData) => {
  const { arquivo, ...userData } = data;
  const userPayload = buildUserPayload(userData);

  const body = new FormData();
  body.append("user_data", JSON.stringify(userPayload));

  if (arquivo?.caminho) {
    const photoName = arquivo.bucketArquivo?.nome || "profile.jpg";
    const mime = arquivo.mimeType || "image/jpeg";

    try {
      const module = await import("../utils/fileToBlob");
      const blob = await module.fileUriToBlob(arquivo.caminho, mime);
      // Ensure blob has a type field (some polyfills may omit it)
      try { if (!blob.type) (blob as any).type = mime; } catch {}
      // Append the Blob object (runtime will populate Content-Type for the part)
      body.append("profile_photo", blob as any, photoName);
      // debug
      // eslint-disable-next-line no-console
      console.log('Attached profile_photo as Blob:', photoName, mime);
    } catch (error) {
      // If Blob creation fails (e.g. in Expo Go where native module isn't available),
      // fallback to the RN-friendly file descriptor object { uri, name, type }.
      // We cast to `any` to satisfy TypeScript/FormData typing in React Native.
      // This keeps uploads working in environments without native Blob support.
      // eslint-disable-next-line no-console
      console.warn('Erro ao criar Blob, usando fallback {uri,name,type}:', error);
      const filePart: any = {
        uri: arquivo.caminho,
        name: photoName,
        type: mime,
      };
      body.append('profile_photo', filePart as any);
      // debug
      // eslint-disable-next-line no-console
      console.log('Attached profile_photo as {uri,name,type}:', filePart);
    }
  } else {
    body.append("profile_photo", "");
  }

  try {
    const response = await fetch(
      "https://harppia-endpoints.onrender.com/v1/users/register",
      {
        method: "POST",
        body,
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    return await response.json();
  } catch (error) {
    console.error("Erro ao registrar usuário:", error);
    throw error;
  }
};
