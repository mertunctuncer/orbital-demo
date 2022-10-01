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

import org.bukkit.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern


fun String.colorize() = ChatColor.translateAlternateColorCodes('&', translateHexColorCodes(this))

private val hexPattern: Pattern = Pattern.compile("[§&]#([A-Fa-f\\d]{6})")
private fun translateHexColorCodes( message: String): String {
	val matcher: Matcher = hexPattern.matcher(message)
	val buffer = StringBuffer(message.length + 4 * 8)
	while (matcher.find()) {
		val group: String = matcher.group(1)
		matcher.appendReplacement(
			buffer, "§x§${group[0]}§${group[1]}§${group[2]}§${group[3]}§${group[4]}§${group[5]}"
		)
	}
	return matcher.appendTail(buffer).toString()
}