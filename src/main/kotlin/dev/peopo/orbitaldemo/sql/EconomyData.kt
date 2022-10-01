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

import dev.peopo.orbitaldemo.util.config
import dev.peopo.skuerrel.annotation.Column
import dev.peopo.skuerrel.annotation.PrimaryKey
import dev.peopo.skuerrel.annotation.Table

@Table("player_balances")
class EconomyData {
	@Column(0, 36) @PrimaryKey
	var uuid: String = "null"
	@Column(1 )
	var balance: Double = config.getDouble("initial_balance")
}