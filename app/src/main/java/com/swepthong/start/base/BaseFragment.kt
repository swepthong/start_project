package com.swepthong.start.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.util.LongSparseArray
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swepthong.start.StartApplication
import com.swepthong.start.injection.component.ConfigPersistentComponent
import com.swepthong.start.injection.component.DaggerConfigPersistentComponent
import com.swepthong.start.injection.component.FragmentComponent
import com.swepthong.start.injection.module.FragmentModule
import com.swepthong.start.util.bind
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by xiangrui on 17-10-17.
 */
abstract class BaseFragment : Fragment() {

    private var mFragmentComponent: FragmentComponent? = null
    private var mFragmentId: Long = 0
    private var mBinding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the FragmentComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mFragmentId = savedInstanceState?.getLong(KEY_FRAGMENT_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent
        if (sComponentsArray.get(mFragmentId) == null) {
            Log.i(TAG, "Creating new ConfigPersistentComponent id=$mFragmentId")
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(StartApplication[activity].component)
                    .build()
            sComponentsArray.put(mFragmentId, configPersistentComponent)
        } else {
            Log.i(TAG, "Reusing ConfigPersistentComponent id=$mFragmentId")
            configPersistentComponent = sComponentsArray.get(mFragmentId)
        }
        mFragmentComponent = configPersistentComponent.fragmentComponent(FragmentModule(this))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putLong(KEY_FRAGMENT_ID, mFragmentId)
    }

    override fun onDestroy() {
        if (!activity.isChangingConfigurations) {
            Log.i(TAG, "Clearing ConfigPersistentComponent id=$mFragmentId")
            sComponentsArray.remove(mFragmentId)
        }
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //mBinding = createDataBinding(inflater,container,savedInstanceState)
        if (hasBind()) {
            mBinding = container?.bind(layoutId())
            return mBinding?.root as View
        }

        val view: View? = inflater?.inflate(layoutId(), container, false) as View
        return view as View
    }

    @LayoutRes abstract fun layoutId(): Int
    abstract fun hasBind(): Boolean

    fun fragmentComponent(): FragmentComponent {
        return mFragmentComponent as FragmentComponent
    }

    companion object {

        private val KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID"
        private val sComponentsArray = LongSparseArray<ConfigPersistentComponent>()
        private val NEXT_ID = AtomicLong(0)
        private val TAG = BaseFragment::class.java.simpleName
    }
}