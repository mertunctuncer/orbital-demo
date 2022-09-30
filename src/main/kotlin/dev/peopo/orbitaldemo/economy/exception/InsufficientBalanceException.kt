package dev.peopo.orbitaldemo.economy.exception

import java.lang.RuntimeException

class InsufficientBalanceException(override val message: String? = null) : RuntimeException(message)