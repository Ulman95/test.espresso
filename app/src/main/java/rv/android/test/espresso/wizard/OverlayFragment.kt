package rv.android.test.espresso.wizard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import kotlinx.android.synthetic.main.fragment_wizard.*
import rv.android.test.espresso.PlatformImpl


class OverlayFragment : BaseWizardFragment() {

    private lateinit var platform: PlatformImpl

    override fun onViewCreated(view: View, b: Bundle?) {
        wizard_text.text = "Overlay"
        wizard_next_btn.setOnClickListener { checkPermission() }
    }


    private fun checkPermission() {
        val context = context ?: return
        platform = PlatformImpl(context)

        if (platform.hasOverlayPermission) {
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            myIntent.data = Uri.parse("package:${context.applicationContext.packageName}")
            startActivityForResult(myIntent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 101 && platform.hasOverlayPermission) next()
    }
}