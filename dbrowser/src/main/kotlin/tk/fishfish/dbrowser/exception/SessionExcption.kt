package tk.fishfish.dbrowser.exception

/**
 * 会话异常
 *
 * @author 奔波儿灞
 * @version 1.5.0
 */
class SessionException(
    override val message: String,
    override val cause: Throwable? = null
) :
    RuntimeException(message, cause) {
}

class SessionFactoryException(
    override val message: String,
    override val cause: Throwable? = null
) :
    RuntimeException(message, cause) {
}