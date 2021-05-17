package tk.fishfish.dbrowser.database.dameng

import tk.fishfish.dbrowser.database.AbstractSessionFactory
import tk.fishfish.dbrowser.database.Props
import tk.fishfish.dbrowser.database.Session

/**
 * 达梦会话工厂
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
class DmSessionFactory : AbstractSessionFactory() {

    override fun support(jdbcUrl: String): Boolean {
        return jdbcUrl.startsWith("jdbc:dm://")
    }

    override fun create(props: Props): Session {
        props
            // 启动线程池不校验连接
            .optionIfAbsent("initializationFailTimeout", -1)
            // 最小连接0
            .optionIfAbsent("minimumIdle", 0)
            // 最大连接8
            .optionIfAbsent("maximumPoolSize", 8)
            // 等待连接池分配连接的最大时长
            .optionIfAbsent("connectionTimeout", 3 * 1000)
        return DmSession(props)
    }

}