class CommunityLogger(clazz: Class<*>) : AppLogger() {

    override val logger = getLogger(clazz)

    fun logCantFind() = logger.info("There is nothing to find")

    fun logInitialisation() = logger.info("Initialised")
}