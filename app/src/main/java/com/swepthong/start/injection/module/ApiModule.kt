package com.swepthong.start.injection.module

import com.swepthong.start.net.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by xiangrui on 17-10-17.
 */

@Module(includes = arrayOf(NetworkModule::class))
class ApiModule {

    @Provides
    @Singleton
    internal fun provideApiService(retrofit: Retrofit) : ApiService =
            retrofit.create(ApiService::class.java)
}