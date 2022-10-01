package dev.peopo.orbitaldemo.economy

import dev.peopo.orbitaldemo.economy.exception.InsufficientBalanceException
import dev.peopo.orbitaldemo.economy.exception.PlayerNotOnlineException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

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