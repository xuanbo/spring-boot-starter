package tk.fishfish.dbrowser.database

import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 测试
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
class SessionFactoryTest {

    private val logger: Logger = LoggerFactory.getLogger(SessionFactoryTest::class.java)

    private val props = Props()
        .option(
            "jdbcUrl",
            "jdbc:mysql://194.168.101.22:3307/dataflow?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8"
        )
        .option("username", "root")
        .option("password", "123456")

    private val factory = SessionFactory.load(props.get("jdbcUrl") as String)

    @Test
    fun ping() {
        val ping = factory.ping(props)
        logger.info("ping: {}", ping)
        Thread.sleep(600 * 1000)
    }

}