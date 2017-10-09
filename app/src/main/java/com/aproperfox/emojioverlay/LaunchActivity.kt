package com.aproperfox.emojioverlay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {

  companion object {
    const val REQUEST_CODE = 33
    private val WOMAN_TECHNOLOGIST = "\uD83D\uDC69\u200D\uD83D\uDCBB"
    private val WOMAN_SINGER = "\uD83D\uDC69\u200D\uD83C\uDFA4"
    val EMOJI = "$WOMAN_TECHNOLOGIST $WOMAN_SINGER"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_launch)
    delegate.isHandleNativeActionModesEnabled = true
    handleOverlayPermissions()
    text.text = getString(R.string.main_text, EMOJI)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if (requestCode == REQUEST_CODE) {
      if (!Settings.canDrawOverlays(this)) {
        Toast.makeText(this, "Sorry. Can't draw overlays without permission...", Toast.LENGTH_SHORT).show()
        finish()
      }
    }
  }

  private fun handleOverlayPermissions() {
    if (!Settings.canDrawOverlays(this)) {
      val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
      startActivityForResult(intent, REQUEST_CODE)
    }
  }
}