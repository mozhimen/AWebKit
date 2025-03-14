package com.mozhimen.webk.basic.impls

import android.webkit.JavascriptInterface
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.kotlin.utilk.kotlin.UtilKStrFile
import com.mozhimen.kotlin.utilk.kotlin.UtilKStrPath
import com.mozhimen.kotlin.utilk.kotlin.getStrFolderPath
import com.mozhimen.kotlin.utilk.kotlin.str2file_use

/**
 * @ClassName JSShowSource
 * @Description TODO
 * @Author mozhimen
 * @Date 2025/3/13
 * @Version 1.0
 */
class JSShowSource(
    private val _print: Boolean = false,
    private val _cache: Boolean = true,
) : IUtilK {
    // 定义一个方法，供 JavaScript 调用
    @JavascriptInterface
    fun showSource(html: String?) {
        if (_print)
            UtilKLogWrapper.d(TAG, "showSource: ${html ?: ""}")
        if (_cache)
            html?.str2file_use(UtilKStrPath.Absolute.Internal.getCache().getStrFolderPath() + "html/${UtilKStrFile.getStrFileName_ofNow()}.html")
    }
}