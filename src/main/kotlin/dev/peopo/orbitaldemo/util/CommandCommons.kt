package dev.peopo.wandererworlds.util

import dev.peopo.wandererworlds.config.messages.Messages
import dev.peopo.wandererworlds.messages
import org.bukkit.entity.Player


fun Player.checkPermission(permission: Permissions) : Boolean?{
	if(!player?.hasPermission(permission.permission)!!) {
		player?.sendMessage(messages.getColorized(Messages.NO_PERMISSION))
		return null
	}
	return true
}

fun Player.sendColorizedMessage(message: Messages) = this.sendMessage(messages.getColorized(message))



