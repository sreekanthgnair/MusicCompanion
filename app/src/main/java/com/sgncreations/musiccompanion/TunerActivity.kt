package com.sgncreations.musiccompanion

import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Build
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sgncreations.musiccompanion.R
import kotlin.math.atan
import kotlin.math.sin


class TunerActivity : AppCompatActivity() {

    lateinit var Track: AudioTrack
    var isPlaying: Boolean = false
    val Fs: Int = 44100
    private val buffLength: Int = AudioTrack.getMinBufferSize(Fs, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)

    private val freqE = 82
    private val freqA = 110
    private val freqD = 147
    private val freqG = 196
    private val freqB = 247
    private val freqE2 = 330


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuner)

        val btnE: View = findViewById(R.id.btn_E)
        val btnA: View = findViewById(R.id.btn_A)
        val btnD: View = findViewById(R.id.btn_D)
        val btnG: View = findViewById(R.id.btn_G)
        val btnB: View = findViewById(R.id.btn_B)
        val btnE2: View = findViewById(R.id.btn_E2)

        val btnMenu: View = findViewById(R.id.tv_menu)

        val tvFreq: TextView = findViewById(R.id.tv_freq)


        btnE.setOnClickListener {
            stopPlaying()
            tvFreq.text = "$freqE Hz"
            Thread {
                initTrack()
                startPlaying()
                playback(freqE)
            }.start()

        }

        btnA.setOnClickListener {
            stopPlaying()
            tvFreq.text = "$freqA Hz"
            Thread {
                initTrack()
                startPlaying()
                playback(freqA)
            }.start()

        }

        btnD.setOnClickListener {
            stopPlaying()
            tvFreq.text = "$freqD Hz"
            Thread {
                initTrack()
                startPlaying()
                playback(freqD)
            }.start()

        }

        btnG.setOnClickListener {
            stopPlaying()
            tvFreq.text = "$freqG Hz"
            Thread {
                initTrack()
                startPlaying()
                playback(freqG)
            }.start()

        }
        btnB.setOnClickListener {
            stopPlaying()
            tvFreq.text = "$freqB Hz"
            Thread {
                initTrack()
                startPlaying()
                playback(freqB)
            }.start()

        }
        btnE2.setOnClickListener {
            stopPlaying()
            tvFreq.text = "$freqE2 Hz"
            Thread {
                initTrack()
                startPlaying()
                playback(freqE2)
            }.start()

        }

        btnMenu.setOnClickListener{
            if(isPlaying){
                stopPlaying()
            }
            startActivity(Intent(this, MainActivity::class.java))
        }

        val stopBtn: Button = findViewById(R.id.btn_stop)
        stopBtn.setOnClickListener {
            tvFreq.text = "0 Hz"
            stopPlaying()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initTrack() {
        Track = AudioTrack(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build(),
            AudioFormat.Builder().setEncoding(AudioFormat.ENCODING_PCM_16BIT).
            setSampleRate(Fs).
            build(),
            buffLength, AudioTrack.MODE_STREAM, AudioManager.AUDIO_SESSION_ID_GENERATE
        )
    }

    private fun playback(frequency : Int) {

        val frameOut: ShortArray = ShortArray(buffLength)
        val amplitude: Int = 32767
         val twopi: Double = 8.0 * atan(1.0)
        var phase: Double = 0.0

        while (isPlaying) {
            for (i in 0 until buffLength) {
                frameOut[i] = (amplitude * sin(phase)).toInt().toShort()
                phase += twopi * frequency / Fs
                if (phase > twopi) {
                    phase -= twopi
                }
            }
            Track.write(frameOut, 0, buffLength)
        }
    }

    private fun startPlaying() {
        Track.play()
        isPlaying = true
    }

    private fun stopPlaying() {
        if (isPlaying) {
            isPlaying = false
            Track.stop()
            Track.release()
        }
    }

}
