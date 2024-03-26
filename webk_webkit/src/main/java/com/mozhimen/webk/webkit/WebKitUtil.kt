package com.mozhimen.webk.webkit

import android.annotation.SuppressLint
import androidx.webkit.ProxyConfig
import androidx.webkit.ProxyController
import androidx.webkit.WebViewFeature
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.utilk.commons.IUtilK
import java.util.concurrent.Executor

/**
 * @ClassName WebKitUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/25
 * @Version 1.0
 */
object WebKitUtil : IUtilK {
    @JvmStatic
    fun setProxy(protocol: String, host: String, port: Int) {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
            val proxyUrl = "${protocol}://${host}:${port}"
            UtilKLogWrapper.d(TAG, "setProxy: proxyUrl $proxyUrl")
            val proxyConfig: ProxyConfig = ProxyConfig.Builder()
                .addProxyRule(proxyUrl)
                .addDirect()//when proxy is not working, use direct connect, maybe?
                .build()
            ProxyController.getInstance().setProxyOverride(
                proxyConfig,
                {
                    UtilKLogWrapper.w(TAG, "setProxy success")
                },
                {
                    UtilKLogWrapper.w(TAG, "setProxy changed")
                })
        } else {
            // use the solution of other anwsers
//            val invokeBool = WebviewSettingProxy.setProxy(
//                mAgentWeb?.getWebCreator()?.webView,
//                "192.168.1.238",
//                8090,
//                "这里填 Application 的包名：com.example.test.Application"
//            )
//            Logger.i("不支持设置代理, 使用反射等方式尝试: " + invokeBool)
        }
    }

    @SuppressLint("RequiresFeature")
    @JvmStatic
    fun clearProxy() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
            ProxyController.getInstance().clearProxyOverride(
                {
                    UtilKLogWrapper.w(TAG, "clearProxy")
                },
                {
                    UtilKLogWrapper.w(TAG, "clearProxy success")

                }
            )
        } else {
//                    WebviewSettingProxy.revertBackProxy(
//                        mAgentWeb?.webCreator?.webView,
//                        "这里填 Application 的包名：com.example.test.Application"
//                    )
        }
    }
}