package br.app.harppia.defaults.custom.validators;

import br.app.harppia.defaults.custom.annotations.ValidCPF;
import br.app.harppia.defaults.custom.sanitizers.CpfSanitizer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Para que um CPF seja válido, ele deve estar de acordo com as seguintes
 * regras: (1) Não ser nulo; (2) Não estar em branco; (3) Não ter pontos, hífen,
 * tabulação, carriage return e quebra de linha; e (4) Ter exatamente 11 dígitos
 * (numéricos), além de passar no cálculo de validação. Essa classe irá cobrir
 * apenas estes aspectos.
 */
public class CpfValidator implements ConstraintValidator<ValidCPF, String> {

	@Override
	public boolean isValid(String cpf, ConstraintValidatorContext context) {

		// O CPF não deve ser nulo ou vazio
		if (cpf == null || cpf.trim().isEmpty())
			return true;

		// O CPF deve conter apenas dígitos numéricos, ponto ou hífen
		if (!cpf.matches("[\\d.\\-]+"))
			return buildViolation(context, "O CPF deve ter apenas: números (0-9), pontos e hífen!");

		// O CPF deve ter, no mínimo, 11 dígitos
		if (cpf.length() < 11)
			return buildViolation(context, "O CPF deve ter pelo menos 11 dígitos!");

		String cpfSomenteNumeros = this.removerPontosHifenCPF(cpf);

		// O CPF não pode ter todos os dígitos iguais
		if (todosDigitosSaoIguais(cpfSomenteNumeros))
			return buildViolation(context, "Todos os dígitos do CPF são iguais!");

		try {
			// Cálculo e validação do primeiro dígito verificador
			int digito1 = calcularDigitoVerificador(cpfSomenteNumeros.substring(0, 9));
			if (digito1 != Character.getNumericValue(cpfSomenteNumeros.charAt(9)))
				return buildViolation(context, "CPF inválido: primeiro dígito verificador incorreto.");

			// Cálculo e validação do segundo dígito verificador
			int digito2 = calcularDigitoVerificador(cpfSomenteNumeros.substring(0, 10));
			if (digito2 != Character.getNumericValue(cpfSomenteNumeros.charAt(10)))
				return buildViolation(context, "CPF inválido: segundo dígito verificador incorreto.");

		} catch (Exception ex) {
			return buildViolation(context, "Houve algum erro durante a validação do CPF...");
		}

		return true;
	}

	private boolean buildViolation(ConstraintValidatorContext context, String message) {
		// Desabilita a mensagem padrão
		context.disableDefaultConstraintViolation();

		// Define a mensagem a ser mostrada
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

		// Como é usado somente em caso de erros, sempre retorna false
		return false;
	}

	private String removerPontosHifenCPF(String cpfSujo) {
		return CpfSanitizer.sanitize(cpfSujo);
	}

	private boolean todosDigitosSaoIguais(String cpf) {
		char primeiro = cpf.charAt(0);
		for (int i = 1; i < cpf.length(); i++) {
			if (cpf.charAt(i) != primeiro) {
				return false; // Encontrou um dígito diferente, então é válido neste quesito
			}
		}
		return true; // Todos os dígitos são iguais
	}

	private int calcularDigitoVerificador(String trecho) {
		int soma = 0;
		int peso = trecho.length() + 1;
		
		for (int i = 0; i < trecho.length(); i++) {
			soma += Character.getNumericValue(trecho.charAt(i)) * (peso - i);
		}
		
		int resto = 11 - (soma % 11);
		return (resto >= 10) ? 0 : resto;
	}
}
