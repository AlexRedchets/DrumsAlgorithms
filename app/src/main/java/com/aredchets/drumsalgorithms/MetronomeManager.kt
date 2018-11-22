package com.aredchets.drumsalgorithms

import android.content.Context
import android.media.MediaPlayer
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * @author Alex Redchets
 */

class MetronomeManager (context: Context?) {

    private var mediaPlayer  = MediaPlayer.create(context, R.raw.stick_sound)
    private var scheduler = Executors.newScheduledThreadPool(1)
    private lateinit var future : ScheduledFuture<*>

    fun playWithInterval(period : Long) {
        future = scheduler.scheduleAtFixedRate({
            playSound()
            println("playing $period     " + 60000/period)
        }, 0, 60000/period, TimeUnit.MILLISECONDS)
    }

    fun stopPlaying() {
        future.cancel(true)
        mediaPlayer.release()
    }

    private fun playSound() {
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }
    }
}
 