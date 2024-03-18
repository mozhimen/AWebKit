package com.mozhimen.webk.test

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.webk.multilang.utils.WebKUtil
import com.mozhimen.webk.test.databinding.ActivityMainBinding

class MainActivity : BaseActivityVDB<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.mainBtn.setOnClickListener {
            WebKUtil.startWebMultiLangActivity<WebActivity>(this,"这是标题","https://www.baidu.com")
        }
    }
}