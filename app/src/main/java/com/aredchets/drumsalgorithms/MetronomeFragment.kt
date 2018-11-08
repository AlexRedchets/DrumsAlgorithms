package com.aredchets.drumsalgorithms

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_metronome.*
import kotlinx.coroutines.GlobalScope
import java.util.*

/**
 * @author Alex Redchets
 */
 
class MetronomeFragment : Fragment() {

    lateinit var soundPool : SoundPool
    lateinit var timer : Timer
    lateinit var timerTask : MetronomeTimerTask
    var soundId = 0
    var hasStarted = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
                    .build()
        } else {
            SoundPool(1, AudioManager.STREAM_MUSIC,1)
        }

        soundId = soundPool.load(context, R.raw.stick_sound, 1)

        return inflater.inflate(R.layout.fragment_metronome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_play_beep.setOnClickListener {
            if (!hasStarted) {
                timer = Timer()
                timerTask = MetronomeTimerTask()
                timer.schedule(timerTask, 0, 1000)
            }
        }

        button_stop_beep.setOnClickListener {
            stop()
        }
    }

    fun play() {
        soundPool.play(soundId, 1F, 1F, 1, 0, 1F)
        hasStarted = true
    }

    fun stop() {
        timer.cancel()
        hasStarted = false
    }

    inner class MetronomeTimerTask : TimerTask() {

        // use coroutines
        override fun run() {
            play()
        }
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }
}