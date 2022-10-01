/*
 *      This file is part of orbital-demo
 *
 *     orbital-demo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     orbital-demo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with  orbital-demo. If not, see <https://www.gnu.org/licenses/>.
 */

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