package com.mozhimen.webk.x5.cmmons

import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.WebView

/**
 * @ClassName IOnWebViewListener
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/17 18:32
 * @Version 1.0
 */
interface IOnWebViewListener {
    fun onProgressChanged(webView: WebView?, progress: Int)
    fun onJsAlert(webView: WebView, s: String, s1: String, jsResult: JsResult)
}