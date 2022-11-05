import kotlin.math.max
import kotlin.math.roundToInt

const val CARD_TYPE_MASTERCARD = "Mastercard"
const val CARD_TYPE_MAESTRO = "Maestro"
const val CARD_TYPE_VISA = "Visa"
const val CARD_TYPE_MIR = "Мир"
const val CARD_TYPE_VKPAY = "Vk Pay"

const val ERROR_UNKNOWN_CARD = -1

fun main() {
    calcCommission(10_000)
    calcCommission(10_000, card = CARD_TYPE_MASTERCARD)
    calcCommission(10_000, 100_000, CARD_TYPE_MAESTRO)
    calcCommission(10_000, card = CARD_TYPE_VISA)
    calcCommission(1_000, 100_000, CARD_TYPE_MIR)
    calcCommission(100, card = "fake")
}

fun calcCommission(amount: Int, amountMonth: Int = 0, card: String = CARD_TYPE_VKPAY): Int {
    val limit = if (amountMonth > 0) amountMonth else amount
    val commission: Int = when (card) {
        CARD_TYPE_VKPAY -> 0
        CARD_TYPE_VISA, CARD_TYPE_MIR -> {
            max((amount * 0.0075f).roundToInt(), 35)
        }
        CARD_TYPE_MASTERCARD, CARD_TYPE_MAESTRO -> {
            if (300 <= limit && limit <= 75_000) 0 else 20 + (amount * 0.006f).roundToInt()
        }
        else -> {
            // println("Unknown card '$card'")
            ERROR_UNKNOWN_CARD
        }
    }

    // println("$card: transfer $amount RUB with commission $commission RUB")
    return commission
}
