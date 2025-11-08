package br.app.harppia.modulo.igreja.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.modulo.igreja.domain.request.BuscarIgrejaRequest;
import br.app.harppia.modulo.igreja.domain.response.BuscarIgrejaResponse;
import br.app.harppia.modulo.igreja.infrastructure.repository.IgrejaRepository;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;

@Service
public class BuscarIgrejaUseCase {

	private final IgrejaRepository igrejaRepository;
	
	public BuscarIgrejaUseCase(IgrejaRepository igrejaRepository) {
		this.igrejaRepository = igrejaRepository;
	}

	public BuscarIgrejaResponse buscar(BuscarIgrejaRequest requestDto) {
		
		if(requestDto == null)
			throw new GestaoIgrejaException("Não foi possível buscar pela igreja: dados ausentes!");
		
		Optional<IgrejaEntity> igreja = igrejaRepository.findById(requestDto.idIgreja());
		
		if(igreja.isEmpty())
			throw new GestaoIgrejaException("Nenhuma igreja encontrada!");
			
		return BuscarIgrejaResponse.builder()
				.idIgreja(igreja.get().getId())
				.build();
	}
}
