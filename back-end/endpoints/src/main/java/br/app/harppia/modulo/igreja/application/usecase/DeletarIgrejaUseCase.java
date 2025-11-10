package br.app.harppia.modulo.igreja.application.usecase;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.modulo.igreja.domain.request.DeletarIgrejaRequest;
import br.app.harppia.modulo.igreja.domain.response.DeletarIgrejaResponse;
import br.app.harppia.modulo.igreja.infrastructure.repository.IgrejaRepository;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;

@Service
public class DeletarIgrejaUseCase {

	private final IgrejaRepository igrejaRepository;
	
	public DeletarIgrejaUseCase(IgrejaRepository igrejaRepository) {
		this.igrejaRepository = igrejaRepository;
	}

	public DeletarIgrejaResponse deletar(DeletarIgrejaRequest requestDto) {
		
		if(requestDto == null)
			throw new GestaoIgrejaException("Não foi possível buscar pela igreja: dados ausentes!");
		
		Optional<IgrejaEntity> igreja = igrejaRepository.findById(requestDto.idIgreja());
		
		if(igreja.isEmpty())
			throw new GestaoIgrejaException("Nenhuma igreja encontrada!");
			
		IgrejaEntity dadosIgrjAtualizados = igreja.get();
		dadosIgrjAtualizados.setIsDeleted(true);
		dadosIgrjAtualizados.setDeletedAt(OffsetDateTime.now());
		dadosIgrjAtualizados.setDeletedBy(requestDto.exclusor());
		
		IgrejaEntity igrejaDeletada = igrejaRepository.save(dadosIgrjAtualizados);
		
		if(igrejaDeletada.getDeletedAt().equals(igreja.get().getDeletedAt()))
			throw new GestaoIgrejaException("Houve algum problema ao deletar a igreja...");
		
		return DeletarIgrejaResponse.builder()
				.idIgreja(igrejaDeletada.getId())
				.isDeleted(igrejaDeletada.getIsDeleted())
				.exclusor(igrejaDeletada.getDeletedBy())
				.build();
	}
}
