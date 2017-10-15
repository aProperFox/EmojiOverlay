package com.aproperfox.emojioverlay

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.text.emoji.EmojiCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_launch.*

class LaunchActivity : AppCompatActivity() {

  companion object {
    const val REQUEST_CODE = 33
  }

  private lateinit var clipboardManager: ClipboardManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_launch)
    delegate.isHandleNativeActionModesEnabled = true
    initViews()
    initServices()
  }

  private fun initServices() {
    handleOverlayPermissions()
    clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  }

  private fun initViews() {
    text.text = getString(R.string.emoji_text)
    EmojiListener.listen()
        .subscribe(
            { emojiTextView.text = EmojiCompat.get().process(getString(R.string.emoji_text)) },
            { Toast.makeText(this, "Failed to connect to emoji service", Toast.LENGTH_SHORT).show() })

    pasteView.setOnClickListener {
      pasteView.text = clipboardManager.primaryClip
          .getItemAt(0)
          .text
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    if (requestCode == REQUEST_CODE) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
        Toast.makeText(this, "Sorry. Can't draw overlays without permission...", Toast.LENGTH_SHORT).show()
        finish()
      }
    }
  }

  private fun handleOverlayPermissions() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
      val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
      startActivityForResult(intent, REQUEST_CODE)
    }
  }
}