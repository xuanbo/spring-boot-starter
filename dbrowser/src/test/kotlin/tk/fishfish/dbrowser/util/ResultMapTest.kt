package tk.fishfish.dbrowser.util

import org.junit.Test

/**
 * 测试
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
class ResultMapTest {

    @Test
    fun map() {
        val list = ResultMap.map(
            mutableListOf(
                mapOf(
                    "id" to "1",
                    "name" to "湖北",
                    "a.name1" to "武汉",
                    "a.name2" to "洪山",
                ),
                mapOf(
                    "id" to "1",
                    "name" to "湖北",
                    "a.name1" to "天门",
                    "a.name2" to "岳口"
                ),
                mapOf(
                    "id" to "2",
                    "name" to "湖南",
                    "a.name1" to "长沙",
                    "a.name2" to "岳麓",
                ),
            )
        )
        println(list)
    }

}