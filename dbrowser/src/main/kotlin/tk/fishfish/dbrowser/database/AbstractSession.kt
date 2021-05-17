package tk.fishfish.dbrowser.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

/**
 * 抽象实现
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
abstract class AbstractSession(props: Props) : Session {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    private val dataSource: HikariDataSource

    init {
        val properties = Properties()
        props.foreach { k, v -> properties[k] = v }
        val config = HikariConfig(properties)
        dataSource = HikariDataSource(config)
    }

    override fun ping(): String? = runBlocking {
        try {
            withTimeout(3000) {
                GlobalScope.async(Dispatchers.IO) {
                    doPing()
                }.await()
            }
        } catch (e: TimeoutCancellationException) {
            e.message
        }
    }

    protected open fun doPing(): String? {
        var con: Connection? = null
        return try {
            con = dataSource.connection
            null
        } catch (e: SQLException) {
            e.message
        } finally {
            close(con)
        }
    }

    override fun schemas(): List<String> {
        dataSource.connection.use { con ->
            return schemaProvider(con)
        }
    }

    override fun tables(schema: String): List<String> {
        dataSource.connection.use { con ->
            tablesProvider(con, schema).use { rs ->
                val schemas = mutableListOf<String>()
                while (rs.next()) {
                    val name = rs.getString("TABLE_NAME")
                    schemas.add(name)
                }
                return schemas
            }
        }
    }

    override fun table(schema: String, table: String): Table {
        dataSource.connection.use { con ->
            tableProvider(con, schema, table).use { rs ->
                val columns = mutableListOf<Column>()
                while (rs.next()) {
                    val map = mutableMapOf<String, Any?>()
                    val metadata = rs.metaData
                    val count = metadata.columnCount
                    for (i in 0 until count) {
                        val name = metadata.getColumnName(i + 1)
                        val value = rs.getObject(i + 1)
                        map[name] = value
                    }
                    val column = columnProvider(map)
                    columns.add(column)
                }
                return Table(schema, table, columns)
            }
        }
    }

    override fun count(schema: String, sql: String): Long {
        dataSource.connection.use { con ->
            setSchema(con, schema)
            con.createStatement().use { statement ->
                val countSql = "SELECT COUNT(*) FROM ($sql) TMP_COUNT"
                logger.debug("Execute SQL => {}", countSql)
                statement.executeQuery(countSql).use { rs ->
                    return if (rs.next()) {
                        rs.getLong(1)
                    } else {
                        0
                    }
                }
            }
        }
    }

    override fun query(schema: String, sql: String, page: Int, size: Int): List<Map<String, Any?>> {
        dataSource.connection.use { con ->
            setSchema(con, schema)
            con.createStatement().use { statement ->
                val pageSql = pageSql(sql, page, size)
                logger.debug("Execute SQL => {}", pageSql)
                statement.executeQuery(pageSql).use { return extractResultSet(it, size) }
            }
        }
    }

    override fun execute(schema: String, sql: String): SqlResult {
        dataSource.connection.use { con ->
            setSchema(con, schema)
            con.createStatement().use { statement ->
                statement.fetchSize = 100
                logger.debug("Execute SQL => {}", sql)
                if (statement.execute(sql)) {
                    statement.resultSet.use { rs ->
                        val rows = extractResultSet(rs, 100)
                        return QueryResult(rows)
                    }
                } else {
                    return UpdateResult(statement.updateCount)
                }
            }
        }
    }

    override fun close() {
        logger.debug("close HikariDataSource")
        try {
            dataSource.close()
        } catch (e: SQLException) {
            logger.warn("close HikariDataSource failed", e)
        }
    }

    protected open fun schemaProvider(con: Connection): List<String> {
        con.metaData.schemas.use { rs ->
            val schemas = mutableListOf<String>()
            while (rs.next()) {
                val name = rs.getString("TABLE_SCHEM")
                schemas.add(name)
            }
            return schemas
        }
    }

    protected open fun tablesProvider(con: Connection, schema: String): ResultSet =
        con.metaData.getTables(null, schema, "%", arrayOf("TABLE"))

    protected open fun tableProvider(con: Connection, schema: String, table: String): ResultSet =
        con.metaData.getColumns(null, schema, table, "%")

    protected abstract fun columnProvider(metadata: Map<String, Any?>): Column

    protected open fun setSchema(con: Connection, schema: String) {
        con.schema = schema
    }

    protected abstract fun pageSql(sql: String, page: Int, size: Int): String

    private fun close(con: Connection?) {
        logger.debug("close Connection")
        try {
            con?.close()
        } catch (e: SQLException) {
            logger.warn("close Connection failed: {}", e.message)
        }
    }

    private fun extractResultSet(rs: ResultSet, fetchSize: Int): List<Map<String, Any?>> {
        val list = mutableListOf<Map<String, Any?>>()
        var index = 0
        while (index <= fetchSize && rs.next()) {
            index++
            val map = mutableMapOf<String, Any?>()
            val metadata = rs.metaData
            val count = metadata.columnCount
            for (i in 0 until count) {
                val name = metadata.getColumnName(i + 1)
                val value = rs.getObject(i + 1)
                map[name] = value
            }
            list.add(map)
        }
        return list
    }

}