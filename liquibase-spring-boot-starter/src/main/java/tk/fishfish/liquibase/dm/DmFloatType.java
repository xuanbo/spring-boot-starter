package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.FloatType;

/**
 * 达梦 float 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "float", aliases = {"java.sql.Types.FLOAT", "java.lang.Float", "real", "java.sql.Types.REAL"}, minParameters = 0, maxParameters = 2, priority = 5)
public class DmFloatType extends FloatType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        return new DatabaseDataType("FLOAT", getParameters());
    }

}
