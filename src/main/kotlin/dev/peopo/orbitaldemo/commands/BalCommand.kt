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
