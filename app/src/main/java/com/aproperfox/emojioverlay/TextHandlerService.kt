package com.aproperfox.emojioverlay

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import timber.log.Timber

/**
 * Created by tolso on 10/28/2017.
 */
class TextHandlerService: AccessibilityService() {

  companion object {
    @JvmStatic
    fun newInstance(context: Context): Intent = Intent(context, TextHandlerService::class.java)
  }
  override fun onInterrupt() {
    Timber.d("onInterrupt")
  }

  override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    Toast.makeText(this, "Look, an event", Toast.LENGTH_SHORT).show()
    Timber.d(event.toString())
  }
}