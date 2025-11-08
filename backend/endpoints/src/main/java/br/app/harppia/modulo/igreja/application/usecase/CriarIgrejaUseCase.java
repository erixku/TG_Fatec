package br.app.harppia.modulo.igreja.application.usecase;

import org.springframework.stereotype.Service;

import br.app.harppia.defaults.custom.exceptions.GestaoIgrejaException;
import br.app.harppia.modulo.igreja.domain.request.CadastroIgrejaRequest;
import br.app.harppia.modulo.igreja.domain.response.CadastroIgrejaResponse;
import br.app.harppia.modulo.igreja.infrastructure.mapper.IgrejaMapper;
import br.app.harppia.modulo.igreja.infrastructure.repository.IgrejaRepository;
import br.app.harppia.modulo.igreja.infrastructure.repository.entities.IgrejaEntity;

@Service
public class CriarIgrejaUseCase {

	private final IgrejaRepository igrejaRepository;
	private final IgrejaMapper igrejaMapper;

	public CriarIgrejaUseCase(IgrejaRepository igrejaRepository, IgrejaMapper igrejaMapper) {
		this.igrejaRepository = igrejaRepository;
		this.igrejaMapper = igrejaMapper;
	}

	public CadastroIgrejaResponse cadastrar(CadastroIgrejaRequest requestDto) {
		if(requestDto == null) 
			throw new GestaoIgrejaException("Falha ao cadastrar igreja: informações ausentes.");
		
		IgrejaEntity igreja = igrejaMapper.toEntity(requestDto);
		
		igreja = igrejaRepository.save(igreja);
		
		if(igreja.getId() == null)
			throw new GestaoIgrejaException("Houve algum erro ao cadastrar a igreja... Tente novamente mais tarde.");
			
		return CadastroIgrejaResponse.builder()
				.idIgreja(igreja.getId())
				.idDono(igreja.getIdProprietario())
				.nome(igreja.getNome())
				.build();
		
	}
	
}
