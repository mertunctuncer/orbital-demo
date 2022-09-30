package dev.peopo.orbitaldemo.util

import dev.peopo.orbitaldemo.config.Messages
import org.bukkit.entity.Player

fun Player.checkPermission(permission: Permissions) : Boolean?{
	if(!player?.hasPermission(permission.permission)!!) {
		player?.sendMessage(messages.getColorized(Messages.NO_PERMISSION))
		return null
	}
	return true
}

fun Player.sendColorizedMessage(message: Messages) = this.sendMessage(messages.getColorized(message))



