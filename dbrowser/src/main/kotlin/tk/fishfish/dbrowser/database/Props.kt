package tk.fishfish.dbrowser.database

/**
 * 配置
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
class Props {

    private val props = mutableMapOf<String, Any?>()

    fun option(key: String, value: Any?): Props {
        props[key] = value
        return this
    }

    fun optionIfAbsent(key: String, value: Any?): Props {
        if (props[key] == null) {
            props[key] = value
        }
        return this
    }

    fun get(key: String): Any? = props[key]

    fun foreach(action: (String, Any?) -> Unit) {
        props.forEach { (k, v) -> action(k, v) }
    }

}