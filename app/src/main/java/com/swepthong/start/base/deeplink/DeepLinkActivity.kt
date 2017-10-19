package com.swepthong.start.base.deeplink

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler

/**
 * Created by xiangrui on 17-10-19.
 */

@DeepLinkHandler(StartDeepLinkModule::class)
class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DeepLinkDelegate, MangoDeepLinkModuleLoader is generated at compile-time.
        val deepLinkDelegate = DeepLinkDelegate(StartDeepLinkModuleLoader())

        // Delegate the deep link handling to DeepLinkDispatch.
        // It will start the correct Activity based on the incoming Intent URI
        deepLinkDelegate.dispatchFrom(this)

        // Finish this Activity since the correct one has been just started
        finish()

    }
}