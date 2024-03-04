package com.mozhimen.webk.test

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.webk.basic.utils.WebKUtil
import com.mozhimen.webk.test.databinding.ActivityMainBinding

class MainActivity : BaseActivityVDB<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.mainBtn.setOnClickListener {
            WebKUtil.startWebKBasicActivity<WebActivity>(this,"这是标题","https://www.baidu.com")
        }
    }
}