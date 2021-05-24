class MenuLogger(clazz: Class<*>) : AppLogger() {

    override val logger = getLogger(clazz)

    fun logNoExampleDir() = logger.info("Can't find example directory")
}