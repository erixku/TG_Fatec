package br.app.harppia.modulo.church.application.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.modulo.church.domain.request.AtualizarIgrejaRequest;
import br.app.harppia.modulo.church.domain.response.AtualizarIgrejaResponse;
import br.app.harppia.modulo.church.infrastructure.repository.IgrejaRepository;
import br.app.harppia.modulo.church.infrastructure.repository.entities.IgrejaEntity;

@Service
public class AtualizarIgrejaUseCase {

	private final IgrejaRepository igrejaRepository;
	
	public AtualizarIgrejaUseCase(IgrejaRepository igrejaRepository) {
		this.igrejaRepository = igrejaRepository;
	}

	public AtualizarIgrejaResponse atualizar(AtualizarIgrejaRequest requestDto) {
		
		if(requestDto == null)
			throw new GestaoIgrejaException("Não foi possível buscar pela igreja: dados ausentes!");
		
		Optional<IgrejaEntity> igreja = igrejaRepository.findById(requestDto.idIgreja());
		
		if(igreja.isEmpty())
			throw new GestaoIgrejaException("Nenhuma igreja encontrada!");
		
		IgrejaEntity dadosIgrjAtualizados = atualizarDados(igreja.get(), requestDto);
		
		dadosIgrjAtualizados = igrejaRepository.save(dadosIgrjAtualizados);
		
		return AtualizarIgrejaResponse.builder()
				.idIgreja(dadosIgrjAtualizados.getId())
				.nome(dadosIgrjAtualizados.getNome())
				.build();
	}

	private IgrejaEntity atualizarDados(IgrejaEntity oldData, AtualizarIgrejaRequest newData) {
		
		IgrejaEntity dadosAtualizados = oldData;
		
		if(!oldData.getNome().equals(newData.nome()))
			dadosAtualizados.setNome(newData.nome());
		
		return dadosAtualizados;
	}
}
