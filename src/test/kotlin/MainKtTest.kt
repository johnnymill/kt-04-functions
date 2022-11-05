import org.junit.Test

import org.junit.Assert.*

class MainKtTest {

    @Test
    fun calcCommission_defaultParameters() {
        // Arrange
        val amount: Int = 100

        // Act
        val commission = calcCommission(amount)

        // Assert
        assertEquals(0, commission)
    }

    @Test
    fun calcCommission_invalidCard() {
        // Arrange
        val amount: Int = 100
        val amountMonth: Int = 0
        val card: String = "Unknown"

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(ERROR_UNKNOWN_CARD, commission)
    }

    @Test
    fun calcCommission_vkpay() {
        // Arrange
        val amount: Int = 100
        val amountMonth: Int = 0
        val card: String = CARD_TYPE_VKPAY

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(0, commission)
    }

    @Test
    fun calcCommission_mirDefault() {
        // Arrange
        val amount: Int = 100
        val amountMonth: Int = 0
        val card: String = CARD_TYPE_MIR

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(35, commission)
    }

    @Test
    fun calcCommission_mirPercent() {
        // Arrange
        val amount: Int = 4_800
        val amountMonth: Int = 0
        val card: String = CARD_TYPE_MIR

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(36, commission)
    }

    @Test
    fun calcCommission_visaDefault() {
        // Arrange
        val amount: Int = 100
        val amountMonth: Int = 0
        val card: String = CARD_TYPE_VISA

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(35, commission)
    }

    @Test
    fun calcCommission_visaPercent() {
        // Arrange
        val amount: Int = 4_800
        val amountMonth: Int = 0
        val card: String = CARD_TYPE_VISA

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(36, commission)
    }

    @Test
    fun calcCommission_maestroLimitBelow() {
        // Arrange
        val amountMonth: Int = 1_000
        val amount: Int = 299
        val card: String = CARD_TYPE_MAESTRO

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(22, commission)
    }

    @Test
    fun calcCommission_maestroLimitAbove() {
        // Arrange
        val topLimit = 75_000
        val amountMonth: Int = 70_000
        val amount: Int = topLimit - amountMonth + 1_000
        val card: String = CARD_TYPE_MAESTRO

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(26, commission)
    }

    @Test
    fun calcCommission_maestroLimitWithin() {
        // Arrange
        val topLimit = 75_000
        val amountMonth: Int = 70_000
        val amount: Int = topLimit - amountMonth
        val card: String = CARD_TYPE_MAESTRO

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(0, commission)
    }

    @Test
    fun calcCommission_mastercardLimitBelow() {
        // Arrange
        val amountMonth: Int = 1_000
        val amount: Int = 299
        val card: String = CARD_TYPE_MASTERCARD

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(22, commission)
    }

    @Test
    fun calcCommission_mastercardLimitAbove() {
        // Arrange
        val topLimit = 75_000
        val amountMonth: Int = 70_000
        val amount: Int = topLimit - amountMonth + 1_000
        val card: String = CARD_TYPE_MASTERCARD

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(26, commission)
    }

    @Test
    fun calcCommission_mastercardLimitWithin() {
        // Arrange
        val topLimit = 75_000
        val amountMonth: Int = 70_000
        val amount: Int = topLimit - amountMonth
        val card: String = CARD_TYPE_MASTERCARD

        // Act
        val commission = calcCommission(amount, amountMonth, card)

        // Assert
        assertEquals(0, commission)
    }
}