package dev.peopo.orbitaldemo.util

enum class Permissions(val permission: String) {
	BALANCE_SELF("economy.balance.self"),
	BALANCE_OTHER("economy.balance.other")
}

val permissionsEnabled by lazy { config.getBoolean("use_permissions") }