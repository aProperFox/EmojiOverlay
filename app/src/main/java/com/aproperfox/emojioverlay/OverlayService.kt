package com.aproperfox.emojioverlay

import android.app.Service
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.LayoutInflater
import com.txusballesteros.bubbles.BubbleLayout
import com.txusballesteros.bubbles.BubblesManager
import kotlinx.android.synthetic.main.emoji_overlay.view.*
import timber.log.Timber

class OverlayService : Service(), ClipboardManager.OnPrimaryClipChangedListener {

  companion object {
    private val KEY_TEXT = "key_text"

    fun newIntent(context: Context, text: String): Intent =
        Intent(context, OverlayService::class.java).apply {
          putExtra(KEY_TEXT, text)
        }
  }

  private lateinit var bubblesManager: BubblesManager
  private lateinit var clipBoardManager: ClipboardManager

  override fun onBind(intent: Intent?): IBinder? = null

  override fun onCreate() {
    super.onCreate()
    initServices()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Timber.d("onStartCommand")
    intent?.let {
      val text = it.getStringExtra(KEY_TEXT)
      createView(text)
    }
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onUnbind(intent: Intent?): Boolean {
    clipBoardManager.removePrimaryClipChangedListener(this)
    bubblesManager.recycle()
    return super.onUnbind(intent)
  }

  override fun onPrimaryClipChanged() {
    createView(clipBoardManager.primaryClip.getItemAt(0).text)
  }

  private fun initServices() {
    clipBoardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    clipBoardManager.addPrimaryClipChangedListener( this )
    bubblesManager = BubblesManager.Builder(this)
        .build()
    bubblesManager.initialize()
  }

  private fun createView(text: CharSequence) {
    val bubbleView = LayoutInflater.from(this).inflate(R.layout.emoji_overlay, null) as BubbleLayout
    bubbleView.emojiTextView.text = text
    bubbleView.setOnBubbleClickListener {
      bubblesManager.removeBubble(it)
    }
    bubbleView.setShouldStickToWall(true)
    bubblesManager.addBubble(bubbleView, 60, 20)
  }
}