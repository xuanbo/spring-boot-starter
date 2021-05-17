package tk.fishfish.dbrowser.database

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * 抽象实现
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
abstract class AbstractSessionFactory : SessionFactory {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun ping(props: Props): String? = runBlocking {
        try {
            withTimeout(3000) {
                GlobalScope.async(Dispatchers.IO) {
                    doPing(props)
                }.await()
            }
        } catch (e: TimeoutCancellationException) {
            e.message
        }
    }

    private fun doPing(props: Props): String? {
        val jdbcUrl = props.get("jdbcUrl") as String
        val username = props.get("username") as String?
        val password = props.get("password") as String?
        var con: Connection? = null
        return try {
            con = DriverManager.getConnection(jdbcUrl, username, password)
            null
        } catch (e: SQLException) {
            e.message
        } finally {
            logger.debug("close Connection")
            try {
                con?.close()
            } catch (e: SQLException) {
                logger.warn("close Connection failed", e)
            }
        }
    }

}