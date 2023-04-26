package com.android.upgradeads.lib

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

internal class UpgradeAdsFragment : Fragment() {

    private lateinit var tvContent: TextView
    private lateinit var tvCountdown: TextView
    private lateinit var btnClose: ImageView
    private lateinit var btnUpgrade: Button

    private var time: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upgrade_ads, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnUpgrade = view.findViewById<Button>(R.id.btn_upgrade).apply {
            setOnClickListener {
                UpgradeAds.upgradeAction?.invoke()
            }
        }

        tvContent = view.findViewById(R.id.text_content)

        btnClose = view.findViewById<ImageView>(R.id.btn_close).apply {
            setOnClickListener {

            }
        }

        tvCountdown = view.findViewById(R.id.text_count_down)
        startCountdown()
    }

    private fun startCountdown() {
        val timer = object: CountDownTimer(time * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvCountdown.text = "${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                tvCountdown.isVisible = false
                btnClose.isVisible = true
            }
        }
        timer.start()
    }
}