package com.mozhimen.webk.basic

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.hjq.language.MultiLanguages

/**
 * @ClassName WebKMultiLangView
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 23:03
 * @Version 1.0
 */
class WebKMultiLangView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : WebView(context, attrs, defStyleAttr) {
    init {
        // 修复 WebView 初始化时会修改 Activity 语种配置的问题
        MultiLanguages.updateAppLanguage(context)
    }
}