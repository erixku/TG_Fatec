GRANT USAGE ON SCHEMA accessibility TO role_usuario;



-- tb_intelectual
GRANT
  SELECT (
    id,
    updated_at,
    tamanho_icone,
    modo_foco,
    feedback_imediato
  )
  ON TABLE accessibility.tb_intelectual
  TO role_usuario;

GRANT
  UPDATE (
    updated_at,
    tamanho_icone,
    modo_foco,
    feedback_imediato
  )
  ON TABLE accessibility.tb_intelectual
  TO role_usuario;



-- tb_auditiva
GRANT
  SELECT (
    id,
    updated_at,
    modo_flash,
    intensidade_flash,
    transcricao_audio,
    vibracao_aprimorada,
    alertas_visuais
  )
  ON TABLE accessibility.tb_auditiva
  TO role_usuario;

GRANT
  UPDATE (
    updated_at,
    modo_flash,
    intensidade_flash,
    transcricao_audio,
    vibracao_aprimorada,
    alertas_visuais
  )
  ON TABLE accessibility.tb_auditiva
  TO role_usuario;



-- tb_visual
GRANT
  SELECT (
    id,
    updated_at,
    cor_tema,
    tamanho_texto,
    negrito,
    alto_contraste,
    modo_daltonismo,
    intensidade_daltonismo,
    remover_animacoes,
    vibrar_ao_tocar
  )
  ON TABLE accessibility.tb_visual
  TO role_usuario;

GRANT
  UPDATE (
    updated_at,
    cor_tema,
    tamanho_texto,
    negrito,
    alto_contraste,
    modo_daltonismo,
    intensidade_daltonismo,
    remover_animacoes,
    vibrar_ao_tocar
  )
  ON TABLE accessibility.tb_visual
  TO role_usuario;