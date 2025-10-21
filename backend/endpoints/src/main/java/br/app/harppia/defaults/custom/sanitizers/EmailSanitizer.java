package br.app.harppia.defaults.custom.sanitizers;

import java.text.Normalizer;
import java.util.Set;

/**
 * Essa clase serve para limpar e formatar o email antes que ele seja persistido.
 * Não deve ser instanciada.
 */
public abstract class EmailSanitizer {

	private static final Set<String> DOMINIOS_VALIDOS = Set.of("yahoo.com", "hotmail.com", "outlook.com", "gmail.com");

	public static String sanitize(String email) {
		if (email == null) {
			throw new IllegalArgumentException("E-mail inválido: não pode ser nulo");
		}

		email = email.trim().toLowerCase();
		email = email.replaceAll("\\p{C}", "");

		// Normalizar Unicode (para evitar caracteres parecidos)
		email = Normalizer.normalize(email, Normalizer.Form.NFC);

		// Remover caracteres fora do conjunto permitido
		email = email.replaceAll("[^a-z0-9!@#%_+\\-.@]", "");

		// Garantir que existe um único @
		if (email.chars().filter(ch -> ch == '@').count() != 1) {
			throw new IllegalArgumentException("E-mail inválido: deve conter exatamente um @");
		}

		// Dividir em local-part e domínio
		String localPart = email.split("@")[0];
		String domain = email.split("@")[1];

		// Remover pontos da parte antes do @
		localPart = localPart.replace(".", "");

		// Validar domínio permitido
		if (!DOMINIOS_VALIDOS.contains(domain)) {
			throw new IllegalArgumentException(
					"E-mail inválido: só são permitidos gmail.com, outlook.com, hotmail.com e yahoo.com");
		}

		// Reconstruir e-mail tratado
		email = localPart + "@" + domain;

		if (email.length() < 11) {
			throw new IllegalArgumentException("E-mail inválido: deve ter pelo menos 11 caracteres");
		}
		if (email.length() > 50) {
			throw new IllegalArgumentException("E-mail inválido: deve ter no máximo 50 caracteres");
		}

		return email;
	}
}
