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