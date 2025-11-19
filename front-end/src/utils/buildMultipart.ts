export async function buildMultipartBody(
  userPayload: any,
  photoBlob: Blob | null,
  photoName: string,
  mime: string
): Promise<{ body: Blob; contentType: string }> {
  const boundary = 'RN_BOUNDARY_' + Date.now();
  const crlf = '\r\n';
  const parts: any[] = [];

  // user_data part
  parts.push(`--${boundary}${crlf}`);
  parts.push('Content-Disposition: form-data; name="user_data"' + crlf + crlf);
  parts.push(JSON.stringify(userPayload) + crlf);

  if (photoBlob) {
    parts.push(`--${boundary}${crlf}`);
    parts.push(
      `Content-Disposition: form-data; name="profile_photo"; filename="${photoName}"` + crlf
    );
    parts.push(`Content-Type: ${mime}` + crlf + crlf);
    parts.push(photoBlob);
    parts.push(crlf);
  } else {
    // empty profile_photo field
    parts.push(`--${boundary}${crlf}`);
    parts.push('Content-Disposition: form-data; name="profile_photo"' + crlf + crlf);
    parts.push('' + crlf);
  }

  parts.push(`--${boundary}--${crlf}`);

  const body = new Blob(parts as any[]);
  const contentType = `multipart/form-data; boundary=${boundary}`;
  return { body, contentType };
}
