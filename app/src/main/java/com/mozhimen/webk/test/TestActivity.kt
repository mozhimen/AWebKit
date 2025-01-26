package com.mozhimen.webk.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.kotlin.elemk.android.webkit.BaseWebChromeClient
import com.mozhimen.kotlin.elemk.android.webkit.BaseWebViewClient
import com.mozhimen.kotlin.utilk.commons.IUtilK
import com.mozhimen.kotlin.utilk.kotlin.getSplitFirstIndexToStart


class TestActivity : AppCompatActivity(), IUtilK {
    private var _webView: WebView? = null
    private val _pages = HashMap<String, Boolean>()

    private val _webViewClient = object : BaseWebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (url != null) {
                if (url.startsWith("https://lelejoy.com")
                    || url.startsWith("https://www.lelejoy.com")
                    || url.startsWith("http://www.lelejoy.com")
                    || url.startsWith("http://lelejoy.com")
                ) {
                    Log.d(TAG, "onPageFinished: skip")
                    return
                }
                if (url.contains("&")) {
                    val newUrl = url.getSplitFirstIndexToStart("&")
                    if (newUrl.isNotEmpty() && !_pages.containsKey(newUrl)) {
                        Log.d(TAG, "onPageFinished:newUrl.isNotEmpty() && !_pages.containsKey(newUrl) newUrl $newUrl")
                        _pages[newUrl] = true
                        // 当页面开始加载新的 URL 时触发该回调
                        // 此时可以在回调中执行自定义逻辑
                        _webView?.evaluateJavascript(
                            """
                async function main() {
    //所有的item条目
    var homeDiv = await getFirstElementByClassName('cz3goc BmP5tf');
    if (homeDiv) {
        console.log('找到第一个条目, 并触发点击事件')

        homeDiv.focus();
        homeDiv.click();
        triggerOnClick(homeDiv)

        console.log('finish success')
    } else {
        var searchTextArea = await getFirstElementByClassName('gLFyf');
        if (searchTextArea) {
            console.log('找到搜索框, 正在置空...')

            const inputEvent = new Event('input', { bubbles: true });
            searchTextArea.value = 'Grand Theft Auto mod lelejoy.com'
            // 聚焦搜索框
            searchTextArea.focus();
            searchTextArea.dispatchEvent(inputEvent);

            console.log('找到搜索框, 赋值')

            // 创建和触发回车键事件
            const enterEvent = new KeyboardEvent('keydown', {
                key: 'Enter',
                keyCode: 13,
                code: 'Enter',
                which: 13,
                bubbles: true,
                cancelable: true
            });
            searchTextArea.dispatchEvent(enterEvent);

            // 有时需要触发 keyup 事件来完成
            const enterUpEvent = new KeyboardEvent('keyup', {
                key: 'Enter',
                keyCode: 13,
                code: 'Enter',
                which: 13,
                bubbles: true,
                cancelable: true
            });
            searchTextArea.dispatchEvent(enterUpEvent);
            console.log('找到搜索框, 点击回车')
        } else {
            console.log('未找搜索框，继续执行后续操作...');
        }
    }
}

main();

function getFirstElementByClassName(className) {
    return new Promise((resolve) => {
        var elements = document.getElementsByClassName(className);
        if (elements.length > 0) {
            console.log('找到元素，类名为 ' + className + '，共 ' + elements.length + ' 个匹配项，第一个元素类型为 ' + elements[0].tagName + '。');
            resolve(elements[0]);
        } else {
            console.log('未找到任何匹配元素，类名为 ' + className + '，第一次尝试。');
            setTimeout(function () {
                elements = document.getElementsByClassName(className);
                if (elements.length > 0) {
                    console.log('再次尝试后找到元素，类名为 ' + className + '，共 ' + elements.length + ' 个匹配项，第一个元素类型为 ' + elements[0].tagName + '。');
                    resolve(elements[0]);
                } else {
                    console.log('再次尝试仍未找到任何匹配元素，类名为 ' + className + '。');
                    resolve(null);
                }
            }, 1500);
        }
    });
}

// function setValueAndTriggerEvents(inputElement, value) {
//     var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;
//     nativeInputValueSetter.call(inputElement, value);
//     inputElement.dispatchEvent(new Event('input', { bubbles: true }));
//     inputElement.dispatchEvent(new Event('change', { bubbles: true }));
// }

function triggerOnClick(element) {
    console.log('正在处理元素:', element.tagName + (element.className ? ' 类名: ' + element.className : ''));

    if (element.onclick) {
        console.log('触发 onclick 事件:', element.tagName);
        element.click();
    }

    var children = element.children;
    for (var i = 0; i < children.length; i++) {
        triggerOnClick(children[i]);
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
            """.trimIndent(), null
                        )
                    }
                } else {
                    if (!_pages.containsKey(url)) {
                        Log.d(TAG, "onPageFinished: !_pages.containsKey(url)")
                        _pages[url] = true
                        // 当页面开始加载新的 URL 时触发该回调
                        // 此时可以在回调中执行自定义逻辑
                        _webView?.evaluateJavascript(
                            """
async function main() {
    //所有的item条目
    var homeDiv = await getFirstElementByClassName('cz3goc BmP5tf');
    if (homeDiv) {
        console.log('找到第一个条目, 并触发点击事件')

        homeDiv.focus();
        homeDiv.click();
        triggerOnClick(homeDiv)

        console.log('finish success')
    } else {
        var searchTextArea = await getFirstElementByClassName('gLFyf');
        if (searchTextArea) {
            console.log('找到搜索框, 正在置空...')

            const inputEvent = new Event('input', { bubbles: true });
            searchTextArea.value = 'Grand Theft Auto mod lelejoy.com'
            // 聚焦搜索框
            searchTextArea.focus();
            searchTextArea.dispatchEvent(inputEvent);

            console.log('找到搜索框, 赋值')

            // 创建和触发回车键事件
            const enterEvent = new KeyboardEvent('keydown', {
                key: 'Enter',
                keyCode: 13,
                code: 'Enter',
                which: 13,
                bubbles: true,
                cancelable: true
            });
            searchTextArea.dispatchEvent(enterEvent);

            // 有时需要触发 keyup 事件来完成
            const enterUpEvent = new KeyboardEvent('keyup', {
                key: 'Enter',
                keyCode: 13,
                code: 'Enter',
                which: 13,
                bubbles: true,
                cancelable: true
            });
            searchTextArea.dispatchEvent(enterUpEvent);
            console.log('找到搜索框, 点击回车')
        } else {
            console.log('未找搜索框，继续执行后续操作...');
        }
    }
}

main();

function getFirstElementByClassName(className) {
    return new Promise((resolve) => {
        var elements = document.getElementsByClassName(className);
        if (elements.length > 0) {
            console.log('找到元素，类名为 ' + className + '，共 ' + elements.length + ' 个匹配项，第一个元素类型为 ' + elements[0].tagName + '。');
            resolve(elements[0]);
        } else {
            console.log('未找到任何匹配元素，类名为 ' + className + '，第一次尝试。');
            setTimeout(function () {
                elements = document.getElementsByClassName(className);
                if (elements.length > 0) {
                    console.log('再次尝试后找到元素，类名为 ' + className + '，共 ' + elements.length + ' 个匹配项，第一个元素类型为 ' + elements[0].tagName + '。');
                    resolve(elements[0]);
                } else {
                    console.log('再次尝试仍未找到任何匹配元素，类名为 ' + className + '。');
                    resolve(null);
                }
            }, 1500);
        }
    });
}

// function setValueAndTriggerEvents(inputElement, value) {
//     var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;
//     nativeInputValueSetter.call(inputElement, value);
//     inputElement.dispatchEvent(new Event('input', { bubbles: true }));
//     inputElement.dispatchEvent(new Event('change', { bubbles: true }));
// }

function triggerOnClick(element) {
    console.log('正在处理元素:', element.tagName + (element.className ? ' 类名: ' + element.className : ''));

    if (element.onclick) {
        console.log('触发 onclick 事件:', element.tagName);
        element.click();
    }

    var children = element.children;
    for (var i = 0; i < children.length; i++) {
        triggerOnClick(children[i]);
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
            """.trimIndent(), null
                        )
                    }
                }
            }
        }
    }

    private val _webChromeClient = object : BaseWebChromeClient() {
    }

    ///////////////////////////////////////////////////////////////////////

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setContentView(_webView)
    }

    ///////////////////////////////////////////////////////////////////////

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        _webView = WebView(this).apply { alpha = 0.1f }
        _webView!!.apply {
            isFocusable = true
            isFocusableInTouchMode = true
            webViewClient = _webViewClient
            webChromeClient = _webChromeClient
            settings.let {
                it.javaScriptEnabled = true//设置支持JS
//            it.builtInZoomControls = true//支持缩放
                it.useWideViewPort = true//将图片调整到适合webView的大小//设置自适应屏幕，两者合用
                it.loadWithOverviewMode = true// 缩放至屏幕的大小

                it.domStorageEnabled = true // 开启DOM
//                it.javaScriptCanOpenWindowsAutomatically = true
//开启支持多窗口
//                it.setSupportMultipleWindows(true)
            }
            loadUrl("https://www.google.com/")
        }
    }
}