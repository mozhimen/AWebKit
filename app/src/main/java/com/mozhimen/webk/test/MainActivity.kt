package com.mozhimen.webk.test

import android.os.Bundle
import com.mozhimen.uik.databinding.bases.viewdatabinding.activity.BaseActivityVDB
import com.mozhimen.webk.multilang.utils.WebKUtil
import com.mozhimen.webk.test.databinding.ActivityMainBinding

class MainActivity : BaseActivityVDB<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vdb.mainBtn.setOnClickListener {
            WebKUtil.startWebMultiLangActivity<TestFilterAdJS3Activity>(this, "Web Address","https://www.retrogames.cc/"/*"https://modsfire.com/Ga750cl021NYYlu"*//* "https://mxdrop.to/f/wl90k3d7tmqo7j"*//*"https://www.baidu.com"*/)
        }
    }
}