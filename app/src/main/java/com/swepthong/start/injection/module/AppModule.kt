package com.swepthong.start.injection.module

import android.app.Application
import android.content.Context
import com.swepthong.start.injection.ApplicationContext
import dagger.Module
import dagger.Provides

/**
 * Created by xiangrui on 17-10-17.
 */
@Module(includes = arrayOf(ApiModule::class))
class AppModule(private val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }
}