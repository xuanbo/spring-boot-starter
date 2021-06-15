package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.DecimalType;

/**
 * 达梦 decimal 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "decimal", aliases = {"java.sql.Types.DECIMAL", "java.math.BigDecimal"}, minParameters = 0, maxParameters = 2, priority = 5)
public class DmDecimalType extends DecimalType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        return new DatabaseDataType("DECIMAL", getParameters());
    }

}
