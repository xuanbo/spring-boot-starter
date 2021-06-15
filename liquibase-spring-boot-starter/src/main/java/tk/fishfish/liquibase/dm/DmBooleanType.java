package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.BooleanType;

/**
 * 达梦 boolean 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "boolean", aliases = {"java.sql.Types.BOOLEAN", "java.lang.Boolean", "bit", "bool"}, minParameters = 0, maxParameters = 0, priority = 5)
public class DmBooleanType extends BooleanType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        return new DatabaseDataType("NUMBER", 1);
    }

    @Override
    protected boolean isNumericBoolean(Database database) {
        return true;
    }

    @Override
    public String getFalseBooleanValue(Database database) {
        return "0";
    }

    @Override
    public String getTrueBooleanValue(Database database) {
        return "1";
    }

}
