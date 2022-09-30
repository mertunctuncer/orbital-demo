package dev.peopo.orbitaldemo

import dev.peopo.orbitaldemo.util.disable
import dev.peopo.orbitaldemo.util.hasDependencies
import dev.peopo.orbitaldemo.util.plugin
import org.bukkit.plugin.java.JavaPlugin

class OrbitalDemoPlugin : JavaPlugin() {
	override fun onEnable() {

		if (!plugin.hasDependencies()) this.disable()
		registerCommands()
		registerListeners()
	}

	override fun onDisable() {

	}

	private fun registerCommands() {

	}

	private fun registerListeners() {

	}
}