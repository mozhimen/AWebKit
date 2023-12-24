package com.mozhimen.webk.test

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.webk.basic.utils.WebKUtil
import com.mozhimen.webk.test.databinding.ActivityMainBinding

class MainActivity : BaseActivityVB<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.mainBtn.setOnClickListener {
            WebKUtil.startWebKBasicActivity<WebActivity>(this,"这是标题","https://www.baidu.com")
        }
    }
}