package com.aproperfox.emojioverlay

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import timber.log.Timber

class Overlay : Service() {

  private var overlay: HUDView? = null
  private lateinit var text: String
  private val wm: WindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onCreate() {
    super.onCreate()
    text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
    Timber.d("Intent data: $text")
    if (Settings.canDrawOverlays(this)) {
      Timber.d("permission granted")
      onPermissionsGranted()
    } else {
      Timber.d("permission not granted")
      onPermissionsDenied()
    }
  }

  override fun onDestroy() {
    overlay?.run { wm.removeView(overlay) }
    super.onDestroy()
  }

  private fun onPermissionsGranted() {
    //Inflate the chat head layout we created
    overlay = HUDView(this)
    (overlay?.findViewById<TextView>(R.id.text) as TextView).text = text
    //Add the view to the window.
    val params: WindowManager.LayoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT)

    params.gravity = Gravity.TOP or Gravity.START
    params.x = 0
    params.y = 100
    wm.addView(overlay, params)
  }

  private fun onPermissionsDenied() {
    // Check if Android M or higher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
      startActivity(myIntent)
    }
  }
}