package com.mozhimen.webk.multilang.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.mozhimen.kotlin.elemk.commons.IExt_Listener
import com.mozhimen.kotlin.utilk.android.content.startActivityForResult
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.webk.multilang.bases.BaseWebKMultiLangBarActivity

/**
 * @ClassName WebKUtil
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 18:32
 * @Version 1.0
 */
object WebKUtil : IUtilK {
    @JvmStatic
    inline fun <reified A : BaseWebKMultiLangBarActivity> startWebMultiLangActivity(context: Context, strUrl: String) {
        startWebMultiLangActivity<A>(context, "", strUrl)
    }

    @JvmStatic
    inline fun <reified A : BaseWebKMultiLangBarActivity> startWebMultiLangActivity(context: Context, strUrl: String, otherBlock: IExt_Listener<Intent>) {
        context.startContext<A> {
            putExtra(BaseWebKMultiLangBarActivity.EXTRA_WEBKIT_BASIC_TITLE, "")
            putExtra(BaseWebKMultiLangBarActivity.EXTRA_WEBKIT_BASIC_URl, strUrl)
            otherBlock.invoke(this)
        }
    }

    @JvmStatic
    inline fun <reified A : BaseWebKMultiLangBarActivity> startWebMultiLangActivity(context: Context, title: String, strUrl: String) {
        UtilKLogWrapper.d(TAG, "startWebMultiLangActivity title $title strUrl $strUrl")
        context.startContext<A> {
            putExtra(BaseWebKMultiLangBarActivity.EXTRA_WEBKIT_BASIC_TITLE, title)
            putExtra(BaseWebKMultiLangBarActivity.EXTRA_WEBKIT_BASIC_URl, strUrl)
        }
    }

    @JvmStatic
    inline fun <reified A : BaseWebKMultiLangBarActivity> startWebMultiLangActivity(context: Context, title: String, strUrl: String, otherBlock: IExt_Listener<Intent>) {
        UtilKLogWrapper.d(TAG, "startWebMultiLangActivity title $title strUrl $strUrl")
        context.startContext<A> {
            putExtra(BaseWebKMultiLangBarActivity.EXTRA_WEBKIT_BASIC_TITLE, title)
            putExtra(BaseWebKMultiLangBarActivity.EXTRA_WEBKIT_BASIC_URl, strUrl)
            otherBlock.invoke(this)
        }
    }

    @JvmStatic
    inline fun <reified A : BaseWebKMultiLangBarActivity> startWebMultiLangActivityForResult(activity: Activity, requestCode: Int, title: String, strUrl: String, otherBlock: IExt_Listener<Intent>) {
        UtilKLogWrapper.d(TAG, "startWebMultiLangActivity title $title strUrl $strUrl")
        activity.startActivityForResult<A>(requestCode) {
            putExtra(BaseWebKMultiLangBarActivity.EXTRA_WEBKIT_BASIC_TITLE, title)
            putExtra(BaseWebKMultiLangBarActivity.EXTRA_WEBKIT_BASIC_URl, strUrl)
            otherBlock.invoke(this)
        }
    }
}