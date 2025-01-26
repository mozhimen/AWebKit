package com.mozhimen.webk.browser.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.webk.browser.utils.CustomTabsIntentUtil
import com.mozhimen.kotlin.utilk.kotlin.strUrl2uri

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CustomTabsIntentUtil.launchUrl(this,"https://www.baidu.com".strUrl2uri())
    }
}