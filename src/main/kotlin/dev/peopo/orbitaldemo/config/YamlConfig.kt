/*
 *      This file is part of orbital-demo
 *
 *     orbital-demo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     orbital-demo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with  orbital-demo. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.peopo.orbitaldemo.config

import dev.peopo.orbitaldemo.util.logger
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

open class YamlConfig(private val plugin: Plugin, fileName: String) : YamlConfiguration() {
    private val file: File
    private val fileName: String

    init {
        if (fileName.endsWith(".yml")) this.fileName = fileName
        else this.fileName = "$fileName.yml"
        file = File(plugin.dataFolder, this.fileName)

        if (!file.exists()) {
            file.parentFile.mkdirs()
            this.saveDefault()
        }
        this.load()
    }

    private fun saveDefault() {
        plugin.saveResource(fileName, false)
    }

    fun load() = try {
        super.load(file)
    } catch (e: Exception) {
        logger.severe("An error has occurred while loading the config file!")
    }

    fun save() = try {
        super.save(file)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

