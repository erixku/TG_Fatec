package br.app.harppia.modulo.church.infrastructure.adapter;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.activities.application.port.out.ConsultarIgrejaActivityToChurchPort;
import br.app.harppia.modulo.church.application.service.CategoriaAtividadeService;

@Component
public class ConsultarIgrejaFromActivityAdapter implements ConsultarIgrejaActivityToChurchPort {

	private final CategoriaAtividadeService catAtvSvc;
	
	public ConsultarIgrejaFromActivityAdapter(CategoriaAtividadeService catAtvSvc) {
		this.catAtvSvc = catAtvSvc;
	}

	@Override
	public Integer getIdOf(String strCategoria) {
		
		if(strCategoria == null || strCategoria.trim().isEmpty())
			return null;
		
		return catAtvSvc.getIdCategoriaFrom(strCategoria);
	}

}
