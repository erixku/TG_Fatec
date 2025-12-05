package br.app.harppia.defaults.custom.sanitizers;

public abstract class CpfSanitizer {

	/**
	 * Apenas remove qualquer coisa que não sejam números. Não realiza validação dos
	 * digitos verificadores.
	 */
	public static String sanitize(String cpf) {
		if (cpf.trim() == null || cpf.isEmpty())
			return null;

		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11)
			throw new IllegalArgumentException("CPF inválido: deve conter exatamente 11 dígitos");

		if (cpf.matches("(\\d)\\1{10}"))
			throw new IllegalArgumentException("CPF inválido: não pode conter todos os dígitos iguais");

		return cpf;
	}
}
