import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class AppLogger {

    protected fun getLogger(forClass: Class<*>): Logger = LoggerFactory.getLogger(forClass)

    protected open val logger: Logger = getLogger(javaClass)

    fun logStart() = logger.info("Started")

    fun logFinish() = logger.info("Finished")
}