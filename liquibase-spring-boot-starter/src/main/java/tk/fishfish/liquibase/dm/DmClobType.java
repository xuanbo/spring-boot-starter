package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.ClobType;
import liquibase.util.StringUtils;

import java.util.Locale;

/**
 * 达梦 clob 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "clob", aliases = {"longvarchar", "text", "longtext", "java.sql.Types.LONGVARCHAR", "java.sql.Types.CLOB", "nclob", "longnvarchar", "ntext", "java.sql.Types.LONGNVARCHAR", "java.sql.Types.NCLOB", "tinytext", "mediumtext"}, minParameters = 0, maxParameters = 0, priority = 5)
public class DmClobType extends ClobType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        String originalDefinition = StringUtils.trimToEmpty(getRawDefinition());
        if ("nclob".equals(originalDefinition.toLowerCase(Locale.US))) {
            return new DatabaseDataType("NCLOB");
        }
        return new DatabaseDataType("CLOB");
    }

}
