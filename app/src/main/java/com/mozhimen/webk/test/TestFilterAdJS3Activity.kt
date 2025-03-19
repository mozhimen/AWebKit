package com.mozhimen.webk.test

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.mozhimen.kotlin.elemk.android.webkit.BaseWebViewClient
import com.mozhimen.kotlin.utilk.android.content.UtilKAssetManager
import com.mozhimen.kotlin.utilk.android.view.applyInVisible
import com.mozhimen.kotlin.utilk.android.view.applyVisible
import com.mozhimen.kotlin.utilk.kotlin.containsAny
import com.mozhimen.webk.multilang.bases.BaseWebKMultiLangActivity
import java.util.Locale


/**
 * @ClassName WebActivity
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/12/24 18:29
 * @Version 1.0
 */
class TestFilterAdJS3Activity : BaseWebKMultiLangActivity() {
    override val webViewClient: BaseWebViewClient = object : BaseWebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            getProgressBar()?.applyVisible()//显示进度条
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            getProgressBar()?.applyInVisible()

            //替换文本
            if (url != null && url.startsWith("https://www.retrogames.cc")) {
                view?.evaluateJavascript(
                    """
                    function replaceRetroWithLeLeJoy() {
                        // 定义一个正则表达式，匹配 "Retro"（不区分大小写）
                        const regex = /Retro/gi;

                        // 递归遍历所有文本节点
                        function walkNode(node) {
                            if (node.nodeType === Node.TEXT_NODE) {
                                // 替换文本内容
                                node.textContent = node.textContent.replace(regex, 'LeLeJoy');
                            } else if (node.nodeType === Node.ELEMENT_NODE) {
                                // 遍历子节点
                                for (let child of node.childNodes) {
                                    walkNode(child);
                                }
                            }
                        }

                        // 从 body 开始遍历
                        walkNode(document.body);
                    }
    
                    function setBackgroundColor() {
                        // 定义目标颜色
                        const backgroundColor = '#0177fd';
                
                        // 获取所有 class="off-menu-close" 的 div 元素
                        const elements = document.querySelectorAll('div.off-menu-close');
                
                        // 遍历所有匹配的元素
                        elements.forEach(element => {
                            // 设置背景颜色
                            element.style.backgroundColor = backgroundColor;
                        });
                    }
    
                    function removeFooterBottom() {
                        const footerBottom = document.getElementById('footer-bottom');
                        if (footerBottom) {
                            footerBottom.remove();
                            console.log('footer-bottom 已移除');
                        } else {
                            console.log('未找到 footer-bottom 元素');
                        }
                    }
    
                    function removeSocialShare() {
                        // 获取所有 class="social-share" 的 div 元素
                        const socialShareDivs = document.querySelectorAll('div.social-share');
                
                        // 检查是否找到匹配的元素
                        if (socialShareDivs.length > 0) {
                            // 遍历所有匹配的元素并移除
                            socialShareDivs.forEach(div => {
                                div.remove();
                            });
                            console.log('所有 social-share 的 div 已移除');
                        } else {
                            console.log('未找到 social-share 的 div 元素');
                        }
                    }
                    
                    function removeDesc() {
                        // 获取所有 class="desc" 的 div 元素
                        const descDivs = document.querySelectorAll('div.desc');
                
                        // 检查是否找到匹配的元素
                        if (descDivs.length > 0) {
                            // 遍历所有匹配的元素并移除
                            descDivs.forEach(div => {
                                div.remove();
                            });
                            console.log('所有 desc 的 div 已移除');
                        } else {
                            console.log('未找到 desc 的 div 元素');
                        }
                    }
                    
                    function removeComments() {
                        const comments = document.getElementById('disqus_thread');
                        if (comments) {
                            comments.remove();
                            console.log('所有 comments 已移除');
                        } else {
                            console.log('未找到 comments 元素');
                        }
                    }
                    
                    function removeSecBg_sidebar() {
                        // 获取所有 class="secBg sidebar" 的 aside 元素
                        const sidebarAside = document.querySelectorAll('aside.secBg.sidebar');
                
                        // 检查是否找到匹配的元素
                        if (sidebarAside.length > 0) {
                            // 遍历所有匹配的元素并移除
                            sidebarAside.forEach(aside => {
                                aside.remove();
                            });
                            console.log('所有 secBg sidebar 的 aside 已移除');
                        } else {
                            console.log('未找到 secBg sidebar 的 aside 元素');
                        }
                    }
                    
                    function removeNavigationNav() {
                        // 获取所有 role="navigation" 的 nav 元素
                        const navigationNavs = document.querySelectorAll('nav[role="navigation"]');

                        // 检查是否找到匹配的元素
                        if (navigationNavs.length > 0) {
                            // 遍历所有匹配的元素并移除
                            navigationNavs.forEach(nav => {
                                nav.remove();
                            });
                            console.log('所有 role="navigation" 的 nav 已移除');
                        } else {
                            console.log('未找到 role="navigation" 的 nav 元素');
                        }
                    }
                    
                    // 执行替换函数
                    replaceRetroWithLeLeJoy();
                    // 移除nav
                    removeNavigationNav();
                    // 设置背景色
                    setBackgroundColor();
                    // 移除底部
                    removeFooterBottom();
                    // 移除social share
                    removeSocialShare();
                    // 移除desc
                    removeDesc();
                    // 移除Comments
                    removeComments();
                    // 移除secBg sidebar
                    removeSecBg_sidebar();
                """.trimIndent(), null
                )
            }
        }

        override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
            val url = request?.url.toString()
            return if (url.containsAny(
                    listOf(
                        "cdn.bncloudfl.com/bn/730/e27/758/730e277581be4ea1a14c2cddfbccf64d3c58af71.gif",
                        "https://cdn.bncloudfl.com/bn/353/b6b/2a3/353b6b2a3cc4c3c3faf282bd47febe016406018d.png"
                    )
                )
            ) {
                try {
                    WebResourceResponse("image/png", "UTF-8", UtilKAssetManager.open(this@TestFilterAdJS3Activity, "trans.png"))
                } catch (e: Exception) {
                    e.printStackTrace()
                    WebResourceResponse(null, null, null)
                }
            } else if (url.contains("https://www.retrogames.cc/images/logo.png?v=20180731")) {
                try {
                    WebResourceResponse("image/png", "UTF-8", UtilKAssetManager.open(this@TestFilterAdJS3Activity, "trans_251_38.png"))
                } catch (e: Exception) {
                    e.printStackTrace()
                    WebResourceResponse(null, null, null)
                }
            } else if (!hasAd(url.toLowerCase(Locale.ROOT))) {
                super.shouldInterceptRequest(view, request)//正常加载
            } else WebResourceResponse(null, null, null) //含有广告资源屏蔽请求
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url.toString()
            if (url.containsAny("https://endowmentoverhangutmost.com", "https://curlyluxurypregnancy.com/")) {
                return true
            } else return super.shouldOverrideUrlLoading(view, request)
        }
    }

    @SuppressLint("JavascriptInterface")
    override fun initView(savedInstanceState: Bundle?) {
        setToolbarBackgroundColor(getColor(R.color.white))
        super.initView(savedInstanceState)
    }

    override fun getToolbarNavigationIconRes(): Int? {
        return R.drawable.baseline_arrow_back_24
    }

    ///////////////////////////////////////////////////////////////////


    private fun hasAd(url: String): Boolean {
        return url.containsAny(
            listOf(
                "ep2.adtrafficquality.google",
                "ep1.adtrafficquality.google",
                "pagead2.googlesyndication.com",
                "www.googleadservices.com",
                "googleads.g.doubleclick.net",
                "tpc.googlesyndication.com/pagead",
                "ads.as.criteo.com",
                "ad.a-ads.com",
                "www.google.co.jp/ads",
//                    "cdn.bncloudfl.com/bn/730/e27/758/730e277581be4ea1a14c2cddfbccf64d3c58af71.gif",
//                    "https://www.emulatorjs.com/ad.html?domain=www.retrogames.cc",
            )
        )
    }
}