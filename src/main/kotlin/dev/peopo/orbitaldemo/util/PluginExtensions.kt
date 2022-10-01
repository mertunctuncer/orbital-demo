/*
 *      This file is part of orbital-demo
 *
 *     orbital-demo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     orbital-demo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with  orbital-demo. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.peopo.orbitaldemo.util

import dev.peopo.orbitaldemo.config.MessageConfig
import dev.peopo.orbitaldemo.config.YamlConfig
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import java.util.logging.Logger

val plugin : Plugin by lazy{ Bukkit.getPluginManager().getPlugin("OrbitalDemo")!! }

val pluginManager: PluginManager by lazy { Bukkit.getPluginManager() }

val config: YamlConfig by lazy { YamlConfig(plugin, "config.yml") }

val messages: MessageConfig by lazy { MessageConfig(plugin, "messages.yml") }

val logger: Logger by lazy { plugin.logger }

val version: String by lazy { plugin.version }

val Plugin.version : String by lazy {
	val input = plugin.getResource("plugin.yml")?.reader()
	val config = YamlConfiguration()
	config.load(input!!)
	return@lazy config.getString("version")!!
}

val Plugin.dependencies : List<String> by lazy {
	val input = plugin.getResource("plugin.yml")?.reader()
	val config = YamlConfiguration()
	config.load(input!!)
	return@lazy config.getStringList("depend")
}

val Plugin.softDependencies : List<String> by lazy {
	val input = plugin.getResource("plugin.yml")?.reader()
	val config = YamlConfiguration()
	config.load(input!!)
	return@lazy config.getStringList("softdepend")
}

val Plugin.apiVersion : String by lazy {
	val input = plugin.getResource("plugin.yml")?.reader()
	val config = YamlConfiguration()
	config.load(input!!)
	return@lazy config.getString("api-version")!!
}

fun Plugin.hasDependencies() : Boolean {
	for(dependency in this.dependencies) if(pluginManager.getPlugin(dependency) == null) return false
	return true
}

fun Plugin.disable() = pluginManager.disablePlugin(this)