/*
 *      This file is part of orbital-demo
 *
 *     orbital-demo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     orbital-demo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with  orbital-demo. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.peopo.orbitaldemo.economy

import dev.peopo.orbitaldemo.economy.currency.CurrencyFormat
import dev.peopo.orbitaldemo.economy.exception.InsufficientBalanceException
import dev.peopo.orbitaldemo.economy.exception.PlayerNotOnlineException
import dev.peopo.orbitaldemo.util.config
import java.util.*
import java.util.concurrent.ConcurrentHashMap


val currency: CurrencyFormat by lazy {
	CurrencyFormat(
		config.getString("currency.name")!!,
		config.getString("currency.symbol")!!,
		config.getString("currency.format")!!,
		config.getBoolean("currency.collapse")
	)
}

object Economy {

	private val balances = ConcurrentHashMap<UUID, Double>()

	fun setBalance(uuid: UUID, balance: Double) {
		balances[uuid] = balance
	}

	fun getBalance(uuid: UUID) = balances[uuid]


	fun depositBalance(uuid: UUID, amount: Double) {
		val current = balances[uuid]?: throw PlayerNotOnlineException("Balance is not cached")
		balances[uuid] = current + amount
	}

	fun withdrawBalance(uuid: UUID, amount: Double) {
		val current = balances[uuid]?: throw PlayerNotOnlineException("Balance is not cached")
		if(current < amount) throw InsufficientBalanceException()

		balances[uuid] = current - amount
	}

	fun hasBalance(uuid: UUID, amount: Double) : Boolean {
		val current = balances[uuid]?: throw PlayerNotOnlineException("Balance is not cached")
		return current > amount
	}

	fun unloadBalance(uuid: UUID) = balances.remove(uuid)
}