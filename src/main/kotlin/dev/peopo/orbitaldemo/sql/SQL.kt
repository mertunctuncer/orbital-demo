package dev.peopo.orbitaldemo.sql

import dev.peopo.orbitaldemo.economy.Economy
import dev.peopo.orbitaldemo.economy.exception.PlayerNotOnlineException
import dev.peopo.orbitaldemo.sqlTable
import dev.peopo.skuerrel.data.SQLPairList
import java.util.UUID


suspend fun Economy.fetchBalance(uuid: UUID) : Double {
	val where = SQLPairList()
	where.add("uuid", uuid.toString())
	val fetchedRows = sqlTable.fetch<EconomyData>(where)

	val data = if (fetchedRows.isEmpty()) {
		val initialData = EconomyData()
		initialData.uuid = uuid.toString()
		sqlTable.insert(initialData)
		initialData
	} else fetchedRows[0]

	return data.balance
}

suspend fun Economy.saveBalance(uuid: UUID) {
	val where = SQLPairList()
	where.add("uuid", uuid.toString())

	val data = EconomyData()
	data.uuid = uuid.toString()
	data.balance = getBalance(uuid) ?: throw PlayerNotOnlineException()

	sqlTable.update(data, where)
}