/*
 *      This file is part of orbital-demo
 *
 *     orbital-demo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     orbital-demo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with  orbital-demo. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.peopo.orbitaldemo.util

import dev.peopo.orbitaldemo.config.Messages
import org.bukkit.entity.Player

fun Player.checkPermission(permission: Permissions) : Boolean?{
	if(permissionsEnabled && !player?.hasPermission(permission.permission)!!) {
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

fun Player.sendColorizedMessage(message: Messages, transform: (message: String) -> String) {
	val transformedMessage = transform(messages.getMessage(message))
	this.sendMessage(transformedMessage.colorize())
}

