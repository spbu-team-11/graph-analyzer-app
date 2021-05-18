class CentralityLogger(clazz: Class<*>) : AppLogger() {

    override val logger = getLogger(clazz)

    fun logEmptyGraph() = logger.info("Graph is empty")

    fun logInitialisation() = logger.info("Initialised")
}