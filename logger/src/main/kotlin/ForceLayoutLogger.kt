import org.slf4j.LoggerFactory

class ForceLayoutLogger(clazz: Class<*>) : AppLogger() {

    override val logger = getLogger(clazz)

    fun logCantPlace() = logger.info("There is nothing to place")

    fun logInitialisation() = logger.info("Initialised")

    fun logIteration(i: Int) = logger.info("$i iteration" + (if (i > 1) "s" else "") + " behind")
}