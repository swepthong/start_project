package com.swepthong.start.injection.component

import android.app.Application
import android.content.Context
import com.swepthong.start.injection.ApplicationContext
import com.swepthong.start.injection.module.AppModule
import com.swepthong.start.net.ApiService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun apiService(): ApiService
}
