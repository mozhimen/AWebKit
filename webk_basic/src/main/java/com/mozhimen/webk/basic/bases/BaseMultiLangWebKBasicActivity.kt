package com.mozhimen.webk.basic.bases

import android.content.Context
import com.hjq.language.MultiLanguages

/**
 * @ClassName BaseMutilangWebKBasicActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 23:06
 * @Version 1.0
 */
open class BaseMultiLangWebKBasicActivity : BaseWebKBasicActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(MultiLanguages.attach(newBase))
    }
}