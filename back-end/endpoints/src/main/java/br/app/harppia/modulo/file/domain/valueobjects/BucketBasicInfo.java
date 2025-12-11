package br.app.harppia.modulo.file.domain.valueobjects;

import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;

public record BucketBasicInfo(Integer id, ENomeBucket nome) {

}
