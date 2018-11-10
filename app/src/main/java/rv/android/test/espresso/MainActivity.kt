package rv.android.test.espresso

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.android.AppNavigator
import ru.terrakok.cicerone.android.SupportAppNavigator
import rv.android.test.espresso.wizard.ContactFragment
import rv.android.test.espresso.wizard.EulaFragment
import rv.android.test.espresso.wizard.OverlayFragment
import rv.android.test.espresso.wizard.PhoneFragment

class MainActivity : AppCompatActivity() {


    private val cicerone = Cicerone.create()


    private enum class Screen {
        Eula, Phone, Contacts, Overlay, App
    }

    private var currentScreen = Screen.Eula

    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.fragment_container) {

        override fun createActivityIntent(context: Context, key: String, data: Any?): Intent? {
            val screen = Screen.valueOf(key)
            return when (screen) {
                Screen.App -> Intent(context, AppActivity::class.java)
                else -> null
            }
        }

        override fun createFragment(key: String, data: Any?): Fragment {
            currentScreen = Screen.valueOf(key)
            return when (currentScreen) {
                Screen.Eula -> EulaFragment()
                Screen.Phone -> PhoneFragment()
                Screen.Contacts -> ContactFragment()
                Screen.Overlay -> OverlayFragment()
                else -> throw IllegalArgumentException("Wrong screen key: $currentScreen")
            }
        }

        override fun back() = onBackPressed()
    }


    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)

        if (bundle == null) cicerone.router.newScreenChain(Screen.Eula.name)
    }


    override fun onResume() {
        cicerone.navigatorHolder.setNavigator(navigator)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        cicerone.navigatorHolder.removeNavigator()
    }


    fun next() {
        val next = when (currentScreen) {
            Screen.Eula -> Screen.Phone
            Screen.Phone -> Screen.Contacts
            Screen.Contacts -> Screen.Overlay
            Screen.Overlay -> Screen.App
            else -> TODO()
        }

        cicerone.router.navigateTo(next.name)
    }
}
