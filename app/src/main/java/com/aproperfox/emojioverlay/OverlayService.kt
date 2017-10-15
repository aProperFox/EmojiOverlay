package com.aproperfox.emojioverlay

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.WindowManager
import timber.log.Timber

class OverlayService : Service() {

  companion object {
    private val KEY_TEXT = "key_text"

    fun newIntent(context: Context, text: String): Intent =
        Intent(context, OverlayService::class.java).apply {
          putExtra(KEY_TEXT, text)
        }
  }

  private lateinit var overlay: HUDView
  private lateinit var wm: WindowManager

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onCreate() {
    super.onCreate()
    Timber.d("onCreate")
    wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    createView()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Timber.d("onStartCommand")
    intent?.let {
      val text = it.getStringExtra(KEY_TEXT)
      setViewText(text)
    }
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onUnbind(intent: Intent?): Boolean {
    wm.removeView(overlay)
    return super.onUnbind(intent)
  }

  private fun createView() {
    Timber.d("createView")
    //Inflate the chat head layout we created
    overlay = HUDView(this)
    //Add the view to the window.
    val params: WindowManager.LayoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT)
    params.width = 300
    params.height = 100
    wm.addView(overlay, params)
  }

  private fun setViewText(text: String) {
    Timber.d("setViewText")
    overlay.setText(text)
  }
}