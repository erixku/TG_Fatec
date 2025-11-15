export type ChurchType = {
  uuid: string;
  created_at: string; // ISO date string (TIMESTAMPTZ)
  updated_at: string; // ISO date string (TIMESTAMPTZ)
  deleted_at: string | null;

  created_by_adm: string;
  updated_by_adm: string;
  deleted_by_adm: string | null;

  is_deleted: boolean;
  cnpj: string;
  nome: string;
  denominacao: string; // enum utils.enum_s_church_t_tb_igreja_c_denominacao
  outra_denominacao: string | null;

  s_storage_t_tb_arquivo_c_foto: string;
  s_auth_t_tb_usuario_c_adm_proprietario: string;
};

export const itensChurch = [
  {
    "uuid": "f4a1d6b8-7c3d-49e1-8a91-c4d71b2a2a54",
    "created_at": "2025-10-05T09:15:00.000Z",
    "updated_at": "2025-10-05T09:15:00.000Z",
    "deleted_at": null,
    "created_by_adm": "a1b2c3d4-e5f6-47a8-9b0c-1d2e3f4a5b6c",
    "updated_by_adm": "a1b2c3d4-e5f6-47a8-9b0c-1d2e3f4a5b6c",
    "deleted_by_adm": null,
    "is_deleted": false,
    "cnpj": "12345678000190",
    "nome": "Igreja Batista Central de Esperança",
    "denominacao": "BATISTA",
    "outra_denominacao": null,
    "s_storage_t_tb_arquivo_c_foto": "a8d2b6e7-5c4d-43b2-9f1c-8e7a3d4b9e6a",
    "s_auth_t_tb_usuario_c_adm_proprietario": "f3a2b1c4-8d9e-47f0-9a2b-c5d6e7f8a9b0"
  },
  {
    "uuid": "e8c1a5d7-9b4e-4c3a-a891-f7e2b5c2a0f1",
    "created_at": "2025-10-05T09:20:00.000Z",
    "updated_at": "2025-10-05T09:20:00.000Z",
    "deleted_at": null,
    "created_by_adm": "b7c8d9e0-f1a2-43b4-9c5d-6e7f8a9b0c1d",
    "updated_by_adm": "b7c8d9e0-f1a2-43b4-9c5d-6e7f8a9b0c1d",
    "deleted_by_adm": null,
    "is_deleted": false,
    "cnpj": "98765432000145",
    "nome": "Comunidade Jovem Ágape",
    "denominacao": "PENTECOSTAL",
    "outra_denominacao": null,
    "s_storage_t_tb_arquivo_c_foto": "e9c8b7a6-4d5e-47f9-8a0b-1c2d3e4f5a6b",
    "s_auth_t_tb_usuario_c_adm_proprietario": "a5b6c7d8-e9f0-4a1b-b2c3-d4e5f6a7b8c9"
  },
  {
    "uuid": "b6d1c2e3-4f5a-4b7d-8a9c-1e2f3d4b5a6c",
    "created_at": "2025-10-05T09:25:00.000Z",
    "updated_at": "2025-10-05T09:25:00.000Z",
    "deleted_at": null,
    "created_by_adm": "c1d2e3f4-a5b6-47c8-9d0e-1f2a3b4c5d6e",
    "updated_by_adm": "c1d2e3f4-a5b6-47c8-9d0e-1f2a3b4c5d6e",
    "deleted_by_adm": null,
    "is_deleted": false,
    "cnpj": "45678912000177",
    "nome": "Igreja Vozes da Fé",
    "denominacao": "EVANGÉLICA",
    "outra_denominacao": "Igreja Independente",
    "s_storage_t_tb_arquivo_c_foto": "b9c8a7d6-5e4f-43b2-8a1c-9e0d2f3a4b5c",
    "s_auth_t_tb_usuario_c_adm_proprietario": "b2c3d4e5-f6a7-49b8-c9d0-e1f2a3b4c5d6"
  },
  {
    "uuid": "a9b1c2d3-e4f5-46a7-9b8c-0d1e2f3a4b5c",
    "created_at": "2025-10-05T09:30:00.000Z",
    "updated_at": "2025-10-05T09:30:00.000Z",
    "deleted_at": null,
    "created_by_adm": "d2e3f4a5-b6c7-48d9-8e0f-1a2b3c4d5e6f",
    "updated_by_adm": "d2e3f4a5-b6c7-48d9-8e0f-1a2b3c4d5e6f",
    "deleted_by_adm": null,
    "is_deleted": false,
    "cnpj": "78912345000123",
    "nome": "Igreja Infantil Louvor Kids",
    "denominacao": "BATISTA",
    "outra_denominacao": null,
    "s_storage_t_tb_arquivo_c_foto": "c3b2a1e4-6d7c-48b9-8e0f-1a2b3c4d5e6f",
    "s_auth_t_tb_usuario_c_adm_proprietario": "e3f4a5b6-c7d8-49e9-8f0a-1b2c3d4e5f6a"
  },
  {
    "uuid": "c8d1e2f3-4a5b-46c7-8d9e-0f1a2b3c4d5e",
    "created_at": "2025-10-05T09:35:00.000Z",
    "updated_at": "2025-10-05T09:35:00.000Z",
    "deleted_at": null,
    "created_by_adm": "e3f4a5b6-c7d8-49e9-8f0a-1b2c3d4e5f6a",
    "updated_by_adm": "e3f4a5b6-c7d8-49e9-8f0a-1b2c3d4e5f6a",
    "deleted_by_adm": null,
    "is_deleted": false,
    "cnpj": "32165498000155",
    "nome": "Igreja Som da Graça",
    "denominacao": "PENTECOSTAL",
    "outra_denominacao": null,
    "s_storage_t_tb_arquivo_c_foto": "d4c5b6a7-e8f9-40a1-b2c3-d4e5f6a7b8c9",
    "s_auth_t_tb_usuario_c_adm_proprietario": "f5a6b7c8-d9e0-41f2-b3a4-c5d6e7f8a9b0"
  }
]
