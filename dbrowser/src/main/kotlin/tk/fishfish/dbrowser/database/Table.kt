package tk.fishfish.dbrowser.database

/**
 * 表信息
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
data class Table(val schema: String, val name: String, val columns: List<Column>)

data class Column(
    val name: String,
    val type: String,
    val size: Int,
    val decimal: Int?,
    val nullable: Boolean,
    val remark: String?
)
