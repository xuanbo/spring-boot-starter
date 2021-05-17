package tk.fishfish.dbrowser.database

import org.junit.After
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tk.fishfish.dbrowser.database.dameng.DmSession

/**
 * 测试
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
class DmSessionTest {

    private val logger: Logger = LoggerFactory.getLogger(DmSessionTest::class.java)

    private var session: Session = DmSession(
        Props()
            .option("jdbcUrl", "jdbc:dm://192.168.101.32:5236")
            .option("username", "egova")
            .option("password", "egova2021")
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
        val tables = session.tables("EGOVA")
        logger.info("tables: {}", tables)
    }

    @Test
    fun table() {
        val table = session.table("EGOVA", "cata_resource")
        logger.info("table: {}", table)
    }

    @Test
    fun count() {
        val count = session.count("EGOVA", "SELECT * FROM pump_stage")
        logger.info("count: {}", count)
    }

    @Test
    fun query() {
        val list = session.query(
            "EGOVA",
            "SELECT id, columns, raw, type, logType, execId, jobId, scheduleId, error, timestamp, createTime FROM pump_stage ORDER BY createTime DESC"
        )
        logger.info("list: {}", list)
    }

    @Test
    fun execute() {
        var result =
            session.execute("EGOVA", "SELECT * FROM cata_resource WHERE id = 'ec9bf56d-766b-4873-94a1-7a33554670c2'")
        logger.info("result: {}", result)

        result = session.execute(
            "EGOVA",
            "UPDATE cata_resource SET status = '0' where id = 'ceb78333-a8af-453f-84f6-5c8e4539d154'"
        )
        logger.info("result: {}", result)
    }

    @After
    fun close() {
        session.close()
    }

}