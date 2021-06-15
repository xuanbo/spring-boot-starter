package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.TinyIntType;

/**
 * 达梦 int 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "tinyint", aliases = "java.sql.Types.TINYINT", minParameters = 0, maxParameters = 1, priority = 5)
public class DmTinyIntType extends TinyIntType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        return new DatabaseDataType("NUMBER", 3);
    }

}
