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
import dev.peopo.orbitaldemo.economy.exception.InsufficientBalanceException
import dev.peopo.orbitaldemo.economy.exception.PlayerNotOnlineException
import dev.peopo.orbitaldemo.util.Permissions
import dev.peopo.orbitaldemo.util.checkOP
import dev.peopo.orbitaldemo.util.checkPermission
import dev.peopo.orbitaldemo.util.sendColorizedMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.lang.NumberFormatException

object SetBalCommand : TabExecutor {


	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		if(sender !is Player) {
			sender.sendMessage("This command can only be used by players.")
			return true
		}

		when(args.size) {
			2 -> {
				sender.checkOP() ?: return true

				val userToSet = Bukkit.getPlayerExact(args[0])?: run { sender.sendColorizedMessage(Messages.PLAYER_NOT_ONLINE); return true }

				val amount = try {
					val parsed = args[1].toDouble()
					if(parsed <= 0) null else parsed
				} catch (e: NumberFormatException) { null }?: run {
					sender.sendColorizedMessage(Messages.INVALID_AMOUNT)
					return true
				}

				try {
					Economy.setBalance(userToSet.uniqueId, amount)

					sender.sendColorizedMessage(Messages.SET_SUCCESSFUL) {
						return@sendColorizedMessage it
							.replace("{player}", userToSet.name)
							.replace("{symbol}", currency.symbol)
							.replace("{amount}", currency.format(amount))
							.replace("{currency_name}", currency.displayName)
					}

					userToSet.sendColorizedMessage(Messages.SET_RECEIVED) {
						return@sendColorizedMessage it
							.replace("{player}", sender.name)
							.replace("{balance}", currency.format(Economy.getBalance(userToSet.uniqueId)!!))
							.replace("{symbol}", currency.symbol)
							.replace("{amount}", currency.format(amount))
							.replace("{currency_name}", currency.displayName)
					}
				} catch (e: PlayerNotOnlineException) {
					sender.sendColorizedMessage(Messages.PLAYER_NOT_ONLINE)
				}
			}
			else -> sender.sendColorizedMessage(Messages.SET_USAGE)
		}
		return true
	}
	override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? = when (args.size) {
		1 -> Bukkit.getOnlinePlayers().map { it.name }.toMutableList()
		2 -> mutableListOf("amount")
		else -> null
	}
}