package com.swepthong.start.base

import android.os.Bundle
import android.support.v4.util.LongSparseArray
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import com.swepthong.start.StartApplication
import com.swepthong.start.injection.component.ActivityComponent
import com.swepthong.start.injection.component.ConfigPersistentComponent
import com.swepthong.start.injection.component.DaggerConfigPersistentComponent
import com.swepthong.start.injection.module.ActivityModule
import com.swepthong.start.util.setupActionBar
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.concurrent.atomic.AtomicLong

/**
 * Created by xiangrui on 17-10-17.
 */
abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    private var mActivityComponent: ActivityComponent? = null
    private var mActivityId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createView(savedInstanceState)

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()
        val configPersistentComponent: ConfigPersistentComponent
        if (sComponentsArray.get(mActivityId) == null) {
            info("Creating new  ConfigPersistentComponent id=$mActivityId")
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(StartApplication[this].component)
                    .build()
            sComponentsArray.put(mActivityId, configPersistentComponent)
        } else {
            info("Reusing ConfigPersistentComponent id=$mActivityId")
            configPersistentComponent = sComponentsArray.get(mActivityId)
        }
        mActivityComponent = configPersistentComponent.activityComponent(ActivityModule(this))
        mActivityComponent?.inject(this)
    }

    abstract fun createView(savedInstanceState: Bundle?)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, mActivityId)
    }

    fun setupToolbar(toolbar: Toolbar, hasBackIcon: Boolean) {
        setupActionBar(toolbar) {
            setDisplayHomeAsUpEnabled(hasBackIcon)
            setDisplayShowHomeEnabled(hasBackIcon)
        }
    }

    override val loggerTag: String
        get() = super.loggerTag

    override fun onDestroy() {
        if (!isChangingConfigurations) {
            info("Clearing ConfigPersistentComponent id=$mActivityId")
            sComponentsArray.remove(mActivityId)
        }
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun activityComponent(): ActivityComponent {
        return mActivityComponent as ActivityComponent
    }

    companion object {
        private const val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        private val NEXT_ID = AtomicLong(0)
        private val sComponentsArray = LongSparseArray<ConfigPersistentComponent>()
    }
}