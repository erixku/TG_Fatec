package br.app.harppia.modulo.file.domain.valueobjects;

import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

public record BucketRestricoesUploadInfo(Integer id, ENomeBucket nome, Boolean isDeleted, 
		Long tamanhoMinimo, Long tamanhoMaximo, Integer tempoLimiteUpload) {

}
