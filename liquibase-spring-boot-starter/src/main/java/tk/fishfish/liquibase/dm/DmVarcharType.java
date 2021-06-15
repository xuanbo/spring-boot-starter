package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.VarcharType;

/**
 * 达梦 varchar 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "varchar", aliases = {"java.sql.Types.VARCHAR", "java.lang.String", "varchar2", "character varying"}, minParameters = 0, maxParameters = 1, priority = 5)
public class DmVarcharType extends VarcharType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        return new DatabaseDataType("VARCHAR2", getParameters());
    }

}
