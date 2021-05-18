class MainLogger(clazz: Class<*>) : AppLogger() {

    override val logger = getLogger(clazz)
}