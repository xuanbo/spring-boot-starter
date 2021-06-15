package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.TimestampType;

/**
 * 达梦 timestamp 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "timestamp", aliases = {"java.sql.Types.TIMESTAMP", "java.sql.Timestamp", "timestamptz"}, minParameters = 0, maxParameters = 1, priority = 5)
public class DmTimestampType extends TimestampType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        return new DatabaseDataType("TIMESTAMP");
    }

}
