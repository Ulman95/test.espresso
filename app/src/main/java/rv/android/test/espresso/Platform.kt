package rv.android.test.espresso

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface Platform {

    val hasPhonePermission: Boolean
    val hasContactPermission: Boolean
    val hasOverlayPermission: Boolean

    suspend fun checkContactPermission(): Boolean
    suspend fun checkPhonePermission(): Boolean
}

class PlatformImpl constructor(private val context: Context) : Platform {

    override suspend fun checkContactPermission() =
        checkPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)

    override suspend fun checkPhonePermission() =
        checkPermission(Manifest.permission.CALL_PHONE, Manifest.permission.ANSWER_PHONE_CALLS)


    override val hasContactPermission: Boolean
        get() = hasPermission(Manifest.permission.READ_CONTACTS) && hasPermission(Manifest.permission.WRITE_CONTACTS)


    override val hasPhonePermission: Boolean
        get() = hasPermission(Manifest.permission.CALL_PHONE) && hasPermission(Manifest.permission.ANSWER_PHONE_CALLS)

    override val hasOverlayPermission: Boolean
        get() = !Settings.canDrawOverlays(context)

    private suspend fun checkPermission(vararg permission: String) =
        suspendCoroutine<Boolean> { continuation ->
            TedPermission.with(context)
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() = continuation.resume(true)
                    override fun onPermissionDenied(d: List<String>) = continuation.resume(false)
                })
                .setPermissions(*permission)
                .check()
        }

    private fun hasPermission(permission: String) =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}