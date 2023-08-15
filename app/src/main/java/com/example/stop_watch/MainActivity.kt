package com.example.stop_watch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import com.example.stop_watch.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private var isRunning = false
    private var timerseconds = 0

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            timerseconds++
            val hours = timerseconds / 3600
            val minutes = (timerseconds % 3600) / 60
            val seconds = timerseconds % 60
            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.timerText.text = time
            handler.postDelayed(this, 1000)
        }
    }
    private fun setButtonClickedColor(button: Button) {
        val clickedColor = ContextCompat.getColor(this, R.color.yellow)
        button.setBackgroundColor(clickedColor)

        handler.postDelayed({
            val defaultColor = ContextCompat.getColor(this, R.color.blue)
            button.setBackgroundColor(defaultColor)
        }, 200)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView((binding.root))

        binding.startBtn.setOnClickListener{
            startTimer()
            setButtonClickedColor(binding.startBtn)
        }
        binding.stopBtn.setOnClickListener{
            stopTimer()
            setButtonClickedColor(binding.stopBtn)
        }
        binding.resetBtn.setOnClickListener{
            resetTimer()
            setButtonClickedColor(binding.resetBtn)
        }


    }

    private fun startTimer() {
        if (!isRunning) {
            handler.postDelayed(runnable, 1000)
            isRunning = true

            binding.startBtn.isEnabled = false

            binding.stopBtn.isEnabled = true
            binding.resetBtn.isEnabled = true

        }
    }

    private fun stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false

            binding.startBtn.isEnabled = true
            binding.startBtn.text = "Start"
            binding.stopBtn.isEnabled = false
            binding.resetBtn.isEnabled = true
        }
    }

    private fun resetTimer() {
        handler.removeCallbacks(runnable)
        timerseconds = 0
        binding.timerText.text = "00:00:00"

        binding.startBtn.isEnabled = true
        binding.startBtn.text = "Start"
        binding.stopBtn.isEnabled = false
        binding.resetBtn.isEnabled = false
    }
}

