package com.lulu.metronomo

import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timerTask
// https://www.cloverfield.co.jp/2021/06/10/%E3%80%90android%E3%80%91%E3%83%A1%E3%83%88%E3%83%AD%E3%83%8E%E3%83%BC%E3%83%A0%E3%82%A2%E3%83%97%E3%83%AA%E3%81%AE%E4%BD%9C%E6%88%90%E3%80%90kotlin%E3%80%91/

/*
* O áudio é armazenado na SoundPool, que é melhor para audios menores do que o AudioManager
* */
class MainActivity : AppCompatActivity() {
    private lateinit var sp: SoundPool
    private var batida = 0
    private lateinit var viewBPM: TextView
    private var isPlaying = false
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = SoundPool.Builder().build()
        criarSP()

        viewBPM = findViewById(R.id.textView)
    }

    override fun onPause() {
        super.onPause()
        sp.release()
    }

    fun aumentarBPM(v: View){
        sp.stop(batida)
        batida = 0
        val texto = viewBPM.text.toString()
        var valor = texto.toInt()
        valor++
        viewBPM.text = valor.toString()
    }

    fun tocarAudio(v: View) {
        isPlaying = true
        handler.post(runnable)
    }

    private fun retornaTempo():Long{
        val tempoTexto = viewBPM.text.toString()
        val tempo = tempoTexto.toLong()
        val milisec = 60000
        return milisec/tempo
    }

    private fun criarSP(){
        batida = sp.load(this,R.raw.tick, 0)
    }

    private val runnable = object: Runnable{
        override fun run() {
            sp.play(batida, 1f, 1f,100, 0,1f)
            handler.postDelayed(this, retornaTempo())
        }
    }
}
