package com.mozhimen.webk.basic.bases

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseBarActivityVB
import com.mozhimen.basick.utilk.android.view.applyInVisible
import com.mozhimen.basick.utilk.android.view.applyVisible
import com.mozhimen.webk.basic.databinding.ActivityWebkBasicBinding


/**
 * @ClassName WebKBasicActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 16:06
 * @Version 1.0
 */
open class BaseWebKBasicActivity : BaseBarActivityVB<ActivityWebkBasicBinding>() {
    companion object {
        const val EXTRA_WEBKIT_BASIC_TITLE = "EXTRA_WEBKIT_BASIC_TITLE"
        const val EXTRA_WEBKIT_BASIC_URl = "EXTRA_WEBKIT_BASIC_URl"
    }

    ///////////////////////////////////////////////////////////////////////

    private var _webView: WebView? = null
    private val _webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            vb.webkBasicProgress.applyVisible()//显示进度条
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            vb.webkBasicProgress.applyInVisible()
        }
    }

    private val _webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            vb.webkBasicProgress.progress = newProgress
        }
    }

    ///////////////////////////////////////////////////////////////////////

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {

        intent.getStringExtra(EXTRA_WEBKIT_BASIC_TITLE)?.let {
            title = it
        }
        _webView = vb.webkBasicWebView
        _webView!!.apply {
            webViewClient = _webViewClient
            webChromeClient = _webChromeClient
            settings.let {
                it.javaScriptEnabled = true//设置支持JS
//            it.builtInZoomControls = true//支持缩放
                it.useWideViewPort = true//将图片调整到适合webView的大小//设置自适应屏幕，两者合用
                it.loadWithOverviewMode = true// 缩放至屏幕的大小
            }
        }
        intent.getStringExtra(EXTRA_WEBKIT_BASIC_URl)?.let {
            vb.webkBasicWebView.loadUrl(it)
        }
    }

    override fun onResume() {
        super.onResume()
        _webView?.apply {
            onResume()
            resumeTimers()
        }
    }

    override fun onPause() {
        super.onPause()
        _webView?.apply {
            onPause()
            pauseTimers()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _webView?.apply {
            Log.d(TAG, "onDestroy: webView destroy")
            clearHistory()//清除历史记录
            stopLoading()//停止加载
            loadUrl("about:blank") //加载一个空白页
            webChromeClient = null
            webViewClient = WebViewClient()
            removeAllViews()//移除WebView所有的View对象
            destroy()//销毁此的WebView的内部状态
        }
        _webView = null
    }
}