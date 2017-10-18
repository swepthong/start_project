package com.swepthong.start.home

import com.swepthong.start.base.BasePresenter
import com.swepthong.start.injection.ConfigPersistent
import com.swepthong.start.model.Gank
import com.swepthong.start.net.ApiService
import com.swepthong.start.net.JsonResult
import com.swepthong.start.util.rx.SchedulerUtils
import javax.inject.Inject

/**
 * Created by xiangrui on 17-10-17.
 */

@ConfigPersistent
class HomePresenter@Inject
constructor(private val mApiService: ApiService) : BasePresenter<HomeView>() {
    private var list = mutableListOf<Gank>()

    internal fun getList() :List<Gank> {
        return list
    }

    fun requestData() {
        checkViewAttached()
        mvpView?.showProgress(true)
        val dispose = mApiService.getGanks(1)
                .compose(SchedulerUtils.ioToMain<JsonResult<List<Gank>>>())
                //.filter { isViewAttached }
                .subscribe({ res ->
                    res?.let {
                        getData(it.results)
                    }
                }) { throwable ->
                    mvpView?.apply {
                        showProgress(false)
                        showError(throwable.message)
                    }
                }
        addDisposable(dispose)
    }

    private fun getData(list: List<Gank>) {
        this.list.addAll(list)
        mvpView?.apply {
            showProgress(false)
            refreshList()
        }
    }
}