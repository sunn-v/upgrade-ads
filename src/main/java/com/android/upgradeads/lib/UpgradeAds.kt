package com.android.upgradeads.lib

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import kotlin.random.Random


object UpgradeAds {

    internal var time: Int = 15
    private var ratio: Float = 0.5f
    internal var upgradeAction: SimpleAction? = null
    internal var onCloseAction: SimpleAction? = null

    fun init(ratio: Float = 0.5f, time: Int = 15) {
        UpgradeAds.ratio = ratio
        UpgradeAds.time = time
    }

    private fun isShowUpgradeAds(): Boolean {
        val _ratio = Random.nextFloat()
        return _ratio < ratio
    }

    fun setOnUpgradeAction(action: SimpleAction) {
        upgradeAction = action
    }

    fun show(context: Context, onClosed: SimpleAction, onFailed: SimpleAction) {
        if (isShowUpgradeAds().not()) {
            onFailed.invoke()
            return
        }
        forceShow(context, time, onClosed)
    }

    fun forceShow(context: Context, time: Int = UpgradeAds.time, onClosed: SimpleAction) {
        onCloseAction = onClosed
        context.startActivity(Intent(context, UpgradeAdsActivity::class.java).apply {
            putExtras(
                bundleOf(
                    "time" to time
                )
            )
        })
    }
}