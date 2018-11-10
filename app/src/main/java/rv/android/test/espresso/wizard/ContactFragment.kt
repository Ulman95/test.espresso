package rv.android.test.espresso.wizard

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_wizard.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rv.android.test.espresso.PlatformImpl


class ContactFragment : BaseWizardFragment() {

    override fun onViewCreated(view: View, b: Bundle?) {
        wizard_text.text = "Contact"
        wizard_next_btn.setOnClickListener { checkPermission() }
    }


    private fun checkPermission() {
        val context = context ?: return

        GlobalScope.launch {
            val allow = PlatformImpl(context).checkContactPermission()
            if (allow) next()
        }
    }
}