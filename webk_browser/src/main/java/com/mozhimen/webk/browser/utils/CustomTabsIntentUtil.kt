package com.mozhimen.webk.browser.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.mozhimen.basick.elemk.commons.IExt_Listener

/**
 * @ClassName CustomTabsIntentUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/1
 * @Version 1.0
 */
object CustomTabsIntentUtil {
    @JvmStatic
    fun launchUrl(context: Context, uri: Uri) {
        CustomTabsIntent.Builder().build().launchUrl(context, uri)
    }

    @JvmStatic
    fun launchUrl(context: Context, uri: Uri, intentBuilderBlock: IExt_Listener<CustomTabsIntent.Builder>) {
        CustomTabsIntent.Builder().apply { this.intentBuilderBlock() }.build().launchUrl(context, uri)
    }

//    //设置颜色方案
//    @JvmStatic
//    fun launchUrl(context: Context, uri: Uri, block: IExtension_Listener<CustomTabColorSchemeParams.Builder>) {
//        val schemeParams = CustomTabColorSchemeParams.Builder()
//            .apply {
//                //.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
//                //.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
//                this.block()
//            }
//            .build()
//        CustomTabsIntent.Builder()
//            .setDefaultColorSchemeParams(schemeParams)
//            .build().launchUrl(context, uri)
//    }
}