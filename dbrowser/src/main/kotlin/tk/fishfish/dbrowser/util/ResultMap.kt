package tk.fishfish.dbrowser.util

/**
 * 结果集映射
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
object ResultMap {

    fun map(list: List<Map<String, Any?>>): List<Map<String, Any?>> {
        if (list.isEmpty()) {
            return emptyList();
        }
        val result = mutableListOf<Map<String, Any?>>()
        if (list is MutableList) {
            list
        } else {
            ArrayList<Map<String, Any?>>(list)
        }.map {
            val map = mutableMapOf<String, Any?>()
            it.forEach { (key, value) ->
                if (key.isEmpty() || key.last() == '.') {
                    map[key] = value
                }
                val index = key.indexOfLast { c -> c == '.' }
                if (index == -1) {
                    map[key] = value
                } else {
                    val newKey = key.substring(0, index)
                    val childKey = key.substring(index + 1)
                    val child = map.computeIfAbsent(newKey) { mutableMapOf<String, Any?>() } as MutableMap<String, Any?>
                    child[childKey] = value
                }
            }
            map
        }.groupBy {
            it.filter { (_, value) ->
                value !is Map<*, *>
            }
        }.forEach { (key, list) ->
            val map = mutableMapOf<String, Any?>()
            key.forEach { (k, v) -> map[k] = v }
            list.forEach { m ->
                m.forEach { (k, v) ->
                    if (v is Map<*, *>) {
                        val children = map.computeIfAbsent(k) { mutableListOf<Any?>() } as MutableList<Any?>
                        children.add(v)
                        map[k] = children
                    }
                }
            }
            result.add(map)
        }
        return result
    }

}