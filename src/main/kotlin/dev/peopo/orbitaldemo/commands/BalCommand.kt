/*
 *      This file is part of orbital-demo
 *
 *     orbital-demo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     orbital-demo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with  orbital-demo. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.peopo.orbitaldemo.commands

import dev.peopo.orbitaldemo.config.Messages
import dev.peopo.orbitaldemo.economy.Economy
import dev.peopo.orbitaldemo.economy.currency
import dev.peopo.orbitaldemo.util.*
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

object BalCommand : TabExecutor {

	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		if(sender !is Player) {
			sender.sendMessage("This command can only be used by players.")
			return true
		}

		when(args.size) {
			0 -> {
				sender.checkPermission(Permissions.BALANCE_SELF)?: return true
				val balance = Economy.getBalance(sender.uniqueId) ?: run {
					sender.sendColorizedMessage(Messages.BALANCE_MISSING)
					return true
				}
				sender.sendColorizedMessage(Messages.BALANCE_SELF) {
					return@sendColorizedMessage it
						.replace("{balance}", currency.format(balance))
						.replace("{symbol}", currency.symbol)
						.replace("{currency_name}", currency.displayName)
				}
			}
			1 -> {
				sender.checkPermission(Permissions.BALANCE_OTHER)?: return true

				val userToCheck = Bukkit.getPlayerExact(args[0])?: run { sender.sendColorizedMessage(Messages.PLAYER_NOT_ONLINE); return true }

				val balance = Economy.getBalance(userToCheck.uniqueId) ?: run {
					sender.sendColorizedMessage(Messages.BALANCE_MISSING)
					return true
				}

				sender.sendColorizedMessage(Messages.BALANCE_OTHER) {
					return@sendColorizedMessage it
						.replace("{balance}", currency.format(balance))
						.replace("{symbol}", currency.symbol)
						.replace("{currency_name}", currency.displayName)
						.replace("{player}", userToCheck.name)
				}
			}
			else -> sender.sendColorizedMessage(Messages.BALANCE_USAGE)
		}
		return true
	}

	override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? = when (args.size) {
		1 -> Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
		else -> null
	}
}
