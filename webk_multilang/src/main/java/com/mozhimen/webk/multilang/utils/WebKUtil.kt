package com.mozhimen.webk.multilang.utils

import android.content.Context
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.webk.multilang.bases.BaseWebKMultiLangActivity

/**
 * @ClassName WebKUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 18:32
 * @Version 1.0
 */
object WebKUtil {
    @JvmStatic
    inline fun <reified A : BaseWebKMultiLangActivity> startWebMultiLangActivity(context: Context, strUrl: String) {
        startWebMultiLangActivity<A>(context, "", strUrl)
    }

    @JvmStatic
    inline fun <reified A : BaseWebKMultiLangActivity> startWebMultiLangActivity(context: Context, title: String, strUrl: String) {
        context.startContext<A> {
            putExtra(BaseWebKMultiLangActivity.EXTRA_WEBKIT_BASIC_TITLE, title)
            putExtra(BaseWebKMultiLangActivity.EXTRA_WEBKIT_BASIC_URl, strUrl)
        }
    }
}