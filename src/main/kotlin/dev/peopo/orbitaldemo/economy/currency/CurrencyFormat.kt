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
    val pluralDisplayName : String,
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