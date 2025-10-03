package br.app.harppia.defaults.custom.converters;

import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

/**
 * Um tipo customizado do Hibernate 6 para mapear Enums Java para tipos ENUM
 * nativos do PostgreSQL de forma genérica.
 */
public class PostgreSQLEnumJdbcType extends VarcharJdbcType {

	/**
	 * Versiona essa classe para serialização de objetos. O UID aumenta em 1 a cada
	 * mudança expressiva na estrutura da classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Sobrescreve o tipo SQL padrão (VARCHAR) retornado por VarcharJdbcType. Ao
	 * retornar NAMED_ENUM, informamos ao driver JDBC para tratar o valor como um
	 * tipo ENUM nativo do PostgreSQL.
	 */
	@Override
	public int getDefaultSqlTypeCode() {
		return SqlTypes.NAMED_ENUM;
	}
}