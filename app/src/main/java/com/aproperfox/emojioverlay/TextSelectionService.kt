package com.aproperfox.emojioverlay

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class TextSelectionService : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    Log.d("TextSelectionService", intent.toString())
    context.startActivity(OverlayActivity.newIntent(context))
  }
}