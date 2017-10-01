package com.aproperfox.emojioverlay

import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat

class OverlayActivity : ActivityCompat() {

  companion object {
    fun newIntent(context: Context) : Intent {
      return Intent(context, OverlayActivity::class.java)
    }
  }
}