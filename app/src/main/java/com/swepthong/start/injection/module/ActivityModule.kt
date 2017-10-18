package com.swepthong.start.injection.module

import android.app.Activity
import android.content.Context
import com.swepthong.start.injection.ActivityContext

import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @ActivityContext
    internal fun providesContext(): Context {
        return activity
    }
}
