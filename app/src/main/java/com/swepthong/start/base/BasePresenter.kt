package com.swepthong.start.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by xiangrui on 17-10-17.
 */
open class BasePresenter<T : BaseView> : Presenter<T> {

    var mvpView: T? = null
        private set
    private val mCompositeSubscription = CompositeDisposable()

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
        if (!mCompositeSubscription.isDisposed) {
            mCompositeSubscription.dispose()
        }
    }

    val isViewAttached: Boolean
        get() = mvpView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    fun addDisposable(disposable: Disposable) {
        mCompositeSubscription.add(disposable)
    }

    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")

}