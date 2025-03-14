package com.mozhimen.webk.multilang.bases

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.DownloadListener
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import com.hjq.language.MultiLanguages
import com.mozhimen.basick.bases.BaseBarActivity
import com.mozhimen.kotlin.utilk.android.view.applyInVisible
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.webk.multilang.databinding.ActivityWebkBasicBinding
import com.mozhimen.uik.databinding.bases.viewdatabinding.activity.BaseBarActivityVDB
import com.mozhimen.kotlin.elemk.android.webkit.BaseWebChromeClient
import com.mozhimen.kotlin.elemk.android.webkit.BaseWebViewClient
import com.mozhimen.kotlin.elemk.commons.IExt_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM

/**
 * @ClassName WebKBasicActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 16:06
 * @Version 1.0
 */
open class BaseWebKMultiLangActivity : BaseBarActivity(), DownloadListener {
    companion object {
        const val EXTRA_WEBKIT_BASIC_TITLE = "EXTRA_WEBKIT_BASIC_TITLE"
        const val EXTRA_WEBKIT_BASIC_URl = "EXTRA_WEBKIT_BASIC_URl"
    }

    ///////////////////////////////////////////////////////////////////////

    protected var _webView: WebView? = null

    ///////////////////////////////////////////////////////////////////////

    protected val basicUrl by UtilKLazyJVM.lazy_ofNone { intent.getStringExtra(EXTRA_WEBKIT_BASIC_URl) }

    protected open val webViewClient = object : BaseWebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            getProgressBar()?.applyVisible()//显示进度条
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            getProgressBar()?.applyInVisible()
        }
    }

    protected open val webChromeClient = object : BaseWebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            getProgressBar()?.progress = newProgress
        }
    }

    protected open fun getRootLayoutId(): Int =
        com.mozhimen.webk.multilang.R.layout.activity_webk_basic

    protected open fun getProgressBar(): ProgressBar? =
        findViewById(com.mozhimen.webk.multilang.R.id.webk_basic_progress)

    protected open fun getWebView(): WebView =
        findViewById(com.mozhimen.webk.multilang.R.id.webk_basic_web_view)

    protected open fun getWebViewGenerator(): IExt_Listener<WebView>? = null

    ///////////////////////////////////////////////////////////////////////

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(MultiLanguages.attach(newBase))
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            initFlag()
            initLayout()
            initData(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "onCreate: e ${e.message}")
        }
    }

    override fun initLayout() {
        setContentView(getRootLayoutId())
        super.initLayout()
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initObserver()
    }

    @OptIn(OApiCall_BindViewLifecycle::class, OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {

        intent.getStringExtra(EXTRA_WEBKIT_BASIC_TITLE)?.let {
            toolBarProxy.setToolbarText(it)
        }
        _webView = getWebView()
        _webView!!.apply {
            isFocusable = true
            isFocusableInTouchMode = true
            webViewClient = this@BaseWebKMultiLangActivity.webViewClient
            webChromeClient = this@BaseWebKMultiLangActivity.webChromeClient
            settings.let {
                it.javaScriptEnabled = true//设置支持JS
//            it.builtInZoomControls = true//支持缩放
                it.useWideViewPort = true//将图片调整到适合webView的大小//设置自适应屏幕，两者合用
                it.loadWithOverviewMode = true// 缩放至屏幕的大小

                it.domStorageEnabled = true // 开启DOM
            }
            setDownloadListener(this@BaseWebKMultiLangActivity)
            getWebViewGenerator()?.invoke(this)
        }
        basicUrl?.let {
            getWebView().loadUrl(it.also { UtilKLogWrapper.d(TAG, "initView: basicUrl $basicUrl") })
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (_webView?.canGoBack() == true) {
            _webView?.goBack()
        } else
            super.onBackPressed()
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
            UtilKLogWrapper.d(TAG, "onDestroy: webView destroy")
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

    ///////////////////////////////////////////////////////////////////////

    override fun onDownloadStart(url: String?, userAgent: String?, contentDisposition: String?, mimetype: String?, contentLength: Long) {
        UtilKLogWrapper.d(TAG, "onDownloadStart: url: $url userAgent: $userAgent contentDisposition: $contentDisposition contentLength: $contentLength")
    }
}