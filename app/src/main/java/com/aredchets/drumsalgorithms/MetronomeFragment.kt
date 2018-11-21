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
import java.util.*
import javax.xml.datatype.DatatypeConstants.SECONDS
import android.R.string.cancel
import java.util.concurrent.*


/**
 * @author Alex Redchets
 */
 
class MetronomeFragment : Fragment() {

    private lateinit var scheduler : ScheduledExecutorService
    private lateinit var future : ScheduledFuture<*>
    private lateinit var soundPool : SoundPool
    private var soundId = 0
    private var hasStarted = false

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

        scheduler = Executors.newScheduledThreadPool(1)

        button_play_beep.setOnClickListener {
            if (!hasStarted) {

                if (tv_bmp.text.toString().filterForNumber() > 0) {
                    runScheduled(tv_bmp.text.toString().filterForNumber())
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
                setNewValue(p1.toString())

                if (p1 <= 0 && hasStarted) {
                    future.cancel(true)
                    return
                }

                if (hasStarted) {
                    future.cancel(true)
                    runScheduled(p1.toLong())
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
        future.cancel(true)
        hasStarted = false
    }

    override fun onStop() {
        super.onStop()
        if (hasStarted) {
            future.cancel(true)
        }
    }

    private fun setNewValue(value : String) {
        tv_bmp.text = resources.getString(R.string.text_bmp, value)
    }

    private fun runScheduled(period : Long) {
        future = scheduler.scheduleAtFixedRate({
            play()
        }, 0, 60000/period, TimeUnit.MILLISECONDS)
        scheduler.schedule({
            future.cancel(true)
        }, Int.MAX_VALUE.toLong(), TimeUnit.SECONDS)
    }
}