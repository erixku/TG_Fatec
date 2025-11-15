import axios from 'axios';

/**
 * Encontra um usuário na API usando uma chave (email, telefone ou CPF).
 * @param key A chave de busca (email, telefone ou CPF).
 * @returns Os dados do usuário em caso de sucesso.
 * @throws Lança um erro em caso de falha na requisição.
 */
export const findUserByKey = async (key: string) => {
  const response = await axios.get(`https://harppia-endpoints.onrender.com/v1/users/find?key=rafaelcosta@yahoo.com`);
  return response.data;
};