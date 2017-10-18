package com.swepthong.start.base

/**
 * Created by xiangrui on 17-10-17.
 */
interface Presenter<in V> {

    fun attachView(mvpView: V)

    fun detachView()
}