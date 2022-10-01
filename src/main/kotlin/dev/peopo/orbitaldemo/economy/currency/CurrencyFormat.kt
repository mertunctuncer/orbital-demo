/*
 *      This file is part of orbital-demo
 *
 *     orbital-demo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 *     orbital-demo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along with  orbital-demo. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.peopo.orbitaldemo.economy.currency

import java.text.DecimalFormat


private const val THOUSAND = "k"
private const val MILLION = "m"
private const val TRILLION = "t"

private const val THOUSAND_REAL = 1_000.0
private const val MILLION_REAL = 1_000_000.0
private const val TRILLION_REAL = 1_000_000_000_000.0

class CurrencyFormat(
    val displayName: String,
    val symbol: String,
    pattern: String = "###,###,###,###,###,###,###,###.00",
    private val collapse : Boolean = true,
) {
    private val decimalFormatter = DecimalFormat(pattern)

    fun format(amount: Double) : String {
        var formattedAmount = amount

        if(!collapse) {
            return decimalFormatter.format(formattedAmount)
        }

        if(formattedAmount > TRILLION_REAL) {
            formattedAmount /= TRILLION_REAL
            return "${decimalFormatter.format(formattedAmount)}$TRILLION"
        }
        else if(formattedAmount > MILLION_REAL) {
            formattedAmount /= MILLION_REAL
            return "${decimalFormatter.format(formattedAmount)}$MILLION"
        }
        else if(formattedAmount > THOUSAND_REAL) {
            formattedAmount /= THOUSAND_REAL
            return "${decimalFormatter.format(formattedAmount)}$THOUSAND"
        }

        return decimalFormatter.format(formattedAmount)
    }
}