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
import dev.peopo.orbitaldemo.util.Permissions
import dev.peopo.orbitaldemo.util.checkPermission
import dev.peopo.orbitaldemo.util.sendColorizedMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.util.*
import kotlin.random.Random

object EarnCommand : TabExecutor{

	private val lastUsages = mutableMapOf<UUID, Long>()
	private const val COOLDOWN = 1000L * 60L

	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		if (sender !is Player) {
			sender.sendMessage("This command can only be used by players.")
			return true
		}

		when (args.size) {
			0 -> {
				sender.checkPermission(Permissions.EARN) ?: return true
				Economy.getBalance(sender.uniqueId) ?: run {
					sender.sendColorizedMessage(Messages.BALANCE_MISSING)
					return true
				}

				val lastUsage = this.lastUsages[sender.uniqueId]
				if (lastUsage != null && System.currentTimeMillis() < lastUsage + COOLDOWN) {
					sender.sendColorizedMessage(Messages.EARN_COOLDOWN) {
						return@sendColorizedMessage it
							.replace("{time_left}", "${(lastUsage + COOLDOWN) / 1000L} second(s)")
					}
					return true
				}

				lastUsages[sender.uniqueId] = System.currentTimeMillis()

				val amount = Random.nextInt(1, 6)

				Economy.depositBalance(sender.uniqueId, amount.toDouble())
				val balance = Economy.getBalance(sender.uniqueId)

				sender.sendColorizedMessage(Messages.EARN_SUCCESSFUL) {
					return@sendColorizedMessage it
						.replace("{balance}", currency.format(balance!!))
						.replace("{symbol}", currency.symbol)
						.replace("{amount}", currency.format(amount.toDouble()))
						.replace("{currency_name}", currency.displayName)
				}
			}

			else -> sender.sendColorizedMessage(Messages.EARN_USAGE)
		}
		return true
	}

	override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? = null

}