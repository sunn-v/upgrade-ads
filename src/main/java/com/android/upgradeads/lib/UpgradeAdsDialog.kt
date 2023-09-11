package com.android.upgradeads.lib

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpgradeAdsDialog: BottomSheetDialogFragment() {


    private lateinit var tvContent: TextView
    private lateinit var tvCountdown: TextView
    private lateinit var btnClose: Button
    private lateinit var btnUpgrade: Button

    private var isFinishedCountdown: Boolean = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext(), theme) {
            @SuppressLint("MissingSuperCall")
            override fun onBackPressed() {
//                this@PaymentDialog.onBackPressed()
            }
        }.apply {
            setOnShowListener { dialog ->
                KeyboardUtils(activity, view)
                (dialog as BottomSheetDialog).findViewById<View>(R.id.design_bottom_sheet)?.also {
                    it.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                    with(BottomSheetBehavior.from(it)) {
                        peekHeight = Resources.getSystem().displayMetrics.heightPixels
                        isHideable = false
                    }
                    it.requestLayout()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_upgrade_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false


        btnUpgrade = view.findViewById<Button>(R.id.btn_upgrade).apply {
            setOnClickListener {
//                finish()
                UpgradeAds.upgradeAction?.invoke()
            }
        }

        tvContent = view.findViewById(R.id.text_content)

        btnClose = view.findViewById<Button>(R.id.btn_close).apply {
            setOnClickListener {
                dismissAllowingStateLoss()
                UpgradeAds.onCloseAction?.invoke()
            }
        }

        tvCountdown = view.findViewById(R.id.text_count_down)

        startCountdown()
    }

    private fun startCountdown() {
        val time = 20
        val timer = object : CountDownTimer(time * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                tvCountdown.text = "$seconds"
                if (seconds <= 5) {
                    btnUpgrade.isVisible = true
                }
            }

            override fun onFinish() {
                btnUpgrade.isVisible = true
                tvCountdown.isVisible = false
                btnClose.isVisible = true
                isFinishedCountdown = true
            }
        }
        timer.start()
    }

    companion object {
        fun showDialog(fragmentManager: FragmentManager) {
            UpgradeAdsDialog().show(fragmentManager, "UpgradeAdsDialog")
        }
    }

}