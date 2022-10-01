/*
 *      This file is part of orbital-demo
 *
 *     orbital-demo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     orbital-demo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with  orbital-demo. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.peopo.orbitaldemo

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.peopo.orbitaldemo.commands.BalCommand
import dev.peopo.orbitaldemo.commands.EarnCommand
import dev.peopo.orbitaldemo.sql.EconomyData
import dev.peopo.orbitaldemo.sql.JoinLeaveListener
import dev.peopo.orbitaldemo.util.*
import dev.peopo.skuerrel.Table
import org.bukkit.plugin.java.JavaPlugin


@Suppress("unused")
class OrbitalDemoPlugin : JavaPlugin() {
	override fun onEnable() {
		if (!plugin.hasDependencies()) this.disable()
		registerCommands()
		registerListeners()
	}

	override fun onDisable() = hikariCP.close()

	private fun registerCommands() {
		getCommand("bal")?.setExecutor(BalCommand)
		getCommand("give")
		getCommand("setbal")
		getCommand("earn")?.setExecutor(EarnCommand)
	}

	private fun registerListeners() {
		pluginManager.registerEvents(JoinLeaveListener, plugin)
	}
}

val sqlTable by lazy { Table(hikariCP, EconomyData::class) }

val hikariCP by lazy {
	val host = config.getString("sql.host")
	val port = config.getInt("sql.port")
	val database = config.getString("sql.database")

	org.postgresql.Driver()

	val hikariConfig = HikariConfig()
	hikariConfig.jdbcUrl = "jdbc:postgresql://$host:$port/$database"
	hikariConfig.username = config.getString("sql.username")
	hikariConfig.password = config.getString("sql.password")
	hikariConfig.isAutoCommit = false
	hikariConfig.maximumPoolSize = config.getInt("sql.pool_size")

	return@lazy HikariDataSource(hikariConfig)
}