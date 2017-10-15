package com.aproperfox.emojioverlay

import android.app.Application
import android.support.text.emoji.EmojiCompat
import android.support.text.emoji.FontRequestEmojiCompatConfig
import android.support.v4.provider.FontRequest
import timber.log.Timber

class EmojiApp : Application() {
  override fun onCreate() {
    super.onCreate()
    Timber.plant(Timber.DebugTree())
    val fontRequest = FontRequest(
        "com.google.android.gms.fonts",
        "com.google.android.gms",
        "Noto Color Emoji Compat",
        R.array.com_google_android_gms_fonts_certs)
    val config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
        .setReplaceAll(true)
        .registerInitCallback(EmojiListener)
    EmojiCompat.init(config)
  }
}