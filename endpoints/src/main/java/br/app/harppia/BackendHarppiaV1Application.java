package br.app.harppia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendHarppiaV1Application {

	public static void main(String[] args) {
		Environment env = SpringApplication.run(BackendHarppiaV1Application.class, args).getEnvironment();
        
        if (env.getProperty("SPRING_DATASOURCE_URL") == null && System.getenv("SPRING_DATASOURCE_URL") == null) {
            // Se a variável não estiver presente, tenta carregar do .env
            try {
                Dotenv dotenv = Dotenv.load();
                dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
            } catch (Exception e) {
                System.err.println("Atenção: .env não encontrado. Certifique-se de que as variáveis de ambiente estão configuradas.");
                // Opcional: logar o erro, mas não travar a aplicação
            }
        }
	}

}
