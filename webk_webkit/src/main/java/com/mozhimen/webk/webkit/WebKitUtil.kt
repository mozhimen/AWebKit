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
    fun setProxy(host: String, port: Int) {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
            UtilKLogWrapper.d(TAG, "setProxy: ")
            val proxyUrl = "${host}:${port}"
            val proxyConfig: ProxyConfig = ProxyConfig.Builder()
                .addProxyRule(proxyUrl)
                .addDirect()//when proxy is not working, use direct connect, maybe?
                .build()
            ProxyController.getInstance().setProxyOverride(
                proxyConfig,
                object : Executor {
                    override fun execute(command: Runnable) {

                    }
                },
                Runnable {
                    UtilKLogWrapper.w(TAG, "setProxy success")
                })
        } else {
            // use the solution of other anwsers
        }
    }

    @SuppressLint("RequiresFeature")
    @JvmStatic
    fun clearProxy() {
        ProxyController.getInstance().clearProxyOverride(
            object : Executor {
                override fun execute(command: Runnable) {

                }
            },
            Runnable {
                UtilKLogWrapper.w(TAG, "clearProxy success")
            }
        )
    }
}