package tk.fishfish.dbrowser.database

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tk.fishfish.dbrowser.exception.SessionException
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

/**
 * 会话管理
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
interface SessionHub {

    fun get(key: String): Session

    fun put(key: String, session: Session)

    fun remove(key: String)

}

class DefaultSessionHub : SessionHub {

    private val logger: Logger = LoggerFactory.getLogger(DefaultSessionHub::class.java)

    private val store = ConcurrentHashMap<String, Session>()

    override fun get(key: String): Session = store[key] ?: throw SessionException("数据库配置不存在或连接不合法")

    override fun put(key: String, session: Session) {
        try {
            store.put(key, session)?.close()
        } catch (e: IOException) {
            logger.warn("close session failed: {}", e.message)
        }
    }

    override fun remove(key: String) {
        try {
            store.remove(key)?.close()
        } catch (e: IOException) {
            logger.warn("close session failed: {}", e.message)
        }
    }

}