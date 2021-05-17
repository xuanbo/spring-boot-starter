package tk.fishfish.dbrowser.database

import org.junit.After
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tk.fishfish.dbrowser.database.mysql.MysqlSession

/**
 * 测试
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
class MysqlSessionTest {

    private val logger: Logger = LoggerFactory.getLogger(MysqlSessionTest::class.java)

    private var session: Session = MysqlSession(
        Props()
            .option(
                "jdbcUrl",
                "jdbc:mysql://localhost:3306/dataflow?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8"
            )
            .option("username", "root")
            .option("password", "123456")
    )

    @Test
    fun ping() {
        val message = session.ping()
        logger.info("ping: {}", message)
    }

    @Test
    fun schemas() {
        val schemas = session.schemas()
        logger.info("schemas: {}", schemas)
    }

    @Test
    fun tables() {
        val tables = session.tables("fish")
        logger.info("tables: {}", tables)
    }

    @Test
    fun table() {
        val table = session.table("fish", "databasechangelog")
        logger.info("table: {}", table)
    }

    @Test
    fun count() {
        val count = session.count("fish", "SELECT * FROM sys_user")
        logger.info("count: {}", count)
    }

    @Test
    fun query() {
        val list = session.query("fish", "SELECT * FROM sys_user")
        logger.info("list: {}", list)
    }

    @Test
    fun execute() {
        var result = session.execute("fish", "SELECT * FROM sys_user")
        logger.info("result: {}", result)

        result = session.execute("fish", "DELETE FROM sys_user WHERE id = 'admin'")
        logger.info("result: {}", result)
    }

    @After
    fun close() {
        session.close()
    }

}