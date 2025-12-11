package br.app.harppia.modulo.notification.interfaces.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/notification")
public class NotificacaoController {

	@PostMapping("/create")
	public void criar() {
		
	}
}
