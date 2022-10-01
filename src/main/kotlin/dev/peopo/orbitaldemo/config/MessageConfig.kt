package dev.peopo.orbitaldemo.config

import dev.peopo.orbitaldemo.util.colorize
import org.bukkit.plugin.Plugin

class MessageConfig(plugin: Plugin, fileName: String) : YamlConfig(plugin, fileName) {

	operator fun get(message: Messages) = this.getString(message.name.lowercase())!!
	fun getMessage(message: Messages) = this.getString(message.name.lowercase())!!
	fun getColorized(message: Messages) = this.getString(message.name.lowercase())!!.colorize()
}