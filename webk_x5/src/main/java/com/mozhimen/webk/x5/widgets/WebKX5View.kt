package com.mozhimen.webk.x5.widgets

import android.annotation.SuppressLint
 import android.content.Context
import android.util.AttributeSet
import com.mozhimen.webk.x5.cmmons.IOnWebViewListener
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @ClassName WebKX5View
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/17 18:17
 * @Version 1.0
 */
class WebKX5View : WebView {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?, i: Int) : super(context, attributeSet, i) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?, i: Int, b: Boolean) : super(context, attributeSet, i, b) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet?, i: Int, map: Map<String?, Any?>?, b: Boolean) : super(context, attributeSet, i, map, b) {
        init(context)
    }

    /////////////////////////////////////////////////////////////////////////////////

    private var _context: Context? = null //    上下文
    private var _onWebViewListener: IOnWebViewListener? = null//    回调接口

    /////////////////////////////////////////////////////////////////////////////////

    fun setOnWebViewListener(listener: IOnWebViewListener) {
        _onWebViewListener = listener
    }

    /////////////////////////////////////////////////////////////////////////////////

    private fun init(context: Context) {
        _context = context
//      打开此代码可使移动设备链接 chrome 调试
//        setWebContentsDebuggingEnabled(true);
//        设置 jsBridge
//        addJavascriptInterface(MyJaveScriptInterface(_context), "androidJSBridge")
        initWebViewSettings()//        webView 设置
        initWebViewClient()//        webClient 设置
        initChromeClient()//        chromeClient 设置
    }

    //webView 设置
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        settings.apply {
            javaScriptEnabled = true//        允许运行 js 代码
            setSupportZoom(false)//        不可缩放
            builtInZoomControls = false
            displayZoomControls = true
            cacheMode = WebSettings.LOAD_DEFAULT //        设置缓存策略
        }
    }

    //webClient 设置
    private fun initWebViewClient() {
        webViewClient = object : WebViewClient() {}//        设置网页在APP 内部打开，而不是用外部浏览器
    }

    //chromeClient 设置
    private fun initChromeClient() {
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webView: WebView, i: Int) {
                _onWebViewListener?.onProgressChanged(webView, i)//                回调网页加载状态
            }

            //监听alert弹出框，使用原生弹框代替alert。
            override fun onJsAlert(webView: WebView, s: String, s1: String, jsResult: JsResult): Boolean {
//                val builder = AlertDialog.Builder(_context)
//                builder.setMessage(s1)
//                builder.setNegativeButton("确定", null)
//                builder.create().show()
                _onWebViewListener?.onJsAlert(webView, s, s1, jsResult)
                jsResult.confirm()
                return true
            }
        }
    }
}

