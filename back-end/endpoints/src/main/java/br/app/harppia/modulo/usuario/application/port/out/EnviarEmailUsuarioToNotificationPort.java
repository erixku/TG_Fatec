package br.app.harppia.modulo.usuario.application.port.out;

public interface EnviarEmailUsuarioToNotificationPort {
	void enviar(String strDestinatario, String strAssunto, String strConteudoHTML);
}

