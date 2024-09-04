package com.mozhimen.webk.basic

import android.app.Application
import android.content.Context
import android.webkit.WebView
import com.mozhimen.kotlin.lintk.optins.OApiInit_InApplication
import com.mozhimen.kotlin.utilk.android.os.UtilKBuildVersion
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @ClassName WebKMgr
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/21
 * @Version 1.0
 */
object WebKMgr {
    private var _isInitWeb = AtomicBoolean(false)
    /**
     * 如果您的应用必须在多个进程中使用 WebView实例，则您必须先使用 WebView.setDataDirectorySuffix() 方法为每个进程指定唯一的数据目录后缀，然后再在相应进程中使用 WebView 的给定实例。该方法会将每个进程的网络数据放入应用数据目录内其自己的目录中。
     * 在进程中的任何WebView实例被创建之前，并且在此进程中调用android.webkit包中的任何其他方法之前，必须先调用WebView.setDataDirectorySuffix()设置进程的数据目录。
     */
    @JvmStatic
    @OApiInit_InApplication
    fun init(context: Context) {
        //Android 9 or above must be set
        if (UtilKBuildVersion.isAfterV_28_9_P() && _isInitWeb.compareAndSet(false,true)) {
            val processName = Application.getProcessName()
            if (context.packageName != processName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
    }
}