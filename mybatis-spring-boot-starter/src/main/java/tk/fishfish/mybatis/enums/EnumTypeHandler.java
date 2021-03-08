package tk.fishfish.mybatis.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举类型TypeHandler
 *
 * @author 奔波儿灞
 * @version 1.3.0
 */
@MappedTypes(value = EnumType.class)
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.INTEGER})
public class EnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public Class<E> getType() {
        return this.type;
    }

    public EnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.type = type;
        }
    }

    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        if (parameter instanceof EnumType) {
            if (jdbcType == null) {
                ps.setString(i, ((EnumType) parameter).getValue());
            } else {
                ps.setObject(i, ((EnumType) parameter).getValue(), jdbcType.TYPE_CODE);
            }
        } else if (jdbcType == null) {
            ps.setString(i, parameter.name());
        } else {
            ps.setObject(i, parameter.name(), jdbcType.TYPE_CODE);
        }

    }

    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : valueOf(this.type, s);
    }

    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return s == null ? null : valueOf(this.type, s);
    }

    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return s == null ? null : valueOf(this.type, s);
    }

    private <E extends Enum<E>> E valueOf(Class<E> type, String value) {
        E[] constants = type.getEnumConstants();
        for (E e : constants) {
            if (!(e instanceof EnumType)) {
                continue;
            }
            if (((EnumType) e).getValue().equals(value)) {
                return e;
            }
        }
        return null;
    }

}
