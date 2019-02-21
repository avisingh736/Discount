package com.discount.views.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.discount.R
import com.discount.app.config.Constants
import com.discount.models.Content
import com.discount.presenters.AboutUsPresenter
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : AppCompatActivity(), DiscountView {

    val mPresenter = AboutUsPresenter(this)

    override fun onErrorOrInvalid(msg: String) {
        //TODO("not implemented")
    }

    override fun onSuccess(msg: String) {
        //TODO("not implemented")
    }

    override fun progress(flag: Boolean) {
        progressBar.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        ivGoToBack.setOnClickListener { finish() }
        mWebView.webChromeClient = WebChromeClient()
        mWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progress(true)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progress(false)
                super.onPageFinished(view, url)
            }
        }
        mWebView.settings.javaScriptEnabled = true
        mWebView.isHorizontalScrollBarEnabled = false
        mWebView.isVerticalScrollBarEnabled = false
        mWebView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebView.loadUrl("https://discoount.com/admin/terms")
        mPresenter.getContents()
    }

    fun onContent(content: Content) {

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
