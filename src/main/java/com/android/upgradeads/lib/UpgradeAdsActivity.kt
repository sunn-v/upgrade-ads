package com.android.upgradeads.lib

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class UpgradeAdsActivity : AppCompatActivity() {

    private lateinit var tvContent: TextView
    private lateinit var tvCountdown: TextView
    private lateinit var btnClose: ImageView
    private lateinit var btnUpgrade: Button

    private var isFinishedCountdown: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_upgrade_ads)

        btnUpgrade = findViewById<Button>(R.id.btn_upgrade).apply {
            setOnClickListener {
                finish()
                UpgradeAds.upgradeAction?.invoke()
            }
        }

        tvContent = findViewById(R.id.text_content)

        btnClose = findViewById<ImageView>(R.id.btn_close).apply {
            setOnClickListener {
                finish()
                UpgradeAds.onCloseAction?.invoke()
            }
        }

        tvCountdown = findViewById(R.id.text_count_down)

    }

    override fun onResume() {
        super.onResume()
        startCountdown()
    }

    private fun startCountdown() {
        val time = intent.extras?.getInt("time", 15) ?: 15
        val timer = object : CountDownTimer(time * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                tvCountdown.text = "$seconds"
                if (seconds <= 5) {
                    btnUpgrade.isVisible = true
                }
            }

            override fun onFinish() {
                tvCountdown.isVisible = false
                btnClose.isVisible = true
                isFinishedCountdown = true
            }
        }
        timer.start()
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (isFinishedCountdown) {
            super.onBackPressed()
        }
    }

}