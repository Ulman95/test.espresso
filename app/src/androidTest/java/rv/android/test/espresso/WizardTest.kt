package rv.android.test.espresso

import android.widget.Switch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class WizardTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var device: UiDevice

    @Test
    @Throws(Exception::class)
    fun test() {
        // Eula
        onView(withId(R.id.wizard_next_btn)).perform(click())

        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Phone
        onView(withId(R.id.wizard_next_btn)).perform(click())
        val allowPhone = device.findObject(UiSelector().text("ALLOW"))
        allowPhone.click()

        // Contact
        onView(withId(R.id.wizard_next_btn)).perform(click())
        val allowContact = device.findObject(UiSelector().text("ALLOW"))
        allowContact.click()

        // Overlay
        onView(withId(R.id.wizard_next_btn)).perform(click())
        val allowOverlay = device.findObject(UiSelector().className(Switch::class.java))
        allowOverlay.click()
        device.pressBack()

        val platform = PlatformImpl(activityRule.activity)
        Assert.assertTrue(platform.hasContactPermission)
        Assert.assertTrue(platform.hasPhonePermission)
        Assert.assertTrue(platform.hasOverlayPermission)
    }
}