package br.app.harppia.defaults.custom.sanitizers;

public abstract class CpfSanitizer {

	/**
	 * Método para sanitizar o CPF, antes de persistí-lo no banco de dados.
	 */
	public static String sanitize(String cpf) {
		if (cpf.trim() == null)
			throw new IllegalArgumentException("CPF inválido: não pode ser nulo");

		if (cpf.isEmpty())
			throw new IllegalArgumentException("CPF inválido: não pode ser vazio");

		// Remove todos os caracteres que não sejam dígitos
		cpf = cpf.replaceAll("[^0-9]", "");

		// Após a sanitização, o CPF deve ter exatamente 11 dígitos
		if (cpf.length() != 11)
			throw new IllegalArgumentException("CPF inválido: deve conter exatamente 11 dígitos");

		// Não pode conter todos os dígitos iguais (ex: 00000000000, 11111111111, etc.)
		if (cpf.matches("(\\d)\\1{10}"))
			throw new IllegalArgumentException("CPF inválido: não pode conter todos os dígitos iguais");

		// Realiza o cálculo dos dígitos verificadores
		if (!isValidCpfDigits(cpf))
			throw new IllegalArgumentException("CPF inválido: digito verificador incorreto");

		return cpf;
	}

	/**
	 * Verifica se os dígitos verificadores do CPF (somente números) são válidos.
	 * 
	 * @param cpf CPF já sanitizado (apenas 11 dígitos)
	 * @return true se os dígitos forem válidos, false caso contrário
	 */
	public static boolean isValidCpfDigits(String cpf) {
		if (cpf == null || !cpf.matches("\\d{11}")) {
			return false; // só aceita CPFs sanitizados
		}

		// Converte em array de inteiros
		int[] numeros = cpf.chars().map(c -> c - '0').toArray();

		// --- Valida o primeiro dígito verificador ---
		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += numeros[i] * (10 - i);
		}
		int resto = (soma * 10) % 11;
		if (resto == 10) {
			resto = 0;
		}
		if (resto != numeros[9]) {
			return false;
		}

		// --- Valida o segundo dígito verificador ---
		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += numeros[i] * (11 - i);
		}
		resto = (soma * 10) % 11;
		if (resto == 10) {
			resto = 0;
		}
		if (resto != numeros[10]) {
			return false;
		}

		return true;
	}
}
