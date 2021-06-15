package tk.fishfish.liquibase.dm;

import liquibase.database.Database;
import liquibase.datatype.DataTypeInfo;
import liquibase.datatype.DatabaseDataType;
import liquibase.datatype.core.BlobType;
import liquibase.util.StringUtils;

import java.util.Locale;

/**
 * 达梦 blob 适配
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
@DataTypeInfo(name = "blob", aliases = {"longblob", "longvarbinary", "java.sql.Types.BLOB", "java.sql.Types.LONGBLOB", "java.sql.Types.LONGVARBINARY", "java.sql.Types.VARBINARY", "java.sql.Types.BINARY", "varbinary", "binary", "image", "tinyblob", "mediumblob"}, minParameters = 0, maxParameters = 1, priority = 5)
public class DmBlobType extends BlobType {

    @Override
    public boolean supports(Database database) {
        return database instanceof DmDatabase;
    }

    @Override
    public DatabaseDataType toDatabaseDataType(Database database) {
        String originalDefinition = StringUtils.trimToEmpty(getRawDefinition());
        if (originalDefinition.toLowerCase(Locale.US).startsWith("bfile")) {
            return new DatabaseDataType("BFILE");
        }
        if (originalDefinition.toLowerCase(Locale.US).startsWith("raw") ||
                originalDefinition.toLowerCase(Locale.US).startsWith("binary") ||
                originalDefinition.toLowerCase(Locale.US).startsWith("varbinary")) {
            return new DatabaseDataType("RAW", getParameters());
        }
        return new DatabaseDataType("BLOB");
    }

}
