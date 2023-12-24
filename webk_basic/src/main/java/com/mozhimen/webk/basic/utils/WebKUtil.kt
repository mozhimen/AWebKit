package com.mozhimen.webk.basic.utils

import android.content.Context
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.webk.basic.bases.BaseWebKBasicActivity

/**
 * @ClassName WebKUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 18:32
 * @Version 1.0
 */
object WebKUtil {
    @JvmStatic
    inline fun <reified A : BaseWebKBasicActivity> startWebKBasicActivity(context: Context, strUrl: String) {
        startWebKBasicActivity<A>(context, "", strUrl)
    }

    @JvmStatic
    inline fun <reified A : BaseWebKBasicActivity> startWebKBasicActivity(context: Context, title: String, strUrl: String) {
        context.startContext<A> {
            putExtra(BaseWebKBasicActivity.EXTRA_WEBKIT_BASIC_TITLE, title)
            putExtra(BaseWebKBasicActivity.EXTRA_WEBKIT_BASIC_URl, strUrl)
        }
    }
}