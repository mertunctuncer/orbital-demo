package dev.peopo.orbitaldemo.economy.exception

import java.lang.RuntimeException

class PlayerNotOnlineException(override val message: String? = null) : RuntimeException(message)