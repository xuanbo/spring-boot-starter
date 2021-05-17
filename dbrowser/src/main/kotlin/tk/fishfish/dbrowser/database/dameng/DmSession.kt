package tk.fishfish.dbrowser.database.dameng

import tk.fishfish.dbrowser.database.AbstractSession
import tk.fishfish.dbrowser.database.Column
import tk.fishfish.dbrowser.database.Props

/**
 * 达梦数据库
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
class DmSession(props: Props) : AbstractSession(props) {

    override fun columnProvider(metadata: Map<String, Any?>): Column {
        val name = metadata["COLUMN_NAME"] as String
        val type = metadata["TYPE_NAME"] as String
        val size = metadata["COLUMN_SIZE"] as Int
        val decimal = metadata["DECIMAL_DIGITS"] as Int?
        val nullable = (metadata["IS_NULLABLE"] as String) == "YES"
        val remark = metadata["REMARKS"] as String?
        return Column(name, type, size, decimal, nullable, remark)
    }

    override fun pageSql(sql: String, page: Int, size: Int): String {
        val offset = (page - 1) * size
        return "SELECT * FROM (SELECT TMP_PAGE.*, ROWNUM _rowNum FROM ($sql) TMP_PAGE WHERE ROWNUM <= ${offset + size}) WHERE _rowNum >= $offset"
    }

}