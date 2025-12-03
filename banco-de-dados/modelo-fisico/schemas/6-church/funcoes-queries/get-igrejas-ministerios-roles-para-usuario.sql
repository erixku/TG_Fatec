CREATE OR REPLACE FUNCTION
  utils.s_church_f_get_igrejas_ministerios_roles_para_usuario (
    IN usuario_id UUID
  )
RETURNS TABLE (
  igreja              UUID,
  ministerio          UUID,
  funcao              INTEGER,
  role_usuario_igreja VARCHAR(16)
)
LANGUAGE sql
VOLATILE
SECURITY INVOKER
SET search_path = auth, church, pg_catalog
AS $$
  WITH
    selecionar_ministerios_funcoes_usuario AS (
      SELECT
        i.id AS igreja,
        m.id AS ministerio,
        uf.id AS funcao,
        'membro'::VARCHAR(16) AS role_usuario_igreja
      FROM
        church.tb_usuario_funcao uf
      INNER JOIN
        auth.tb_usuario u
        ON u.id = uf.s_auth_t_tb_usuario_c_lev
      INNER JOIN
        church.tb_ministerio_louvor m
        ON m.id = uf.min_lou_id
      INNER JOIN
        church.tb_igreja i
        ON i.id = m.igr_id
      WHERE
        u.id = usuario_id
    ),

    selecionar_igreja_onde_e_proprietario AS (
      SELECT
        i.id AS igreja,
        NULL::UUID AS ministerio,
        NULL::INTEGER AS funcao,
        'adm_proprietario'::VARCHAR(16) AS role_usuario_igreja
      FROM
        church.tb_igreja i
      WHERE
        i.s_auth_t_tb_usuario_c_adm_proprietario = usuario_id
    ),

    selecionar_igreja_onde_e_administrador AS (
      SELECT
        i.id AS igreja,
        NULL::UUID AS ministerio,
        NULL::INTEGER AS funcao,
        'administrador'::VARCHAR(16) AS role_usuario_igreja
      FROM
        church.tb_igreja i
      INNER JOIN
        church.tb_administrador a
        ON i.id = a.igr_id
      WHERE
        s_auth_t_tb_usuario_c_adm = usuario_id
    )
  
  SELECT
    *
  FROM
    selecionar_ministerios_funcoes_usuario
  UNION ALL

  SELECT
    *
  FROM
    selecionar_igreja_onde_e_proprietario
  UNION ALL

  SELECT
    *
  FROM
    selecionar_igreja_onde_e_administrador;
$$;