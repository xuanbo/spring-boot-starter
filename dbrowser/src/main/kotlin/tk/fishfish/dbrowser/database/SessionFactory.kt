package tk.fishfish.dbrowser.database

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tk.fishfish.dbrowser.exception.SessionFactoryException
import java.util.*

/**
 * 会话工厂
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
interface SessionFactory {

    fun support(jdbcUrl: String): Boolean

    fun ping(props: Props): String?

    fun create(props: Props): Session

    fun order(): Int = 100

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(SessionFactory::class.java)

        private val factories = mutableListOf<SessionFactory>()

        init {
            val clazz = SessionFactory::class.java
            val loader = ServiceLoader.load(clazz, clazz.classLoader)
            loader.forEach {
                logger.info("Loading {} , order: {}", it.javaClass.name, it.order())
                factories.add(it)
            }
            factories.sortBy { it.order() }
        }

        fun load(jdbcUrl: String): SessionFactory {
            return factories.find { it.support(jdbcUrl) } ?: throw SessionFactoryException("未适配的驱动: $jdbcUrl")
        }

    }

}