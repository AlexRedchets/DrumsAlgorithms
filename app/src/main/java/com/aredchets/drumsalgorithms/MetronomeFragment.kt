package com.aredchets.drumsalgorithms

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import kotlinx.android.synthetic.main.fragment_metronome.*


/**
 * @author Alex Redchets
 */
 
class MetronomeFragment : Fragment() {

    private lateinit var soundManager : MetronomeManager
    private var hasStarted = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        soundManager = MetronomeManager(context)

        return inflater.inflate(R.layout.fragment_metronome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_play_beep.setOnClickListener {

            if (tv_bmp.text.toString().filterForNumber() > 0) {
                startPlay(tv_bmp.text.toString().filterForNumber())
            }

        }

        button_stop_beep.setOnClickListener {
            stopPlay()
        }

        pb_tempo.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                setNewValue(p1.toString())

                if (p1 <= 0 && hasStarted) {
                    soundManager.stopPlaying()
                    return
                }
                startPlay(p1.toLong())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.d("not implemented", "")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.d("not implemented", "")
            }
        })
    }

    override fun onStop() {
        super.onStop()
        if (hasStarted) {
            soundManager.stopPlaying()
        }
    }

    private fun setNewValue(value : String) {
        tv_bmp.text = resources.getString(R.string.text_bmp, value)
    }

    private fun startPlay(period : Long) {
        if (!hasStarted) {
            soundManager.playWithInterval(period)
        } else {
            soundManager.stopPlaying()
            soundManager.playWithInterval(period)
        }
        hasStarted = true
    }

    private fun stopPlay() {
        if (hasStarted) {
            soundManager.stopPlaying()
            hasStarted = false
        }
    }
}