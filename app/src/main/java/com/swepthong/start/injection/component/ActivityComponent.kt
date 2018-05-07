package com.swepthong.start.injection.component

import com.swepthong.start.base.BaseActivity
import com.swepthong.start.home.HomeActivity
import com.swepthong.start.injection.PerActivity
import com.swepthong.start.injection.module.ActivityModule
import dagger.Subcomponent

/**
 * Created by xiangrui on 17-10-17.
 */
@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(baseActivity: BaseActivity)
    fun inject(activity: HomeActivity)
}