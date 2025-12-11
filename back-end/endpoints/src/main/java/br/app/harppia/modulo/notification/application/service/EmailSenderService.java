package br.app.harppia.modulo.notification.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {

	@Value("${app.email.remetente}")
	private String remetente;
	
    private final JavaMailSender mailSender;

    @Async 
    public void enviarEmail(String destinatario, String assunto, String conteudoHtml) {
        log.info("Iniciando envio de email para: {}", destinatario);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(conteudoHtml, true);

            mailSender.send(message);
            
            log.info("Email enviado com sucesso para: {}", destinatario);

        } catch (MessagingException e) {
            log.error("Falha ao enviar email para {}: {}", destinatario, e.getMessage());
        }
    }
}