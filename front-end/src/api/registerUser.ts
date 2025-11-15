import { buildUserPayload, RegisterUserFormData } from "@/schemas/registerUserSchema";
import axios from "axios";

export const registerUser = async (data: RegisterUserFormData) => {
  const { arquivo, ...userData } = data;
  const userPayload = buildUserPayload(userData);

  const body = new FormData();

  // envia JSON como campo de texto puro
  body.append("user_data", JSON.stringify(userPayload));

  // Foto, se existir
  if (arquivo?.caminho) {
    const photoName = arquivo.bucketArquivo?.nome || "profile.jpg";

    const file = {
      uri: arquivo.caminho,
      type: arquivo.mimeType || "image/jpeg",
      name: photoName,
    } as any;

    body.append("profile_photo", file);
  }

  const response = await axios({
    url: "https://harppia-endpoints.onrender.com/v1/users/register",
    method: "POST",
    data: body,
    headers: {
      Accept: "application/json",
      // IMPORTANTE: não definir Content-Type
      // Deixe o Axios gerar o boundary automaticamente
    },
    transformRequest: (data) => data, // <- ESSENCIAL NO EXPO/HERMES
  });

  return response.data;
};
