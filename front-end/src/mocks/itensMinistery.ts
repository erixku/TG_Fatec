export type MinisteryType = {
  uuid: string;
  created_at: string; // ISO date string
  updated_at: string; // ISO date string
  deleted_at: string | null;
  created_by_adm: string;
  updated_by_adm: string;
  deleted_by_adm: string | null;
  is_deleted: boolean;
  nome: string;
  descricao: string;
  codigo: string;
  igr_uuid: string;
  s_storage_t_tb_arquivo_c_foto: string | null;
};

export const itensMinistery = [
  {
    "uuid": "7c0a1e5d-8a29-4c77-96b2-ecb4f0e2d351",
    "created_at": "2025-10-10T14:32:11.000Z",
    "updated_at": "2025-10-10T14:32:11.000Z",
    "deleted_at": null,
    "created_by_adm": "a1b2c3d4-e5f6-47a8-9b0c-1d2e3f4a5b6c",
    "updated_by_adm": "a1b2c3d4-e5f6-47a8-9b0c-1d2e3f4a5b6c",
    "deleted_by_adm": null,
    "is_deleted": false,
    "nome": "Ministério de Louvor Central",
    "descricao": "Louvor principal da sede",
    "codigo": "ML001",
    "igr_uuid": "f4a1d6b8-7c3d-49e1-8a91-c4d71b2a2a54",
    "s_storage_t_tb_arquivo_c_foto": "c1a2b3c4-d5e6-47f8-8a9b-0c1d2e3f4a5b"
  },
  {
    "uuid": "f38e5a42-62b8-4b69-a2f1-1bcb413e28b7",
    "created_at": "2025-10-10T14:33:00.000Z",
    "updated_at": "2025-10-10T14:33:00.000Z",
    "deleted_at": null,
    "created_by_adm": "b7c8d9e0-f1a2-43b4-9c5d-6e7f8a9b0c1d",
    "updated_by_adm": "b7c8d9e0-f1a2-43b4-9c5d-6e7f8a9b0c1d",
    "deleted_by_adm": null,
    "is_deleted": false,
    "nome": "Adoração Jovem",
    "descricao": "Grupo de louvor dos jovens",
    "codigo": "ML002",
    "igr_uuid": "e8c1a5d7-9b4e-4c3a-a891-f7e2b5c2a0f1",
    "s_storage_t_tb_arquivo_c_foto": "d4c5b6a7-e8f9-40a1-b2c3-d4e5f6a7b8c9"
  },
  {
    "uuid": "be1f96a9-2a85-4a09-9b47-1d9eb9fce51f",
    "created_at": "2025-10-10T14:33:45.000Z",
    "updated_at": "2025-10-10T14:33:45.000Z",
    "deleted_at": null,
    "created_by_adm": "c1d2e3f4-a5b6-47c8-9d0e-1f2a3b4c5d6e",
    "updated_by_adm": "c1d2e3f4-a5b6-47c8-9d0e-1f2a3b4c5d6e",
    "deleted_by_adm": null,
    "is_deleted": false,
    "nome": "Vozes da Fé",
    "descricao": "Coral adulto da igreja",
    "codigo": "ML003",
    "igr_uuid": "b6d1c2e3-4f5a-4b7d-8a9c-1e2f3d4b5a6c",
    "s_storage_t_tb_arquivo_c_foto": "f1e2d3c4-b5a6-49d7-8f9a-0b1c2d3e4f5a"
  },
  {
    "uuid": "a5dbf493-3f4d-4b2c-b88c-0a5e1bb7819f",
    "created_at": "2025-10-10T14:34:22.000Z",
    "updated_at": "2025-10-10T14:34:22.000Z",
    "deleted_at": null,
    "created_by_adm": "d2e3f4a5-b6c7-48d9-8e0f-1a2b3c4d5e6f",
    "updated_by_adm": "d2e3f4a5-b6c7-48d9-8e0f-1a2b3c4d5e6f",
    "deleted_by_adm": null,
    "is_deleted": false,
    "nome": "Louvor Kids",
    "descricao": "Louvor infantil",
    "codigo": "ML004",
    "igr_uuid": "a9b1c2d3-e4f5-46a7-9b8c-0d1e2f3a4b5c",
    "s_storage_t_tb_arquivo_c_foto": "b2c3d4e5-f6a7-41b8-c9d0-e1f2a3b4c5d6"
  },
  {
    "uuid": "edb3e5d2-0c62-4f87-9d3e-046acbd28d44",
    "created_at": "2025-10-10T14:35:10.000Z",
    "updated_at": "2025-10-10T14:35:10.000Z",
    "deleted_at": null,
    "created_by_adm": "e3f4a5b6-c7d8-49e9-8f0a-1b2c3d4e5f6a",
    "updated_by_adm": "e3f4a5b6-c7d8-49e9-8f0a-1b2c3d4e5f6a",
    "deleted_by_adm": null,
    "is_deleted": false,
    "nome": "Som da Graça",
    "descricao": "Grupo acústico e coral misto",
    "codigo": "ML005",
    "igr_uuid": "c8d1e2f3-4a5b-46c7-8d9e-0f1a2b3c4d5e",
    "s_storage_t_tb_arquivo_c_foto": "c4d5e6f7-a8b9-4c0d-b1e2-c3f4a5b6d7e8"
  }
]