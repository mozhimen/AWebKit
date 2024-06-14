# 内置浏览器安全策略
代码中强制设置不使用『安全浏览策略』
Android O 以上
webviewSetting.setSafeBrowsingEnabled(false)
```
<manifest>
    <application>
        <meta-data android:name="android.webkit.WebView.EnableSafeBrowsing"
android:value="false" />
        ...
    </application>
</manifest>
```