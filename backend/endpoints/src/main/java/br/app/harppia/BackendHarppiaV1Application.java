package br.app.harppia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication(scanBasePackages = "br.app.harppia")
@EnableAsync
public class BackendHarppiaV1Application {

	public static void main(String[] args) {
		// Carrega .env primeiro
        try {
            Dotenv dotenv = Dotenv.load();
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        } catch (Exception e) {
            System.err.println("Atenção: .env não encontrado. Certifique-se de que as variáveis de ambiente estão configuradas.");
        }

        // Sobe a aplicação
        SpringApplication.run(BackendHarppiaV1Application.class, args);
	}
}
