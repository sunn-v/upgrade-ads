package com.android.upgradeads.lib

import android.content.Context
import android.content.Intent
import kotlin.random.Random


object UpgradeAds {

    internal var time : Int = 5
    private var ratio: Float = 0.5f
    internal var upgradeAction: SimpleAction? = null
    internal var onCloseAction: SimpleAction? = null

    fun init(ratio: Float = 0.5f, time: Int = 5) {
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
        forceShow(context, onClosed)
    }

    fun forceShow(context: Context, onClosed: SimpleAction) {
        onCloseAction = onClosed
        context.startActivity(Intent(context, UpgradeAdsActivity::class.java))
    }
}