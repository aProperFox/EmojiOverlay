package com.aproperfox.emojioverlay

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.text.emoji.EmojiCompat
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.emoji_overlay.view.*
import timber.log.Timber

class HUDView(context: Context) : ViewGroup(context) {

  init {
    (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        .inflate(R.layout.emoji_overlay, this, true)
  }

  fun setText(text: CharSequence) {
    EmojiListener.listen()
        .subscribe(
            { emojiTextView.text = EmojiCompat.get().process(text) },
            { Toast.makeText(context, "Failed to connect to emoji service", Toast.LENGTH_SHORT).show() })
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