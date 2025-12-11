package br.app.harppia.modulo.music.domain.response;

import java.util.List;

import br.app.harppia.modulo.music.domain.valueobject.InformacoesBasicasMusicaRVO;

public record BuscarMusicaResponse(List<InformacoesBasicasMusicaRVO> listInfBscMusRVO) {
}
