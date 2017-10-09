package com.aproperfox.emojioverlay

import android.app.Activity
import android.content.Intent
import android.os.Bundle


class TextReceiverActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
    startService(OverlayService.newIntent(this, text))
    finish()
  }
}