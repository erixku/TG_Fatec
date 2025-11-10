package br.app.harppia.modulo.file.domain.valueobjects;

import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

public record BucketRestricoesUploadInfo(Short id, ENomeBucket nome, Boolean isDeleted, 
		Integer tamanhoMinimo, Integer tamanhoMaximo, Short tempoLimiteUpload) {

}
