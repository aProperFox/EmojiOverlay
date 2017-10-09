package com.aproperfox.emojioverlay

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.text.emoji.widget.EmojiAppCompatTextView
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Toast
import timber.log.Timber

class HUDView(context: Context) : ViewGroup(context) {

  init {
    background = resources.getDrawable(R.color.abc_input_method_navigation_guard)
  }

  fun setText(text: String) {
    val emojiTextView = EmojiAppCompatTextView(context)
    addView(emojiTextView)
    emojiTextView.text = text
    emojiTextView.setTextColor(Color.CYAN)
    Timber.d("Adding view and setting text...?")
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    Timber.d("onLayout:\nchanged: $changed\nl:$l\nt:$t\nr:$r\nb:$b")
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    Toast.makeText(context, "onTouchEvent", Toast.LENGTH_LONG).show()
    return true
  }
}