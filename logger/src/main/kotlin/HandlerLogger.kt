class HandlerLogger(clazz: Class<*>) : AppLogger() {

    override val logger = getLogger(clazz)

    fun logSave() = logger.info("Graph saved successfully")

    fun logOpen() = logger.info("Graph opened successfully")
}