package com.mozhimen.webk.multilang.bases

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.DownloadListener
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import com.mozhimen.kotlin.utilk.android.view.applyInVisible
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.kotlin.elemk.android.webkit.bases.BaseWebChromeClient
import com.mozhimen.kotlin.elemk.android.webkit.bases.BaseWebViewClient
import com.mozhimen.kotlin.elemk.androidx.appcompat.commons.IActivity
import com.mozhimen.kotlin.elemk.androidx.appcompat.commons.IFragment
import com.mozhimen.kotlin.elemk.androidx.fragment.bases.BaseFragment
import com.mozhimen.kotlin.elemk.commons.IExt_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindViewLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.utilk.androidx.fragment.UtilKFragment
import com.mozhimen.kotlin.utilk.kotlin.UtilKLazyJVM
import com.mozhimen.webk.multilang.widgets.WebKMultiLangView

/**
 * @ClassName WebKBasicActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 16:06
 * @Version 1.0
 */
open class BaseWebKMultiLangFragment : BaseFragment(), IFragment, IActivity, DownloadListener {
    companion object {
        const val EXTRA_WEBKIT_BASIC_TITLE = "EXTRA_WEBKIT_BASIC_TITLE"
        const val EXTRA_WEBKIT_BASIC_URl = "EXTRA_WEBKIT_BASIC_URl"
    }

    ///////////////////////////////////////////////////////////////////////

    protected var _webView: WebKMultiLangView? = null

    ///////////////////////////////////////////////////////////////////////

    protected val basicUrl by UtilKLazyJVM.lazy_ofNone { arguments?.getString(EXTRA_WEBKIT_BASIC_URl) }

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
        view?.findViewById(com.mozhimen.webk.multilang.R.id.webk_basic_progress)

    protected open fun getWebView(): WebKMultiLangView? =
        view?.findViewById(com.mozhimen.webk.multilang.R.id.webk_basic_web_view)

    protected open fun getWebViewGenerator(): IExt_Listener<WebView>? = null

    //////////////////////////////////////////////////////////////////////////////

    //@warn 如果子类可以继承, 这里子类的VB一定要放置在第一个
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inflateView(container)
        return inflater.inflate(getRootLayoutId(), container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            initLayout()
            initData(savedInstanceState)
        } catch (e: Exception) {
            e.printStackTrace()
            UtilKLogWrapper.e(TAG, "onViewCreated: e ${e.message}")
        }
    }

    override fun inflateView(viewGroup: ViewGroup?) {

    }

    override fun initLayout() {
    }

    @CallSuper
    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initObserver()
        initEvent()
    }

    @OptIn(OApiCall_BindViewLifecycle::class, OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        _webView = getWebView()
        _webView!!.apply {
            isFocusable = true
            isFocusableInTouchMode = true
            webViewClient = this@BaseWebKMultiLangFragment.webViewClient
            webChromeClient = this@BaseWebKMultiLangFragment.webChromeClient
            settings.let {
                it.javaScriptEnabled = true//设置支持JS
//            it.builtInZoomControls = true//支持缩放
                it.useWideViewPort = true//将图片调整到适合webView的大小//设置自适应屏幕，两者合用
                it.loadWithOverviewMode = true// 缩放至屏幕的大小

                it.domStorageEnabled = true // 开启DOM
            }
            setDownloadListener(this@BaseWebKMultiLangFragment)
            getWebViewGenerator()?.invoke(this)
        }
        basicUrl?.let {
            getWebView()?.loadUrl(it.also { UtilKLogWrapper.d(TAG, "initView: basicUrl $basicUrl") })
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