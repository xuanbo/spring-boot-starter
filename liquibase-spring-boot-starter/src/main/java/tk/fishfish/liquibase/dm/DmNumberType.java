package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.NumberType;

/**
 * 达梦 number 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "number", aliases = {"numeric", "java.sql.Types.NUMERIC"}, minParameters = 0, maxParameters = 2, priority = 5)
public class DmNumberType extends NumberType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        if ((getParameters().length > 1) && "0".equals(getParameters()[0]) && "-127".equals(getParameters()[1])) {
            return new DatabaseDataType("NUMBER");
        } else {
            return new DatabaseDataType("NUMBER", getParameters());
        }
    }

}
