package com.swepthong.start.detail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.webkit.WebView
import android.webkit.WebViewClient
import com.airbnb.deeplinkdispatch.DeepLink
import com.swepthong.start.R
import com.swepthong.start.base.BaseActivity
import com.swepthong.start.detail.DetailActivity.Companion.DETAIL_PARAM_URL

import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import java.net.URLDecoder

@DeepLink("gank://androidwing.net/detail/{$DETAIL_PARAM_URL}")
class DetailActivity : BaseActivity() {

    private var url = ""

    override fun createView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            url = URLDecoder.decode(intent.extras.getString(DETAIL_PARAM_URL))
        }

        webView.loadUrl(url)
        webView.webViewClient = (object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    companion object {
        const val DETAIL_PARAM_URL = "url"
    }
}
