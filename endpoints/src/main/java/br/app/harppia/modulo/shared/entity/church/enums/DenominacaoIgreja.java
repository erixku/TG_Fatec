package br.app.harppia.modulo.shared.entity.church.enums;

import br.app.harppia.defaults.utils.rules.EnumPersistivel;

public enum DenominacaoIgreja implements EnumPersistivel {
    OUTRA("outra"),
    ADVENTISTA("adventista"),
    ASSEMBLEIA_DE_DEUS("assembleia de deus"),
    BATISTA("batista"),
    BOLA_DE_NEVE("bola de neve"),
    CASA_DA_BENCAO("casa da bênção"),
    COMUNIDADE_CRISTA("comunidade cristã"),
    CONGREGACAO_CRISTA_NO_BRASIL("congregação cristã no brasil"),
    DEUS_E_AMOR("deus é amor"),
    EVANGELHO_QUADRANGULAR("evangelho quadrangular"),
    IGREJA_EPISCOPAL("igreja episcopal"),
    IGREJA_INTERNACIONAL_DA_GRACA_DE_DEUS("igreja internacional da graça de deus"),
    IGREJA_LUTERANA("igreja luterana"),
    IGREJA_METODISTA("igreja metodista"),
    IGREJA_PENTECOSTAL("igreja pentecostal"),
    IGREJA_PRESBITERIANA("igreja presbiteriana"),
    IGREJA_RENASCER_EM_CRISTO("igreja renascer em cristo"),
    IGREJA_SARA_NOSSA_TERRA("igreja sara nossa terra"),
    IGREJA_UNIVERSAL_DO_REINO_DE_DEUS("igreja universal do reino de deus"),
    MINISTERIO_APASCENTAR("ministério apascentar"),
    MINISTERIO_FONTE_DA_VIDA("ministério fonte da vida"),
    MINISTERIO_INTERNACIONAL_DA_RESTAURACAO("ministério internacional da restauração"),
    MINISTERIO_VOZ_DA_VERDADE("ministério voz da verdade"),
    NOVA_VIDA("nova vida"),
    O_BRASIL_PARA_CRISTO("o brasil para cristo"),
    PAZ_E_VIDA("paz e vida"),
    PROJETO_VIDA("projeto vida"),
    VERBO_DA_VIDA("verbo da vida"),
    VIDEIRA("videira"),
    VITORIA_EM_CRISTO("vitória em cristo"),
    WESLEYANA("wesleyana"),
    ZION_CHURCH("zion church");

    private final String denominacao;

    DenominacaoIgreja(String denominacao) {
        this.denominacao = denominacao;
    }

    @Override
    public String getCustomValue() {
        return denominacao;
    }
}
