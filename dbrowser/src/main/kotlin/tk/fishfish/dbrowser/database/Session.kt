package tk.fishfish.dbrowser.database

import java.io.Closeable

/**
 * 会话
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
interface Session : Closeable {

    fun ping(): String?

    fun schemas(): List<String>

    fun tables(schema: String): List<String>

    fun table(schema: String, table: String): Table

    fun count(schema: String, sql: String): Long

    fun query(schema: String, sql: String, page: Int = 1, size: Int = 10): List<Map<String, Any?>>

    fun execute(schema: String, sql: String): SqlResult

}