package br.app.harppia.modulo.file.domain.valueobjects;

public record BucketRestricoesUploadInfoRVO(
    Short id, 
    Boolean isDeleted, 
    String nome,
    Short tempoLimiteUpload,
    Integer tamanhoMaximo, 
    Integer tamanhoMinimo
) {

}
