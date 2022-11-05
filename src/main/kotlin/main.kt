import kotlin.math.max
import kotlin.math.roundToInt

const val CARD_TYPE_MASTERCARD = "Mastercard"
const val CARD_TYPE_MAESTRO = "Maestro"
const val CARD_TYPE_VISA = "Visa"
const val CARD_TYPE_MIR = "Мир"
const val CARD_TYPE_VKPAY = "Vk Pay"

const val ERROR_UNKNOWN_CARD = -1

const val ERROR_LIMIT_OK = 0
const val ERROR_LIMIT_ONE_OPERATION = -10
const val ERROR_LIMIT_DAY = -11
const val ERROR_LIMIT_MONTH = -12

fun main() {
    testTransfer(100, CARD_TYPE_MASTERCARD)
    testTransfer(100, CARD_TYPE_MAESTRO)
    testTransfer(100, CARD_TYPE_VISA)
    testTransfer(100, CARD_TYPE_MIR)
    testTransfer(100, CARD_TYPE_VKPAY)

    transfer(0, 0, 0, "fake")
}

fun testTransfer(amountStart: Int, card: String) {
    var amountDay = 0
    var amountMonth = 0
    var amount = amountStart

    println(">>> Test transfers with '$card'")

    for (i in 1..10)
    {
        if (i % 6 == 0) {
            // Next day
            amount = amountStart
            amountDay = 0
        }
        if (transfer(amount, amountDay, amountMonth, card)) {
            amountDay += amount
            amountMonth += amount
            amount *= 10
        }
    }

    println()
}

fun transfer(amount: Int, amountDay: Int, amountMonth: Int, card: String = CARD_TYPE_VKPAY): Boolean {
    var success = false
    when (checkLimit(amount, amountDay, amountMonth, card)) {
        ERROR_LIMIT_OK -> {
            val commission = calcCommission(amount, amountMonth, card)
            println("$card: transfer $amount RUB with commission $commission RUB")
            success = true
        }
        ERROR_UNKNOWN_CARD -> println("Unknown card '$card'")
        ERROR_LIMIT_ONE_OPERATION -> println("$card: Limit for one operation exceeded")
        ERROR_LIMIT_DAY -> println("$card: Day limit exceeded")
        ERROR_LIMIT_MONTH -> println("$card: Month limit exceeded")
    }
    return success
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
            ERROR_UNKNOWN_CARD
        }
    }

    return commission
}

fun checkLimit(amount: Int, amountDay: Int, amountMonth: Int, card: String = CARD_TYPE_VKPAY): Int {
    val status = when (card) {
        CARD_TYPE_VKPAY -> {
            when {
                amount > 15_000 -> ERROR_LIMIT_ONE_OPERATION
                amount + amountMonth > 40_000 -> ERROR_LIMIT_MONTH
                else -> ERROR_LIMIT_OK
            }
        }
        CARD_TYPE_VISA, CARD_TYPE_MIR, CARD_TYPE_MASTERCARD, CARD_TYPE_MAESTRO -> {
            when {
                amount + amountDay > 150_000 -> ERROR_LIMIT_DAY
                amount + amountMonth > 600_000 -> ERROR_LIMIT_MONTH
                else -> ERROR_LIMIT_OK
            }
        }
        else -> {
            ERROR_UNKNOWN_CARD
        }
    }

    return status
}
