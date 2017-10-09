package com.aproperfox.emojioverlay

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder


class TextHandlerService : Service() {

  companion object {
    fun newIntent(context: Context) = Intent(context, TextHandlerService::class.java)
  }

  object Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
      val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
      context.startService(OverlayService.newIntent(context, text))
    }
  }

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onCreate() {
    super.onCreate()
    val filter = IntentFilter().apply { addAction(Intent.ACTION_PROCESS_TEXT) }
    registerReceiver(Receiver, filter)
  }

  override fun onDestroy() {
    unregisterReceiver(Receiver)
    super.onDestroy()
  }
}