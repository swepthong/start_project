package com.swepthong.start.home

import com.swepthong.start.base.BaseView

/**
 * Created by xiangrui on 17-10-17.
 */
interface HomeView : BaseView {

    fun showProgress(show: Boolean)
    fun showError(error: String?)
    fun refreshList()

}