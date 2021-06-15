package tk.fishfish.liquibase.dm;

import liquibase.CatalogAndSchema;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.ExecutorService;
import liquibase.logging.LogService;
import liquibase.logging.LogType;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.core.RawCallStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.util.JdbcUtils;
import liquibase.util.StringUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 达梦数据库
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
public class DmDatabase extends AbstractJdbcDatabase {

    public static final String PRODUCT_NAME = "DM DBMS";

    private Set<String> reservedWords = new HashSet<>();

    private Boolean canAccessDbaRecycleBin;
    private Integer databaseMajorVersion;
    private Integer databaseMinorVersion;

    /**
     * Default constructor for an object that represents the Oracle Database DBMS.
     */
    public DmDatabase() {
        super.unquotedObjectsAreUppercased = true;
        super.setCurrentDateTimeFunction("SYSTIMESTAMP");
        // Setting list of Oracle's native functions
        dateFunctions.add(new DatabaseFunction("SYSDATE"));
        dateFunctions.add(new DatabaseFunction("SYSTIMESTAMP"));
        dateFunctions.add(new DatabaseFunction("CURRENT_TIMESTAMP"));
        super.sequenceNextValueFunction = "%s.nextval";
        super.sequenceCurrentValueFunction = "%s.currval";
        reservedWords.add("SCHEMA");
    }

    @Override
    public int getPriority() {
        return 6;
    }

