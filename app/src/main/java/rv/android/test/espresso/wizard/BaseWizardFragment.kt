package rv.android.test.espresso.wizard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rv.android.test.espresso.MainActivity
import rv.android.test.espresso.R


abstract class BaseWizardFragment : Fragment() {

    companion object {
        private const val l = R.layout.fragment_wizard
    }

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, b: Bundle?): View =
        i.inflate(l, c, false)


    fun next() = (activity as MainActivity).next()
}