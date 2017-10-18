package com.swepthong.start.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.swepthong.start.R
import com.swepthong.start.base.BaseActivity
import com.swepthong.start.util.router
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.content_start.*
import java.net.URLEncoder
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeView {

    @Inject lateinit var presenter: HomePresenter
    @Inject lateinit var adapter: HomeAdapter

    override fun createView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_start)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent().inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.attachView(this)
        init()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun init() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
/*        recycler_view.addItemDecoration(GridItemDecoration(
                GridItemDecoration.Builder(this)
                        .horSize(Util.getScreenDensity(this).toInt() * 8)
                        .color(R.color.black_8p)))*/
        adapter.setList(presenter.getList())
        presenter.requestData()

        adapter.setOnItemClickListener {
            pos ->
            val url = URLEncoder.encode(presenter.getList()[pos].url)
            router(this, DETAIL + url)
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_start, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showProgress(show: Boolean) {
    }

    override fun showError(error: String?) {
    }

    override fun refreshList() {
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val DETAIL = "gank://androidwing.net/detail/"
    }
}
