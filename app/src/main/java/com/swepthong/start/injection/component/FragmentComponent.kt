package com.swepthong.start.injection.component

import com.swepthong.start.injection.PerFragment
import com.swepthong.start.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * Created by xiangrui on 17-10-17.
 */
@PerFragment
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent