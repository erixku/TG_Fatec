package br.app.harppia.usuario.cadastro.converters;

import java.util.HashMap;
import java.util.Map;

import br.app.harppia.usuario.cadastro.utils.rules.EnumPersistivel;
import jakarta.persistence.AttributeConverter;

public abstract class ConversorEnumPadrao<E extends Enum<E> & EnumPersistivel> implements AttributeConverter<E, String>{

	private final Map<String, E> constantesDefinicoesEnum = new HashMap<>();
	
	public ConversorEnumPadrao(Class<E> classeEnum) {
		for(E valorFixo : classeEnum.getEnumConstants()) {
			constantesDefinicoesEnum.put(valorFixo.getValorCustomizado(), valorFixo);
		}
	}
	
	@Override
	public String convertToDatabaseColumn(E attribute) {
		return (attribute != null) ? attribute.getValorCustomizado() : null;
	}

	@Override
	public E convertToEntityAttribute(String dbData) {
		if(dbData == null || dbData.trim().isEmpty()) return null;
		
		E resultado = constantesDefinicoesEnum.get(dbData);
		
		return (resultado != null) ? resultado : null;
	}
}
