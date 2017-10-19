package com.swepthong.start.base.deeplink

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by xiangrui on 17-10-19.
 */
class DeepLinkReceiver : BroadcastReceiver(), AnkoLogger {

    override fun onReceive(context: Context, intent: Intent) {
        val deepLinkUri = intent.getStringExtra(DeepLinkHandler.EXTRA_URI)

        if (intent.getBooleanExtra(DeepLinkHandler.EXTRA_SUCCESSFUL, false)) {
            info("Success deep linking: " + deepLinkUri)
        } else {
            val errorMessage = intent.getStringExtra(DeepLinkHandler.EXTRA_ERROR_MESSAGE)
            info("Error deep linking: $deepLinkUri with error message +$errorMessage")
        }
    }
}