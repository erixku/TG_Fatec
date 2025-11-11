package br.app.harppia.modulo.file.domain.valueobjects;

import br.app.harppia.modulo.file.infrastructure.repository.enums.ENomeBucket;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketRestricoesUploadInfoCVO {

	private final Short id;
	private final Boolean isDeleted;	
	private final ENomeBucket eNomeBkt;
	private final Short tmpLmtUpl;
	private final Integer tamMax;
	private final Integer tamMin;
	public BucketRestricoesUploadInfoCVO(Short id, Boolean isDeleted, String eNomeBkt, Short tmpLmtUpl,
			Integer tamMax, Integer tamMin) {
		this.id = id;
		this.isDeleted = isDeleted;
		this.eNomeBkt = ENomeBucket.fromNome(eNomeBkt);
		this.tmpLmtUpl = tmpLmtUpl;
		this.tamMax = tamMax;
		this.tamMin = tamMin;
	}

	
}
