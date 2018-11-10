package rv.android.test.espresso.wizard

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_wizard.*


class EulaFragment : BaseWizardFragment() {

    override fun onViewCreated(view: View, b: Bundle?) {
        wizard_text.text = "EULA"
        wizard_next_btn.setOnClickListener { next() }
    }
}