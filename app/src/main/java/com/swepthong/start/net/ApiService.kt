package com.swepthong.start.net

import com.swepthong.start.model.Gank
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by xiangrui on 17-10-16.
 */
interface ApiService {

    @GET("data/Android/10/{page}")
    fun getGanks(@Path("page") page:Int): Single<JsonResult<List<Gank>>>
}