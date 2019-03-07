package com.discount.views.ui.activities

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import kotlinx.android.synthetic.main.activity_about_us.*

class WebActivity : AppCompatActivity() {

    private fun progress(flag: Boolean) {
        progressBar.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val mBundle = intent.getBundleExtra(Constants.KEY_BUNDLE_PARAM)
        var mAboutUsUrl =  Constants.DOMAIN
        if (mBundle != null) {
            when{
                mBundle.getString(Constants.KEY_TO_WHERE) == Constants.URL_ABOUT -> {
                    tvMainTitle.text = getString(R.string.about_us)
                    mAboutUsUrl = PrefHelper.instance?.getPref(Constants.URL_ABOUT,Constants.DOMAIN)!!
                }
                mBundle.getString(Constants.KEY_TO_WHERE) == Constants.URL_TERMS -> {
                    tvMainTitle.text = getString(R.string.terms_and_conditions)
                    mAboutUsUrl = PrefHelper.instance?.getPref(Constants.URL_TERMS,Constants.DOMAIN)!!
                }
                mBundle.getString(Constants.KEY_TO_WHERE) == Constants.URL_POLICY -> {
                    tvMainTitle.text = getString(R.string.privacy_policy)
                    mAboutUsUrl = PrefHelper.instance?.getPref(Constants.URL_POLICY,Constants.DOMAIN)!!
                }
            }
        }

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
        mWebView.loadUrl(mAboutUsUrl)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
