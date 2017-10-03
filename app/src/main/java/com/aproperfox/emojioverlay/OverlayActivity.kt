package com.aproperfox.emojioverlay

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

class OverlayActivity : Activity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
  }
}