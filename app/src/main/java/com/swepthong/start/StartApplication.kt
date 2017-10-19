package com.swepthong.start

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.facebook.stetho.Stetho

import com.squareup.leakcanary.LeakCanary
import com.swepthong.start.base.deeplink.DeepLinkReceiver
import com.swepthong.start.injection.component.AppComponent
import com.swepthong.start.injection.component.DaggerAppComponent
import com.swepthong.start.injection.module.AppModule
import com.swepthong.start.injection.module.NetworkModule

/**
 * Created by xiangrui on 17-10-16.
 */

class StartApplication : Application() {

    private var appComponent: AppComponent? = null

    companion object {
        operator fun get(context: Context): StartApplication {
            return context.applicationContext as StartApplication
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            LeakCanary.install(this)
        }
        val intentFilter = IntentFilter(DeepLinkHandler.ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(DeepLinkReceiver(), intentFilter)
    }

    // Needed to replace the component with a test specific one
    var component: AppComponent
        get() {
            if (appComponent == null) {
                appComponent = DaggerAppComponent.builder()
                        .appModule(AppModule(this))
                        .networkModule(NetworkModule(this))
                        .build()
            }
            return appComponent as AppComponent
        }
        set(appComponent) {
            this.appComponent = appComponent
        }

}
