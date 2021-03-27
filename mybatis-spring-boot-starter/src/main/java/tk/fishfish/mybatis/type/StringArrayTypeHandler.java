package tk.fishfish.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * String[]类型处理
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter == null ? null : String.join(",", parameter));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String value = rs.getString(columnName);
        if (value == null) {
            return null;
        }
        return value.split(",");
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String value = rs.getString(columnIndex);
        if (value == null) {
            return null;
        }
        return value.split(",");
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String value = cs.getString(columnIndex);
        if (value == null) {
            return null;
        }
        return value.split(",");
    }

}
