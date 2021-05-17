package tk.fishfish.dbrowser.database.mysql

import tk.fishfish.dbrowser.database.AbstractSession
import tk.fishfish.dbrowser.database.Column
import tk.fishfish.dbrowser.database.Props
import java.sql.Connection
import java.sql.ResultSet

/**
 * MySQL实现
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
class MysqlSession(props: Props) : AbstractSession(props) {

    override fun schemaProvider(con: Connection): List<String> {
        con.metaData.catalogs.use { rs ->
            val schemas = mutableListOf<String>()
            while (rs.next()) {
                val name = rs.getString("TABLE_CAT")
                schemas.add(name)
            }
            return schemas
        }
    }

    override fun tablesProvider(con: Connection, schema: String): ResultSet =
        con.metaData.getTables(schema, null, "%", arrayOf("TABLE"))

    override fun tableProvider(con: Connection, schema: String, table: String): ResultSet =
        con.metaData.getColumns(schema, null, table, "%")

    override fun columnProvider(metadata: Map<String, Any?>): Column {
        val name = metadata["COLUMN_NAME"] as String
        val type = metadata["TYPE_NAME"] as String
        val size = metadata["COLUMN_SIZE"] as Int
        val decimal = metadata["DECIMAL_DIGITS"] as Int?
        val nullable = (metadata["IS_NULLABLE"] as String) == "YES"
        val remark = metadata["REMARKS"] as String?
        return Column(name, type, size, decimal, nullable, remark)
    }

    override fun setSchema(con: Connection, schema: String) {
        con.catalog = schema
    }

    override fun pageSql(sql: String, page: Int, size: Int): String {
        if (page <= 0) {
            return "SELECT * FROM ($sql) TMP_PAGE LIMIT $size"
        }
        return "SELECT * FROM ($sql) TMP_PAGE LIMIT $size OFFSET ${(page - 1) * size}"
    }

}