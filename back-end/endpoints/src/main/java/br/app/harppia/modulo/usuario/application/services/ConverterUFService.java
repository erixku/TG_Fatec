package br.app.harppia.modulo.usuario.application.services;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class ConverterUFService {

	private static final Set<String> UFs_VALIDAS = Set.of(
		"AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA",
		"MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN",
		"RO", "RR", "RS", "SC", "SE", "SP", "TO"
	);

	private static final Map<String, String> ESTADO_PARA_SIGLA = Map.ofEntries(
		Map.entry("acre", "AC"),
		Map.entry("alagoas", "AL"),
		Map.entry("amapa", "AP"),
		Map.entry("amazonas", "AM"),
		Map.entry("bahia", "BA"),
		Map.entry("ceara", "CE"),
		Map.entry("distrito federal", "DF"),
		Map.entry("espirito santo", "ES"),
		Map.entry("goias", "GO"),
		Map.entry("maranhao", "MA"),
		Map.entry("minas gerais", "MG"),
		Map.entry("mato grosso do sul", "MS"),
		Map.entry("mato grosso", "MT"),
		Map.entry("para", "PA"),
		Map.entry("paraiba", "PB"),
		Map.entry("pernambuco", "PE"),
		Map.entry("piaui", "PI"),
		Map.entry("parana", "PR"),
		Map.entry("rio de janeiro", "RJ"),
		Map.entry("rio grande do norte", "RN"),
		Map.entry("rondonia", "RO"),
		Map.entry("roraima", "RR"),
		Map.entry("rio grande do sul", "RS"),
		Map.entry("santa catarina", "SC"),
		Map.entry("sergipe", "SE"),
		Map.entry("sao paulo", "SP"),
		Map.entry("tocantins", "TO")
	);

	public String paraSigla(String value) {

		if (value == null || value.trim().isEmpty() || value.trim().length() < 2)
			return null;

		value = Normalizer.normalize(value.trim().toLowerCase(), Form.NFC);

		if (value.length() != 2) 
			return ESTADO_PARA_SIGLA.getOrDefault(value, null);

		return UFs_VALIDAS.contains(value.toUpperCase())
				? value.toUpperCase()
				: null;
	}
}
