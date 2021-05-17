package tk.fishfish.dbrowser.database

/**
 * Sql执行结果
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
interface SqlResult {

    fun getType(): String

}

class QueryResult(val rows: List<Map<String, Any?>>) : SqlResult {

    override fun getType(): String = "query"

    override fun toString(): String {
        return "{type: query, count: ${rows.size}}"
    }

}

class UpdateResult(val count: Int) : SqlResult {

    override fun getType(): String = "update"

    override fun toString(): String {
        return "{type: update, count: $count}"
    }

}