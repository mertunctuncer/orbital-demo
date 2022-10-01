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