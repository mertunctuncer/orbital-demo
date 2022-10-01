package dev.peopo.orbitaldemo.sql

import dev.peopo.bukkitscope.coroutine.DefaultScope
import dev.peopo.orbitaldemo.economy.Economy
import kotlinx.coroutines.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object JoinLeaveListener : Listener {

	@EventHandler
	fun onPlayerJoin(event: PlayerJoinEvent) {
		DefaultScope.launch {
			val balance = Economy.fetchBalance(event.player.uniqueId)
			Economy.setBalance(event.player.uniqueId, balance)
		}
	}

	@EventHandler
	fun onPlayerLeave(event: PlayerQuitEvent) {
		DefaultScope.launch {
			Economy.saveBalance(event.player.uniqueId)
			Economy.unloadBalance(event.player.uniqueId)
		}
	}
}