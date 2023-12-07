package com.example.laptopstore.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.example.laptopstore.model.Laptop
import com.example.laptopstore.model.OrderLaptop
import com.example.laptopstore.ui.screen.detail.DetailContent
import org.junit.Assert.*
import com.onNodeWithStringId
import com.example.laptopstore.R
import com.example.laptopstore.ui.theme.LaptopStoreTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderLaptop = OrderLaptop(
        laptop = Laptop(4, R.drawable.tuf, "Asus Tuf", 783),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            LaptopStoreTheme {
                DetailContent(
                    fakeOrderLaptop.laptop.image,
                    fakeOrderLaptop.laptop.title,
                    fakeOrderLaptop.laptop.price,
                    fakeOrderLaptop.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(fakeOrderLaptop.laptop.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.required_price,
                fakeOrderLaptop.laptop.price
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }


    @Test
    fun decreaseProduct_stillZero() {
        // Decrementing when the count is already zero should not go below zero
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }

    @Test
    fun orderButtonStateChange() {
        // Initially, the button should be disabled
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()

        // Increase product count
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()

        // Assert that the button is now enabled
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun orderButtonRemainsDisabledWithDecreasedCount() {
        // Initially, the button should be disabled
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()

        // Attempt to decrease product count
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()

        // Assert that the button is still disabled
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
    }
    @Test
    fun orderButtonStateUnchangedWithZeroCount() {
        // Initially, the button should be disabled
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()

        // Ensure that the count is at zero
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))

        // Click the minus symbol (attempting to reduce count at zero)
        composeTestRule.onNodeWithStringId(R.string.minus_symbol).performClick()

        // Assert that the button remains disabled
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
    }

}