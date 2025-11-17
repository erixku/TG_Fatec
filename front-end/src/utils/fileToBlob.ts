export async function fileUriToBlob(uri: string, mime = 'image/jpeg'): Promise<any> {
  let RNFB: any = null;
  try {
    // lazy require — may fail in Expo Go or Metro analysis
    // eslint-disable-next-line @typescript-eslint/no-var-requires
    RNFB = require('react-native-blob-util');
  } catch (e) {
    RNFB = null;
  }

  try {
    // 1) Preferred: use react-native-blob-util polyfill if available
    if (RNFB && RNFB.fs && typeof RNFB.fs.readFile === 'function') {
      const base64 = await RNFB.fs.readFile(uri, 'base64');
      const BlobPolyfill = RNFB.polyfill && RNFB.polyfill.Blob;
      if (BlobPolyfill && typeof BlobPolyfill.build === 'function') {
        const blob = await BlobPolyfill.build(base64, { type: mime, encoding: 'base64' });
        return blob;
      }
      // Fallback: convert base64 to bytes and build standard Blob if available
      if (typeof Blob === 'function') {
        const binary = (global as any).atob(base64);
        const len = binary.length;
        const bytes = new Uint8Array(len);
        for (let i = 0; i < len; i++) bytes[i] = binary.charCodeAt(i);
        return new Blob([bytes], { type: mime });
      }
    }

    // 2) Fallback: try fetch -> arrayBuffer (works for file:// and http(s) URIs in many environments)
    try {
      const resp = await fetch(uri);
      if (!resp.ok) throw new Error(`fetch(${uri}) returned ${resp.status}`);
      const buffer = await resp.arrayBuffer();
      if (typeof Blob === 'function') {
        return new Blob([buffer], { type: mime });
      }
    } catch (fetchErr) {
      // continue to error below
      // eslint-disable-next-line no-console
      console.warn('fetch fallback for fileUriToBlob failed:', fetchErr);
    }

    throw new Error('No Blob implementation available');
  } catch (err) {
    // eslint-disable-next-line no-console
    console.error('Erro ao criar Blob:', err);
    throw err;
  }
}
