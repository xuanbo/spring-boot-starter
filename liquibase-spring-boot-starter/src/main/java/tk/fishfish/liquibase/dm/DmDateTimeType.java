package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.DateTimeType;

import java.util.Locale;

/**
 * 达梦 datetime 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "datetime", aliases = {"java.sql.Types.DATETIME", "java.util.Date", "smalldatetime", "datetime2"}, minParameters = 0, maxParameters = 1, priority = 5)
public class DmDateTimeType extends DateTimeType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if (getRawDefinition().toUpperCase(Locale.US).contains("TIME ZONE")) {
            // remove the last data type size that comes from column size
            return new DatabaseDataType(getRawDefinition().replaceFirst("\\(\\d+\\)$", ""));
        }
        return new DatabaseDataType("TIMESTAMP", getParameters());
    }

}
