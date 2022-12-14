package com.lulu.metronomo

import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// https://www.cloverfield.co.jp/2021/06/10/%E3%80%90android%E3%80%91%E3%83%A1%E3%83%88%E3%83%AD%E3%83%8E%E3%83%BC%E3%83%A0%E3%82%A2%E3%83%97%E3%83%AA%E3%81%AE%E4%BD%9C%E6%88%90%E3%80%90kotlin%E3%80%91/

/*
* O áudio é armazenado na SoundPool, que é melhor para audios menores do que o AudioManager
* */
class MainActivity : AppCompatActivity() {
    private lateinit var sp: SoundPool
    private var spID = 0
    private lateinit var viewBPM: TextView
    private var isPlaying = false
    private var handler = Handler(Looper.getMainLooper())
    private var valueBPM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewBPM = findViewById(R.id.textView)
    }

    override fun onResume() {
        super.onResume()
        sp = SoundPool.Builder().build()
        spID = sp.load(this, R.raw.tick, 1)
    }

    override fun onPause() {
        super.onPause()
        sp.release()
    }

    fun increaseBPM(v: View) {
        stop()
        valueBPM = viewBPM.text.toString().toInt()
        valueBPM++
        viewBPM.text = valueBPM.toString()
    }

    fun decreaseBPM(v: View) {
        stop()
        valueBPM = viewBPM.text.toString().toInt()
        valueBPM--
        viewBPM.text = valueBPM.toString()
    }

    private fun stop(){
        isPlaying = false
        handler.removeCallbacks(runnable)
    }

    fun play(v: View) {
        if(isPlaying){return}
        isPlaying = true
        handler.post(runnable)
    }

    private fun retornaTempo(): Long {
        val tempoTexto = viewBPM.text.toString()
        val tempo = tempoTexto.toLong()
        val milisec = 60000
        return milisec / tempo
    }

    private val runnable = object : Runnable {
        override fun run() {
            sp.play(spID, 1f, 1f, 100, 0, 1f)
            handler.postDelayed(this, retornaTempo())
        }
    }
}
