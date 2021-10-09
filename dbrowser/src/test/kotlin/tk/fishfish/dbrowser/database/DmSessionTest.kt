package tk.fishfish.dbrowser.database

import com.fasterxml.jackson.databind.ObjectMapper
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
        val table = session.table("EGOVA", "databasechangeloglock")
        logger.info("table: {}", table)
    }

    @Test
    fun count() {
        val result1 = session.query("EGOVA", "select * from com_schema", 1, 1000)
        val om = ObjectMapper()
        logger.info("result: {}", result1)
        val json = om.writeValueAsString(result1)
        logger.info("json: {}", json)
//        val result2 = session.execute("EGOVA", "update databasechangeloglock set LOCKED = 0")
//        logger.info("result: {}", result2)
    }

    @Test
    fun query() {
        val list = session.query(
            "EGOVA",
            "select j.name as name, sum(sl.totalReadRecords) as count from pump_schedule_log sl left join pump_job j on sl.jobId = j.id where sl.status = 2 group by j.name having sum(sl.totalReadRecords) is not null order by count desc"
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