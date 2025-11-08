COMMENT ON SCHEMA schedule IS '
  Schema que armazena dados referentes às
  tarefas da igreja, como escalas, agendamentos,
  compromissos etc.
';



COMMENT ON TABLE schedule.tb_registro_ausencia IS '
  Tabela que armazena períodos de ausência de
  usuários em determinada igreja. Eles não podem
  ser inseridos em nenhuma atividade enquanto
  estiverem nesse período de ausência
';



COMMENT ON TABLE schedule.tb_publicacao IS '
  Tabela que armazena publicações da igreja.
  Publicações se referem a avisos, agendamentos
  e compromissos

  Os avisos são 100% sanados com esta tabela,
  mas agendamentos e compromissos possuem dados
  extras na tabela de atividade e em suas interconexões
';



COMMENT ON TABLE schedule.tb_atividade IS '
  Tabela que armazena dados específicos de agendamentos
  e compromissos, que não estão na tabela geral (tb_publicacao).
';



COMMENT ON TABLE schedule.tb_participante IS '
  Tabela que armazena quem são os levitas participantes
  de um agendamento/compromisso. Os usuários são adicionados
  em agendamentos/compromissos pelo criador da publicação,
  e por padrão, possuem presença confirmada, mas podem revogar
  essa presença
';

COMMENT ON COLUMN
schedule.tb_participante.s_church_t_tb_instrumento_ass_usuario_c_funcao
IS '
  Esta coluna deve ser nula quando o tipo de atividade for "compromisso",
  e deve ser não nula quando o tipo de atividade for "agendamento"

  Quando for agendamento, o levita pode ser cadastrado mais de uma vez
  nessa tabela para a mesma atividade, isso para caso ele exerça mais
  de uma função na atividade
';



COMMENT ON TABLE schedule.tb_item_levado IS '
  Tabela que armazena itens que usuários estão levando para um compromisso,
  como alimentos ou outros itens gerais
';



COMMENT ON TABLE schedule.tb_faixa_elencada IS '
  Tabela que armazena as faixas que serão tocadas em um agendamento
';



COMMENT ON TABLE schedule.tb_escala IS '
  Tabela que armazena escalas de agendamentos e compromissos
';



COMMENT ON TABLE schedule.tb_escala_ass_atividade IS '
  Tabela que armazena a associação entre escalas e atividades
';