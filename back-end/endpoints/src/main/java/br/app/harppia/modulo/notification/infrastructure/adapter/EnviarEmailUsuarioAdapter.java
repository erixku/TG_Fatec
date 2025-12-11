package br.app.harppia.modulo.notification.infrastructure.adapter;

import org.springframework.stereotype.Component;

import br.app.harppia.modulo.notification.application.service.EmailSenderService;
import br.app.harppia.modulo.usuario.application.port.out.EnviarEmailUsuarioToNotificationPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnviarEmailUsuarioAdapter implements EnviarEmailUsuarioToNotificationPort {

	private final EmailSenderService envEmlSvc;
	
	@Override
	public void enviar(String strDestinatario, String strAssunto, String strConteudoHTML) {
		
		if(strDestinatario.trim().isEmpty())
			return;
		
		envEmlSvc.enviarEmail(strDestinatario, strAssunto, strConteudoHTML);
	}
}
