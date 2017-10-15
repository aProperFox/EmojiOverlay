package com.aproperfox.emojioverlay

import android.support.text.emoji.EmojiCompat
import io.reactivex.Completable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

object EmojiListener : EmojiCompat.InitCallback() {

  private val connectionEvents: BehaviorSubject<Boolean> = BehaviorSubject.create()

  override fun onInitialized() {
    Timber.i("EmojiCompat initialized")
    connectionEvents.onNext(true)
  }

  override fun onFailed(throwable: Throwable?) {
    Timber.e("EmojiCompat initialization failed %s", throwable)
    throwable?.run { connectionEvents.onError(throwable) }
  }

  fun listen(): Completable =
      connectionEvents.take(1)
          .ignoreElements()
}