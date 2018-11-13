package com.aredchets.drumsalgorithms

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
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

        tv_bmp.text = resources.getString(R.string.text_bmp, "0")

        button_play_beep.setOnClickListener {
            if (!hasStarted) {
                timer = Timer()
                timerTask = MetronomeTimerTask()
                val re = Regex("[^0-9]")
                val value = re.replace(tv_bmp.text.toString(), "")
                if (value.toLong() > 0) {
                    timer.schedule(timerTask, 0, 60000/value.toLong())
                }
            }
        }

        button_stop_beep.setOnClickListener {
            if (hasStarted) {
                stop()
            }
        }

        pb_tempo.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tv_bmp.text = resources.getString(R.string.text_bmp, p1.toString())

                if (p1 <= 0 && hasStarted) {
                    timer.cancel()
                    return
                }

                if (hasStarted && p1 > 0) {
                    timer.cancel()
                    timer = Timer()
                    timerTask = MetronomeTimerTask()
                    timer.schedule(timerTask, 0, 60000/p1.toLong())
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("not implemented", "")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("not implemented", "")
            }
        })
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
        if (hasStarted) {
            timer.cancel()
        }
    }
}