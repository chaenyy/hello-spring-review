package com.ce.spring2.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(String[].class) // javaType
@MappedJdbcTypes(JdbcType.VARCHAR) // jdbcType
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

	/**
	 * String[] -> VARCHAR
	 * - parameter가 null인 경우는 호출되지 않음
	 */
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType)
			throws SQLException {
		String value = String.join(",", parameter);
		ps.setString(i, value);
	}

	/**
	 * VARCHAR -> String[]
	 * - null이 올 수도 있기 때문에 분기처리 필요!
	 */
	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String value = rs.getString(columnName);
		return value != null ? value.split(",") : null;
	}

	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = rs.getString(columnIndex);
		return value != null ? value.split(",") : null;
	}

	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = cs.getString(columnIndex);
		return value != null ? value.split(",") : null;
	}

}
