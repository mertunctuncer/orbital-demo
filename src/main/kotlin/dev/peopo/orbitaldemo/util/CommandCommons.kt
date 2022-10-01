package dev.peopo.orbitaldemo.util

import dev.peopo.orbitaldemo.config.Messages
import org.bukkit.entity.Player

fun Player.checkPermission(permission: Permissions) : Boolean?{
	if(!permissionsEnabled && !player?.hasPermission(permission.permission)!!) {
		player?.sendMessage(messages.getColorized(Messages.NO_PERMISSION))
		return null
	}
	return true
}

fun Player.checkOP() : Boolean? {
	if(!this.isOp) {
		player?.sendMessage(messages.getColorized(Messages.NO_PERMISSION))
		return null
	}
	return true
}

fun Player.sendColorizedMessage(message: Messages) = this.sendMessage(messages.getColorized(message))

fun Player.sendColorizedMessage(message: Messages, transform: (message: String) -> String) : String {
	val transformedMessage = transform(messages.getMessage(message))
	return transformedMessage.colorize()
}