    @Override
    public void setConnection(DatabaseConnection conn) {
        //noinspection HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,
        // HardCodedStringLiteral
        //noinspection HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,
        // HardCodedStringLiteral
        reservedWords.addAll(Arrays.asList("GROUP", "USER", "SESSION", "PASSWORD", "RESOURCE", "START", "SIZE", "UID", "DESC", "ORDER")); //more reserved words not returned by driver

        Connection sqlConn = null;
        if (!(conn instanceof OfflineConnection)) {
            try {
                /*
                 * Don't try to call getWrappedConnection if the conn instance is
                 * is not a JdbcConnection. This happens for OfflineConnection.
                 * see https://liquibase.jira.com/browse/CORE-2192
                 */
                if (conn instanceof JdbcConnection) {
                    sqlConn = ((JdbcConnection) conn).getWrappedConnection();
                }
            } catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }

            if (sqlConn != null) {
                try {
                    //noinspection HardCodedStringLiteral
                    reservedWords.addAll(Arrays.asList(sqlConn.getMetaData().getSQLKeywords().toUpperCase().split(",\\s*")));
                } catch (SQLException e) {
                    //noinspection HardCodedStringLiteral
                    LogService.getLog(getClass()).info(LogType.LOG, "Could get sql keywords on OracleDatabase: " + e.getMessage());
                    //can not get keywords. Continue on
                }
                try {
                    Method method = sqlConn.getClass().getMethod("setRemarksReporting", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(sqlConn, true);
                } catch (Exception e) {
                    //noinspection HardCodedStringLiteral
                    LogService.getLog(getClass()).info(LogType.LOG, "Could not set remarks reporting on OracleDatabase: " + e.getMessage());

                    //cannot set it. That is OK
                }

                Statement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = sqlConn.createStatement();
                    //noinspection HardCodedStringLiteral
                    resultSet = statement.executeQuery("SELECT value FROM v$parameter WHERE name = 'compatible'");
                    String compatibleVersion = null;
                    if (resultSet.next()) {
                        //noinspection HardCodedStringLiteral
                        compatibleVersion = resultSet.getString("value");
                    }
                    if (compatibleVersion != null) {
                        Matcher majorVersionMatcher = Pattern.compile("(\\d+)\\.(\\d+)\\..*").matcher(compatibleVersion);
                        if (majorVersionMatcher.matches()) {
                            this.databaseMajorVersion = Integer.valueOf(majorVersionMatcher.group(1));
                            this.databaseMinorVersion = Integer.valueOf(majorVersionMatcher.group(2));
                        }
                    }
                } catch (SQLException e) {
                    @SuppressWarnings("HardCodedStringLiteral") String message = "Cannot read from v$parameter: " + e.getMessage();

                    //noinspection HardCodedStringLiteral
                    LogService.getLog(getClass()).info(LogType.LOG, "Could not set check compatibility mode on OracleDatabase, assuming not running in any sort of compatibility mode: " + message);
                } finally {
                    JdbcUtils.close(resultSet, statement);
                }


            }
        }
        super.setConnection(conn);
    }

    @Override
    public String getShortName() {
        //noinspection HardCodedStringLiteral
        return "dm";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        //noinspection HardCodedStringLiteral
        return PRODUCT_NAME;
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        if (databaseMajorVersion == null) {
            return super.getDatabaseMajorVersion();
        } else {
            return databaseMajorVersion;
        }
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        if (databaseMinorVersion == null) {
            return super.getDatabaseMinorVersion();
        } else {
            return databaseMinorVersion;
        }
    }

    @Override
    public Integer getDefaultPort() {
        return 5236;
    }

    @Override
    public String getJdbcCatalogName(CatalogAndSchema schema) {
        return null;
    }

    @Override
    public String getJdbcSchemaName(CatalogAndSchema schema) {
        return correctObjectName((schema.getCatalogName() == null) ? schema.getSchemaName() : schema.getCatalogName(), Schema.class);
    }

    @Override
    protected String getAutoIncrementClause(final String generationType, final Boolean defaultOnNull) {
        if (StringUtils.isEmpty(generationType)) {
            return "";
        }

        String autoIncrementClause = "GENERATED %s AS IDENTITY"; // %s -- [ ALWAYS | BY DEFAULT [ ON NULL ] ]
        String generationStrategy = generationType;
        if (Boolean.TRUE.equals(defaultOnNull) && generationType.toUpperCase().equals("BY DEFAULT")) {
            generationStrategy += " ON NULL";
        }
        return String.format(autoIncrementClause, generationStrategy);
    }

    @Override
    public String generatePrimaryKeyName(String tableName) {
        if (tableName.length() > 27) {
            //noinspection HardCodedStringLiteral
            return "PK_" + tableName.toUpperCase(Locale.US).substring(0, 27);
        } else {
            //noinspection HardCodedStringLiteral
            return "PK_" + tableName.toUpperCase(Locale.US);
        }
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return true;
    }

    @Override
    public boolean isReservedWord(String objectName) {
        return reservedWords.contains(objectName.toUpperCase());
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    /**
     * Oracle supports catalogs in liquibase terms
     *
     * @return false
     */
    @Override
    public boolean supportsSchemas() {
        return false;
    }

    @Override
    protected String getConnectionCatalogName() throws DatabaseException {
        if (getConnection() instanceof OfflineConnection) {
            return getConnection().getCatalog();
        }
        try {
            //noinspection HardCodedStringLiteral
            return ExecutorService.getInstance().getExecutor(this).queryForObject(new RawCallStatement("select sys_context( 'userenv', 'current_schema' ) from dual"), String.class);
        } catch (Exception e) {
            //noinspection HardCodedStringLiteral
            LogService.getLog(getClass()).info(LogType.LOG, "Error getting default schema", e);
        }
        return null;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return PRODUCT_NAME.equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public String getDefaultDriver(String url) {
        return "dm.jdbc.driver.DmDriver";
    }

    @Override
    public String getDefaultCatalogName() {//NOPMD
        return (super.getDefaultCatalogName() == null) ? null : super.getDefaultCatalogName().toUpperCase(Locale.US);
    }

    /**
     * <p>Returns an Oracle date literal with the same value as a string formatted using ISO 8601.</p>
     *
     * <p>Convert an ISO8601 date string to one of the following results:
     * to_date('1995-05-23', 'YYYY-MM-DD')
     * to_date('1995-05-23 09:23:59', 'YYYY-MM-DD HH24:MI:SS')</p>
     * <p>
     * Implementation restriction:<br>
     * Currently, only the following subsets of ISO8601 are supported:<br>
     * <ul>
     * <li>YYYY-MM-DD</li>
     * <li>YYYY-MM-DDThh:mm:ss</li>
     * </ul>
     */
    @Override
    public String getDateLiteral(String isoDate) {
        String normalLiteral = super.getDateLiteral(isoDate);

        if (isDateOnly(isoDate)) {
            StringBuffer val = new StringBuffer();
            //noinspection HardCodedStringLiteral
            val.append("TO_DATE(");
            val.append(normalLiteral);
            //noinspection HardCodedStringLiteral
            val.append(", 'YYYY-MM-DD')");
            return val.toString();
        } else if (isTimeOnly(isoDate)) {
            StringBuffer val = new StringBuffer();
            //noinspection HardCodedStringLiteral
            val.append("TO_DATE(");
            val.append(normalLiteral);
            //noinspection HardCodedStringLiteral
            val.append(", 'HH24:MI:SS')");
            return val.toString();
        } else if (isTimestamp(isoDate)) {
            StringBuffer val = new StringBuffer(26);
            //noinspection HardCodedStringLiteral
            val.append("TO_TIMESTAMP(");
            val.append(normalLiteral);
            //noinspection HardCodedStringLiteral
            val.append(", 'YYYY-MM-DD HH24:MI:SS.FF')");
            return val.toString();
        } else if (isDateTime(isoDate)) {
            int seppos = normalLiteral.lastIndexOf('.');
            if (seppos != -1) {
                normalLiteral = normalLiteral.substring(0, seppos) + "'";
            }
            StringBuffer val = new StringBuffer(26);
            //noinspection HardCodedStringLiteral
            val.append("TO_DATE(");
            val.append(normalLiteral);
            //noinspection HardCodedStringLiteral
            val.append(", 'YYYY-MM-DD HH24:MI:SS')");
            return val.toString();
        }
        //noinspection HardCodedStringLiteral
        return "UNSUPPORTED:" + isoDate;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example == null) {
            return false;
        }

        if (this.isLiquibaseObject(example)) {
            return false;
        }

        if (example instanceof Schema) {
            //noinspection HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral
            if ("SYSTEM".equals(example.getName()) || "SYS".equals(example.getName()) || "CTXSYS".equals(example.getName()) || "XDB".equals(example.getName())) {
                return true;
            }
            //noinspection HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral
            if ("SYSTEM".equals(example.getSchema().getCatalogName()) || "SYS".equals(example.getSchema().getCatalogName()) || "CTXSYS".equals(example.getSchema().getCatalogName()) || "XDB".equals(example.getSchema().getCatalogName())) {
                return true;
            }
        } else if (isSystemObject(example.getSchema())) {
            return true;
        }
        if (example instanceof Catalog) {
            //noinspection HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral
            if (("SYSTEM".equals(example.getName()) || "SYS".equals(example.getName()) || "CTXSYS".equals(example.getName()) || "XDB".equals(example.getName()))) {
                return true;
            }
        } else if (example.getName() != null) {
            //noinspection HardCodedStringLiteral
            if (example.getName().startsWith("BIN$")) { //oracle deleted table
                boolean filteredInOriginalQuery = this.canAccessDbaRecycleBin();
                if (!filteredInOriginalQuery) {
                    filteredInOriginalQuery = StringUtils.trimToEmpty(example.getSchema().getName()).equalsIgnoreCase(this.getConnection().getConnectionUserName());
                }

                if (filteredInOriginalQuery) {
                    return !((example instanceof PrimaryKey) || (example instanceof Index) || (example instanceof
                            liquibase.statement.UniqueConstraint));
                } else {
                    return true;
                }
            } else //noinspection HardCodedStringLiteral
                if (example.getName().startsWith("AQ$")) { //oracle AQ tables
                    return true;
                } else //noinspection HardCodedStringLiteral
                    if (example.getName().startsWith("DR$")) { //oracle index tables
                        return true;
                    } else //noinspection HardCodedStringLiteral
                        if (example.getName().startsWith("SYS_IOT_OVER")) { //oracle system table
                            return true;
                        } else //noinspection HardCodedStringLiteral,HardCodedStringLiteral
                            if ((example.getName().startsWith("MDRT_") || example.getName().startsWith("MDRS_")) && example.getName().endsWith("$")) {
                                // CORE-1768 - Oracle creates these for spatial indices and will remove them when the index is removed.
                                return true;
                            } else //noinspection HardCodedStringLiteral
                                if (example.getName().startsWith("MLOG$_")) { //Created by materliaized view logs for every table that is part of a materialized view. Not available for DDL operations.
                                    return true;
                                } else //noinspection HardCodedStringLiteral
                                    if (example.getName().startsWith("RUPD$_")) { //Created by materialized view log tables using primary keys. Not available for DDL operations.
                                        return true;
                                    } else //noinspection HardCodedStringLiteral
                                        if (example.getName().startsWith("WM$_")) { //Workspace Manager backup tables.
                                            return true;
                                        } else //noinspection HardCodedStringLiteral
                                            if ("CREATE$JAVA$LOB$TABLE".equals(example.getName())) { //This table contains the name of the Java object, the date it was loaded, and has a BLOB column to store the Java object.
                                                return true;
                                            } else //noinspection HardCodedStringLiteral
                                                if ("JAVA$CLASS$MD5$TABLE".equals(example.getName())) { //This is a hash table that tracks the loading of Java objects into a schema.
                                                    return true;
                                                } else //noinspection HardCodedStringLiteral
                                                    if (example.getName().startsWith("ISEQ$$_")) { //System-generated sequence
                                                        return true;
                                                    } else //noinspection HardCodedStringLiteral
                                                        if (example.getName().startsWith("USLOG$")) { //for update materialized view
                                                            return true;
                                                        } else if (example.getName().startsWith("SYS_FBA")) { //for Flashback tables
                                                            return true;
                                                        }
        }

        return super.isSystemObject(example);
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }

    @Override
    public boolean supportsAutoIncrement() {
        // Oracle supports Identity beginning with version 12c
        boolean isAutoIncrementSupported = false;

        try {
            if (getDatabaseMajorVersion() >= 12) {
                isAutoIncrementSupported = true;
            }

            // Returning true will generate create table command with 'IDENTITY' clause, example:
            // CREATE TABLE AutoIncTest (IDPrimaryKey NUMBER(19) GENERATED BY DEFAULT AS IDENTITY NOT NULL, TypeID NUMBER(3) NOT NULL, Description NVARCHAR2(50), CONSTRAINT PK_AutoIncTest PRIMARY KEY (IDPrimaryKey));

            // While returning false will continue to generate create table command without 'IDENTITY' clause, example:
            // CREATE TABLE AutoIncTest (IDPrimaryKey NUMBER(19) NOT NULL, TypeID NUMBER(3) NOT NULL, Description NVARCHAR2(50), CONSTRAINT PK_AutoIncTest PRIMARY KEY (IDPrimaryKey));

        } catch (DatabaseException ex) {
            isAutoIncrementSupported = false;
        }

        return isAutoIncrementSupported;
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return false;
    }

    @Override
    public int getDataTypeMaxParameters(String dataTypeName) {
        //noinspection HardCodedStringLiteral
        if ("BINARY_FLOAT".equals(dataTypeName.toUpperCase())) {
            return 0;
        }
        //noinspection HardCodedStringLiteral
        if ("BINARY_DOUBLE".equals(dataTypeName.toUpperCase())) {
            return 0;
        }
        return super.getDataTypeMaxParameters(dataTypeName);
    }

    @Override
    public boolean jdbcCallsCatalogsSchemas() {
        return true;
    }

    @Override
    public String generateDatabaseFunctionValue(DatabaseFunction databaseFunction) {
        //noinspection HardCodedStringLiteral
        if ((databaseFunction != null) && "current_timestamp".equalsIgnoreCase(databaseFunction.toString())) {
            return databaseFunction.toString();
        }
        if ((databaseFunction instanceof SequenceNextValueFunction) || (databaseFunction instanceof
                SequenceCurrentValueFunction)) {
            String quotedSeq = super.generateDatabaseFunctionValue(databaseFunction);
            // replace "myschema.my_seq".nextval with "myschema"."my_seq".nextval
            return quotedSeq.replaceFirst("\"([^\\.\"]+)\\.([^\\.\"]+)\"", "\"$1\".\"$2\"");

        }

        return super.generateDatabaseFunctionValue(databaseFunction);
    }

    @Override
    public ValidationErrors validate() {
        ValidationErrors errors = super.validate();
        DatabaseConnection connection = getConnection();
        if ((connection == null) || (connection instanceof OfflineConnection)) {
            //noinspection HardCodedStringLiteral
            LogService.getLog(getClass()).info(LogType.LOG, "Cannot validate offline database");
            return errors;
        }

        if (!canAccessDbaRecycleBin()) {
            errors.addWarning(getDbaRecycleBinWarning());
        }

        return errors;

    }

    public String getDbaRecycleBinWarning() {
        //noinspection HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral,
        // HardCodedStringLiteral
        //noinspection HardCodedStringLiteral,HardCodedStringLiteral,HardCodedStringLiteral
        return "Liquibase needs to access the DBA_RECYCLEBIN table so we can automatically handle the case where " +
                "constraints are deleted and restored. Since Oracle doesn't properly restore the original table names " +
                "referenced in the constraint, we use the information from the DBA_RECYCLEBIN to automatically correct this" +
                " issue.\n" +
                "\n" +
                "The user you used to connect to the database (" + getConnection().getConnectionUserName() +
                ") needs to have \"SELECT ON SYS.DBA_RECYCLEBIN\" permissions set before we can perform this operation. " +
                "Please run the following SQL to set the appropriate permissions, and try running the command again.\n" +
                "\n" +
                "     GRANT SELECT ON SYS.DBA_RECYCLEBIN TO " + getConnection().getConnectionUserName() + ";";
    }

    public boolean canAccessDbaRecycleBin() {
        if (canAccessDbaRecycleBin == null) {
            DatabaseConnection connection = getConnection();
            if ((connection == null) || (connection instanceof OfflineConnection)) {
                return false;
            }

            Statement statement = null;
            try {
                statement = ((JdbcConnection) connection).createStatement();
                @SuppressWarnings("HardCodedStringLiteral") ResultSet resultSet = statement.executeQuery("select 1 from dba_recyclebin where 0=1");
                resultSet.close(); //don't need to do anything with the result set, just make sure statement ran.
                this.canAccessDbaRecycleBin = true;
            } catch (Exception e) {
                //noinspection HardCodedStringLiteral
                if ((e instanceof SQLException) && e.getMessage().startsWith("ORA-00942")) { //ORA-00942: table or view does not exist
                    this.canAccessDbaRecycleBin = false;
                } else {
                    //noinspection HardCodedStringLiteral
                    LogService.getLog(getClass()).warning(LogType.LOG, "Cannot check dba_recyclebin access", e);
                    this.canAccessDbaRecycleBin = false;
                }
            } finally {
                JdbcUtils.close(null, statement);
            }
        }

        return canAccessDbaRecycleBin;
    }

    @Override
    public boolean supportsNotNullConstraintNames() {
        return true;
    }

}
