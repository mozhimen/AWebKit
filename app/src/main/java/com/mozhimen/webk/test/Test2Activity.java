//package com.mozhimen.webk.test;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Looper;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.View;
//import android.webkit.ConsoleMessage;
//import android.webkit.CookieManager;
//import android.webkit.JavascriptInterface;
//import android.webkit.ValueCallback;
//import android.webkit.WebChromeClient;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebResourceResponse;
//import android.webkit.WebStorage;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Test2Activity extends AppCompatActivity {
//    private WebView webView1;
//
//    String host = "http://47.116.71.50";//生产
//
//    boolean retry = false;
//
//    int count = 0;
//    String URL = "https://www.midasbuy.com/midasbuy/by/redeem/tarisland";
//
//    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1; // 请求存储权限的请求码
//    private static final int REQUEST_CODE_MANAGE_STORAGE_PERMISSION = 2; // 请求管理所有文件权限的请求码
//
//    long id = 0;
//    String game = "";
//    String uid = "";//游戏中ID
//    String serialCode = "";
//
//    String area;
//    String ranking;
//    String server;
//
//    String userName = "";//米大师账号
//    String cookie = "";
//
//    boolean isLogin = false;
//
//    public void initData(InitDataCallback callback) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String str = host + "/midas/task/pull/TarisLand";
//                String response = OkHttpUtil.postUrl(str);
//                System.out.println("allen=======jsonBody Response: " + response);
//
//                try {
//                    JSONObject obj = new JSONObject(response);
//                    int code = obj.getInt("code");
//
//                    if (code == 1) {
//                        JSONObject data = obj.getJSONObject("data");
//                        id = data.getLong("id");
//                        game = data.getString("game");
//                        uid = data.getString("uid");
//                        serialCode = data.getString("serialCode");
//                        try {
//                            serialCode = MidasApiUtil.codeDecryption(serialCode);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        userName = data.getString("mdsAccount");
//                        cookie = data.getString("cookie");
//
//
//                        area = data.getString("area");
//                        ranking = data.getString("ranking");
//                        server = data.getString("server");
//
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                callback.onDataInitialized();
//                            }
//                        });
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//
//    public void useAccount(InitDataCallback callback) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String str = host + "/midas/account/useAccount";
//                String response = OkHttpUtil.postUrl(str);
//                System.out.println("allen=======jsonBody Response: " + response);
//
//                try {
//                    JSONObject obj = new JSONObject(response);
//                    int code = obj.getInt("code");
//
//                    if (code == 1) {
//                        JSONObject data = obj.getJSONObject("data");
//
//                        userName = data.getString("userName");
//                        cookie = data.getString("cookie");
//                 /*       area = data.getString("area");
//                        ranking = data.getString("ranking");
//                        server = data.getString("server");*/
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                callback.onDataInitialized();
//                            }
//                        });
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//
//    public interface InitDataCallback {
//        void onDataInitialized();
//    }
//
//
//    String imageResponse = null;
//
//    public void upload(int resultCode, String notice, File imageFile) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String str = host + "/midas/task/upload";
//                String cookies = "";
//
//                if (resultCode == -7) {
//                    cookies = "";
//                } else {
//                    cookies = CookieManager.getInstance().getCookie(URL);
//                }
//
//                // 创建一个CountDownLatch，初始值为1
//                CountDownLatch latch = new CountDownLatch(1);
//
//                // 创建一个新的线程来上传图片文件
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String imageUploadUrl = host + "/midas/task/uploadImage";
//                        OkHttpClient okHttpClient = OkHttpUtil.getOkHttpClient();
//                        Headers headers = new Headers.Builder().build();
//                        if (imageFile != null) {
//                            imageResponse = OkHttpUtil.postImage(okHttpClient, imageUploadUrl, imageFile, headers);
//                            System.out.println("allen=======imageResponse: " + imageResponse);
//                        }
//
//                        // 图片上传完成后，减少CountDownLatch的计数
//                        latch.countDown();
//                    }
//                }).start();
//
//                try {
//                    // 等待图片上传完成，最多等待10秒
//                    boolean completed = latch.await(20, TimeUnit.SECONDS);
//                    if (!completed) {
//                        imageResponse = null; // 超时处理
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    imageResponse = null; // 异常处理
//                }
//
//                // 解析imageResponse并获取imgUrl
//                String imgUrl = "";
//
//
//                if (imageResponse != null) {
//                    try {
//                        JSONObject imageResponseObj = new JSONObject(imageResponse);
//                        int imageResponseCode = imageResponseObj.getInt("code");
//                        if (imageResponseCode == 1) {
//                            imgUrl = imageResponseObj.getString("data");
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                // 调用 postJson 方法发送请求，并传入自定义 headers
//                Headers headers = new Headers.Builder()
//                        .add("Content-Type", "application/json; charset=utf-8")
//                        .build();
//
//
//                JSONObject jsonBody = new JSONObject();
//                try {
//                    jsonBody.put("id", id);
//                    jsonBody.put("imgUrl", imgUrl);
//                    jsonBody.put("resultCode", resultCode);
//                    jsonBody.put("mdsAccount", userName);
//                    jsonBody.put("cookie", cookies);
//                    jsonBody.put("errMsg", notice);
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//                System.out.println("allen=======jsonBody" + jsonBody);
//
//                String response = OkHttpUtil.postJson(OkHttpUtil.getOkHttpClient(), str, jsonBody.toString(), headers);
//
//                JSONObject obj = null;
//                try {
//                    obj = new JSONObject(response);
//                    int code = obj.getInt("code");
//
//                    if (code == 1) {
//                        // 使用Handler切换回主线程
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//                                System.out.println("allen=======jsonBodyUser 上传状态 成功 ");
//                            }
//                        });
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("allen=======jsonBody Response: " + response);
//
//                releaseAccount();
//
//                // 创建一个Handler对象，绑定到主线程的Looper
//                if (response != null) {
//                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            // 在主线程延迟2秒后执行这些操作
//                            if (imageFile != null) {
//                                boolean b = imageFile.delete();
//                                System.out.println("allen=======imageFile 删除 : " + b);
//                            }
//                        }
//                    }, 2000); // 延迟时间为2000毫秒，即2秒
//                }
//            }
//        }).start();
//    }
//
//
//    public void releaseAccount() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String str = host + "/midas/account/releaseAccount";
//
//                String jsonBody = "{\"userName\":\"" + userName + "\"}";
//
//                System.out.println("allen=======jsonBody" + jsonBody);
//
//                // 调用 postJson 方法发送请
//                Headers headers = new Headers.Builder()
//                        .add("Content-Type", "application/json; charset=utf-8")
//                        .build();
//
//                // 调用 postJson 方法发送请求，并传入自定义 headers
//                String response = OkHttpUtil.postJson(OkHttpUtil.getOkHttpClient(), str, jsonBody, headers);
//
//                System.out.println("allen=======jsonBodyUser releaseAccount Name: " + userName);
//
//                JSONObject obj = null;
//                try {
//                    obj = new JSONObject(response);
//
//                    int code = obj.getInt("code");
//
//                    if (code == 1) {
//                        // 使用Handler切换回主线程
//                        new Handler(Looper.getMainLooper()).post(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                System.out.println("allen=======jsonBodyUser 释放成功 Name: " + userName);
//                            }
//                        });
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//                System.out.println("allen=======jsonBody Response: " + response);
//
//            }
//        }).start();
//
//    }
//
//
//    // 请求存储权限的方法
//    private void requestStoragePermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            // Android 11 (R) 及以上版本
//            if (!Environment.isExternalStorageManager()) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//                startActivityForResult(intent, REQUEST_CODE_MANAGE_STORAGE_PERMISSION);
//            } else {
//                Toast.makeText(this, "管理所有文件权限已授予", Toast.LENGTH_SHORT).show();
//            }
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            // Android 10 (Q) 及以上版本
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
//            } else {
//                Toast.makeText(this, "存储权限已授予", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            // Android 10 以下版本
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
//            } else {
//                Toast.makeText(this, "存储权限已授予", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    // 处理权限请求结果
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "存储权限已授予", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "存储权限被拒绝", Toast.LENGTH_SHORT).show();
//                // 如果权限被拒绝，可以在这里提醒用户去设置中手动开启权限
//                showDialogToManuallyEnablePermission();
//            }
//        }
//    }
//
//    // 处理管理所有文件权限请求结果
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_MANAGE_STORAGE_PERMISSION) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                if (Environment.isExternalStorageManager()) {
//                    Toast.makeText(this, "管理所有文件权限已授予", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "管理所有文件权限被拒绝", Toast.LENGTH_SHORT).show();
//                    // 如果权限被拒绝，可以在这里提醒用户去设置中手动开启权限
//                    showDialogToManuallyEnablePermission();
//                }
//            }
//        }
//    }
//
//    // 提示用户手动开启权限的对话框
//    private void showDialogToManuallyEnablePermission() {
//        new AlertDialog.Builder(this)
//                .setTitle("权限被拒绝")
//                .setMessage("应用需要存储权限，请在设置中手动开启。")
//                .setPositiveButton("去设置", (dialog, which) -> {
//                    // 跳转到应用的设置界面
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    Uri uri = Uri.fromParts("package", getPackageName(), null);
//                    intent.setData(uri);
//                    startActivity(intent);
//                })
//                .setNegativeButton("取消", null)
//                .show();
//    }
//
//
//    public void clearWebViewData(WebView webView) {
//        // 清除缓存
//        webView.clearCache(true);
//        // 清除历史记录
//        webView.clearHistory();
//        // 清除表单数据
//        webView.clearFormData();
//
//        // 清除所有的 Cookie
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.removeAllCookies(null);
//        cookieManager.flush();
//
//        // 清除 WebStorage 数据
//        WebStorage webStorage = WebStorage.getInstance();
//        webStorage.deleteAllData();
//
//        // 清除数据库和文件
//        Context context = webView.getContext();
//        context.deleteDatabase("webview.db");
//        context.deleteDatabase("webviewCache.db");
//        File webviewCacheDir = new File(context.getCacheDir(), "webviewCache");
//        if (webviewCacheDir.exists()) {
//            deleteDir(webviewCacheDir);
//        }
//        File webviewFilesDir = new File(context.getFilesDir(), "webview");
//        if (webviewFilesDir.exists()) {
//            deleteDir(webviewFilesDir);
//        }
//    }
//
//    // 删除目录及其子目录和文件的辅助方法
//    private boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        // 目录现在为空，可以删除
//        return dir.delete();
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        webView1 = findViewById(R.id.web1);
//
//        clearWebViewData(webView1);
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        cookieManager.removeAllCookies(null);
//
//        // 检查并请求存储权限
//        requestStoragePermissions();
//
//        // 调用 initData
//        initData(new InitDataCallback() {
//            @Override
//            public void onDataInitialized() {
//                setCookies(webView1, URL, cookie, cookieManager);
//                webView1.loadUrl(URL);
//                doWebview1(cookieManager, webView1, "webView1");
//            }
//        });
//    }
//
//
//    // 设置Cookie的方法
//    private void setCookies(WebView webView, String url, String cookieString, CookieManager cookieManager) {
//
//
//        webView.clearCache(true); // true表示清除磁盘上的缓存，false只清除RAM上的缓存
//
//        // 清除WebView的历史记录
//        webView.clearHistory();
//
//        webView.clearMatches();
//
//        // 清除所有WebView的数据（包括LocalStorage和SessionStorage）
//        webView.clearFormData();
//        webView.clearHistory();
//        webView.clearSslPreferences();
//
//
//        // Android 7.0（API级别24）及以上版本，还可以清除Web存储
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            WebStorage.getInstance().deleteAllData();
//        }
//
//
//        // 如果是API 21或以上版本，开启第三方cookie支持
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.setAcceptThirdPartyCookies(webView, true);
//        }
//
//
//        cookieManager.setCookie(url, "accumrecharge_activity_landing_pop=1");
//
//        cookieManager.setCookie(url, "cookie_control=1|1");
//
//        cookieManager.setCookie(url, "select_cookie=1");
//
//        cookieManager.setCookie(url, "country=us");
//
//        cookieManager.setCookie(url, "select_country=us");
//
//
//        // 分割并设置每个cookie
//        String[] cookies = cookieString.split("; ");
//        for (String cookie : cookies) {
//
//            if (cookie.startsWith("select_country=")) {
//                cookie = "select_country=us";
//            }
//
//            String cookie2 = cookie + "; path=/";
//
//            System.out.println("allen=======jsonBody cookie: " + cookie2);
//
//            cookieManager.setCookie(url, cookie2);
//        }
//
//
//        // 异步方式确保 cookies 被写入磁盘
//        cookieManager.flush();
//
//
//    }
//
//
//    private void setCookiesFromJson(String jsonCookies, String url, CookieManager cookieManager) {
//        try {
//            JSONObject cookies = new JSONObject(jsonCookies);
//
//            Iterator<String> keys = cookies.keys();
//            while (keys.hasNext()) {
//                String key = keys.next();
//                String value = cookies.getString(key);
//                String cookieString = key + "=" + value + "; path=/";
//
//                cookieManager.setCookie(url, cookieString); // 设置 Cookie
//            }
//
//            CookieManager.getInstance().flush(); // 确保写入操作完成
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    String numberStr = "";
//    String nickName = "";
//
//    String notice = "";
//
//    String midasUserName = "";
//
//    public void doWebview1(CookieManager cookieManager, WebView webView, String webViewName) {
//
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setSupportZoom(true);
//
//        webView.getSettings().setDefaultTextEncodingName("UTF-8");
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.setAcceptThirdPartyCookies(webView, true);
//        }
//
//
//        count = 0;
//
//
//        webView.addJavascriptInterface(new Object() {
//            @JavascriptInterface
//            public void printHtml(String html, String extraParam) {
//                Log.d("WebViewHTML", html);
//
//
//                String htmlContent = html;
//                String regex = "<div class=\"PopConfirmRedeem_item_mess__td5zk\"><p>\\s*(\\d+)\\s*UC\\s*</p></div>";
//
//                Pattern pattern = Pattern.compile(regex);
//                Matcher matcher = pattern.matcher(htmlContent);
//
//                if (matcher.find()) {
//                    numberStr = matcher.group(1).trim();
//                    System.out.println("WebViewHTML Match UC found:" + numberStr);
//
//                } else {
//                    System.out.println("WebViewHTML No match UC found.");
//                }
//
//
//                // 使用新的变量名 htmlContent_2
//                String htmlContent2 = html;
//                //String regex2 = "<div class=\"PopConfirmRedeem_label__Z8V4Q\">.*?(?:玩家暱稱|Nickname|Joueur Surnom|玩家昵称)\\s*[:：].*?</div>\\s*<div class=\"PopConfirmRedeem_val__3wKTQ\">\\s*(.*?)\\s*</div>";
//
//                String regex2 = "<div class=\"PopConfirmRedeem_label__olPLE\">.*?(?:玩家暱稱|暱稱|Nickname|Joueur Surnom|玩家昵称)\\s*[:：].*?</div>\\s*<div class=\"PopConfirmRedeem_val__XjIFC\">\\s*(.*?)\\s*</div>";
//
//                Pattern pattern2 = Pattern.compile(regex2);
//                Matcher matcher2 = pattern2.matcher(htmlContent2);  // 使用新的变量名 htmlContent_2
//
//                if (matcher2.find()) {
//                    nickName = matcher2.group(1).trim();
//                    System.out.println("WebViewHTML Match nickName found:" + nickName);
//                } else {
//                    System.out.println("WebViewHTML No match nickName found.");
//                }
//
//
//                //String regex3 = "<i class=\"i-midas:notice icon\"></i>\\s*<div>(.*?)</div>";
//                String regex3 = "<p class=\"PopStatusPrompt_sub_text__1LlC0 PopStatusPrompt_text_left__Ku5jM\">(.*?)</p>";
//
//                Pattern pattern3 = Pattern.compile(regex3);
//                Matcher matcher3 = pattern3.matcher(htmlContent);
//
//                int regex3MatchCount = 0;
//
//                while (matcher3.find()) {
//                    if (regex3MatchCount == 1) {
//                        notice = matcher3.group(1).trim(); // 提取并去除任何前后空白
//                        System.out.println("WebViewHTML match notice text:" + notice);
//                    }
//                    regex3MatchCount++;
//                }
//
//                if (regex3MatchCount < 1) {
//                    System.out.println("WebViewHTML No match  notice found.");
//                }
//
//                /*if (matcher3.find()) {
//                    notice = matcher3.group(1).trim(); // 提取并去除任何前后空白
//                    System.out.println("WebViewHTML match notice text:" + notice);
//                } else {
//                    System.out.println("WebViewHTML No match  notice found.");
//                }*/
//
//
//                String regex4 = "<i class=\"i-midas:user icon\"></i>\\s*<p class=\"MobileNav_tablet_show__spkaJ MobileNav_pc_show__57cEq\" title=\"([^\"]+)\">";
//
//                Pattern pattern4 = Pattern.compile(regex4);
//                Matcher matcher4 = pattern4.matcher(htmlContent);
//
//                if (matcher4.find()) {
//                    midasUserName = matcher4.group(1).trim(); // 提取并去除前后空白
//                    System.out.println("WebViewHTML match midsUserName text:" + midasUserName);
//                } else {
//                    System.out.println("WebViewHTML No midsUserName match found.");
//                }
//
//
//                if (isLogin && extraParam.equals("submit")) {
//
//
//                    if (html.contains("PurchaseContainer_text__OmIRF") || html.contains(">Redeem Successful<") || html.contains(">УСПЕШНО ВЫКУПЛЕНО<")) {
//                        System.out.println("WebViewHTML 兑换成功：" + notice);
//
//
//                        //File imageFile = new File(WebViewScreenshotActivity.takeScreenshot(webView));
//
//                        upload(1, notice, null);
//
//                        // doUplodSuccess(webView);
//
//                    } else if ("".equals(notice) || notice.contains("try again later")) {   //出现验证码
//
//                        System.out.println("WebViewHTML 出现验证码或者兑换被限制");
//
//                        File imageFile = WebViewScreenshotActivity.takeScreenshot(webView);
//                        upload(-4, notice, imageFile);
//
//                    } else if (notice.contains("redeemed by someone else")) { //被别人兑换Redemption code is redeemed by someone else
//
//                        System.out.println("WebViewHTML 被别人兑换");
//
//                        File imageFile = WebViewScreenshotActivity.takeScreenshot(webView);
//                        upload(-3, notice, imageFile);
//
//
//                    } else if (notice.contains("have already redeemed")) { //被自己兑换 You have already redeemed
//                        System.out.println("WebViewHTML 被自己兑换");
//                        File imageFile = WebViewScreenshotActivity.takeScreenshot(webView);
//                        upload(2, notice, imageFile);
//
//                    } else {//其他提示
//
//                        File imageFile = WebViewScreenshotActivity.takeScreenshot(webView);
//
//                        upload(-5, notice, imageFile);
//                        System.out.println("WebViewHTML 其他提示：" + notice);
//                    }
//
//
//                }
//
//
//            }
//        }, "AndroidFunction");
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//
//                Log.d("allen=======url", url);
//                if (url.contains(URL) && count == 0) {
//                    count++;
//
//
//                    String jsCode = "javascript:(async function() {"
//                            + "const jarea = '" + area + "';"
//                            + "const jranking = '" + ranking + "';"
//                            + "const jserver = '" + server + "';"
//                            + "const juid = '" + uid + "';"
//                            + "const jserialCode = '" + serialCode + "';"
//
//                            + "async function main() {"
//
//                            + "    console.log('" + webViewName + "');" //判断那个webView开始加载，如果这个开始加载，那么就关闭另外一个webview
//                            + "    var closeButton = await getFirstElementByClassName('wrappper_WrOIO visible_1ws1M');"
//                            + "    if (closeButton) {"
//                            + "        console.log('找到关闭按钮，正在尝试关闭...');"
//                            + "        closeButton.click();"
//                            + "    } else {"
//                            + "        console.log('未找到关闭按钮，继续执行后续操作...');"
//                            + "    }"
//
//                            + "    closeButton = await getFirstElementByClassName('PatFacePopWrapper_close-btn__erWAb');"
//                            + "    if (closeButton) {"
//                            + "        console.log('找到关闭按钮，正在尝试关闭...');"
//                            + "        closeButton.click();"
//                            + "    } else {"
//                            + "        console.log('未找到关闭按钮，继续执行后续操作...');"
//                            + "    }"
//
//                            + "    var userIcon = await getFirstElementByClassName('i-midas:exit icon');"
//                            + "    if (userIcon) {"
//                            + "        console.log('自动登录成功');"
//                            + "        await sleep(4000);"
//
//                            + "        var inputUserTab = await getFirstElementByClassName('UserTabBox_use_tab_box__otkPd UserTabBox_not_logined_box__m0w1t');"
//                            + "        if (!inputUserTab) {"
//                            + "            console.log('未找到输入玩家ID标签，尝试查找UserDataBox_switch_btn__q0ZYA');"
//                            + "            inputUserTab = await getFirstElementByClassName('UserDataBox_switch_btn__q0ZYA');"
//                            + "        }"
//
//                            + "        if (inputUserTab) {"
//                            + "            console.log('找到输入玩家ID标签或用户标签，正在尝试点击...');"
//                            + "            triggerOnClick(inputUserTab);"
//
//                            + "            await sleep(1500);"
//
//                            + "            var inputElement = document.querySelector('div.Input_input__s4ezt > input[type=\"text\"]');"
//                            + "            if (inputElement) {"
//                            + "                console.log('找到输入框，正在置空...');"
//                            + "                setValueAndTriggerEvents(inputElement, '');"
//                            + "            } else {"
//                            + "                console.log('未找到输入框。');"
//                            + "            }"
//
//                            + "            await sleep(2500);"
//                            + "            var selectAreaDiv = await getFirstElementByClassName('BindLoginPop_select_area__XJT-W');"
//                            + "            if (selectAreaDiv) {"
//                            + "                console.log('找到选择区域的div，正在尝试点击...');"
//                            + "                triggerOnClick(selectAreaDiv);"
//
//                            + "                await sleep(1500);"
//
//                            + "                var selectAreaDiv1 = await getFirstElementByClassName('SelectAreaPop_a1__jJ9aB');"
//                            + "                if (selectAreaDiv1) {"
//                            + "                    var targetOption1 = Array.from(selectAreaDiv1.querySelectorAll('div')).find(child => child.textContent === jarea);"
//                            + "                    if (targetOption1) {"
//                            + "                        console.log('找到指定的 jarea 分区选择，正在尝试点击...');"
//                            + "                        triggerOnClick(targetOption1);"
//
//                            + "                        await sleep(1500);"
//
//                            + "                        var selectAreaDiv2 = await getFirstElementByClassName('SelectAreaPop_a2__5PU-F');"
//                            + "                        if (selectAreaDiv2) {"
//                            + "                            var targetOption2 = Array.from(selectAreaDiv2.querySelectorAll('div')).find(child => child.textContent === jranking);"
//                            + "                            if (targetOption2) {"
//                            + "                                console.log('找到指定的 jranking 分区选择，正在尝试点击...');"
//                            + "                                triggerOnClick(targetOption2);"
//
//                            + "                                await sleep(1500);"
//
//                            + "                                var selectAreaDiv3 = await getFirstElementByClassName('SelectAreaPop_a3__KCN9H');"
//                            + "                                if (selectAreaDiv3) {"
//                            + "                                    var targetOption3 = Array.from(selectAreaDiv3.querySelectorAll('div')).find(child => child.textContent === jserver);"
//                            + "                                    if (targetOption3) {"
//                            + "                                        console.log('找到指定的 jserver 分区选择，正在尝试点击...');"
//                            + "                                        triggerOnClick(targetOption3);"
//                            + "                                    } else {"
//                            + "                                        console.log('未找到指定的 jserver 分区选项');"
//                            + "                                        return;"
//                            + "                                    }"
//                            + "                                } else {"
//                            + "                                    console.log('未找到SelectAreaPop_a3__KCN9H div');"
//                            + "                                    return;"
//                            + "                                }"
//                            + "                            } else {"
//                            + "                                console.log('未找到指定的 jranking 分区选项');"
//                            + "                                return;"
//                            + "                            }"
//                            + "                        } else {"
//                            + "                            console.log('未找到SelectAreaPop_a2__5PU-F div');"
//                            + "                            return;"
//                            + "                        }"
//                            + "                    } else {"
//                            + "                        console.log('未找到指定的 jarea 分区选项');"
//                            + "                        return;"
//                            + "                    }"
//                            + "                } else {"
//                            + "                    console.log('未找到SelectAreaPop_a1__jJ9aB div');"
//                            + "                    return;"
//                            + "                }"
//                            + "            } else {"
//                            + "                console.log('未找到选择区域的div');"
//                            + "                return;"
//                            + "            }"
//                            + "        } else {"
//                            + "            console.log('未找到输入玩家ID标签和用户标签。');"
//                            + "        }"
//
//                            + "        await sleep(5000);"
//                            + "        var inputElement = document.querySelector('div.Input_input__s4ezt > input[type=text]');"
//                            + "        if (inputElement) {"
//                            + "            console.log('找到输入框，正在尝试输入信息...');"
//                            + "            inputElement.value = '';"
//                            + "            inputElement.dispatchEvent(new Event('input', { bubbles: true }));"
//                            + "            inputElement.dispatchEvent(new Event('change', { bubbles: true }));"
//
//                            + "            await sleep(2000);"
//                            + "            inputElement.click();"
//                            + "            inputElement.focus();"
//
//                            + "            setValueAndTriggerEvents(inputElement, juid);"
//                            + "            inputElement.blur();"
//
//                            + "            await sleep(4000);"
//                            + "            var submitButton = await getFirstElementByClassName('BindLoginPop_btn_wrap__eiPwz');"
//                            + "            if (submitButton) {"
//                            + "                triggerOnClick(submitButton);"
//                            + "                console.log('用户ID按钮点击完成。');"
//
//                            + "                await sleep(4000);"
//                            + "                var errorTextElement = document.querySelector('.Input_error_text__Pd7xh');"
//                            + "                if (errorTextElement) {"
//                            + "                    console.log('用户ID无效');"
//                            + "                    return;"
//                            + "                } else {"
//                            + "                    await sleep(4000);"
//                            + "                    var anotherInputElement = document.querySelector('div.Input_input__s4ezt > input[type=text]');"
//                            + "                    anotherInputElement.value = '';"
//                            + "                    anotherInputElement.dispatchEvent(new Event('input', { bubbles: true }));"
//                            + "                    anotherInputElement.dispatchEvent(new Event('change', { bubbles: true }));"
//
//                            + "                    if (anotherInputElement) {"
//                            + "                        anotherInputElement.click();"
//                            + "                        anotherInputElement.focus();"
//                            + "                        console.log('找到兑换码输入框，正在尝试输入信息...');"
//                            + "                        setValueAndTriggerEvents(anotherInputElement, jserialCode);"
//                            + "                        anotherInputElement.blur();"
//
//                            + "                        await sleep(3000);"
//                            + "                        var submitButton2 = await getFirstElementByClassName('RedeemGiftBox_btn_box__yNyi');"
//                            + "                        submitButton2 = await getFirstElementByClassName('RedeemStepBox_btn_wrap__wEKY9');"
//                            + "                        if (submitButton2) {"
//                            + "                            triggerOnClick(submitButton2);"
//                            + "                            console.log('兑换按钮点击完成。');"
//
//                            + "                            await sleep(4000);"
//
//                            + "                            var errorTextElement2 = document.querySelector('.Input_error_text__Pd7xh');"
//                            + "                            if (errorTextElement2) {"
//                            + "                                console.log('兑换码无效');"
//                            + "                                return;"
//                            + "                            } else {"
//                            + "                                await sleep(4000);"
//                            + "                                getHtmlContent('perpare');"
//
//                            + "                                await sleep(4000);"
//                            + "                                var submitButton3 = await getFirstElementByClassName('PopConfirmRedeem_mess_wrap__m1PnF');"
//                            + "                                submitButton3 = await getFirstElementByClassName('PopConfirmRedeem_btn_wrap__3RKFf ');"
//                            + "                                if (submitButton3) {"
//                            + "                                    triggerOnClick(submitButton3);"
//                            + "                                    console.log('兑换按钮二次确认点击完成。');"
//
//                            + "                                    await sleep(12000);"
//                            + "                                    getHtmlContent('submit');"
//                            + "                                } else {"
//                            + "                                    console.log('未找到二次确认兑换按钮');"
//                            + "                                }"
//                            + "                            }"
//                            + "                        }"
//                            + "                    } else {"
//                            + "                        console.log('未找到兑换码输入框。');"
//                            + "                    }"
//                            + "                }"
//                            + "            } else {"
//                            + "                console.log('未找到用户信息提交按钮。');"
//                            + "            }"
//                            + "        } else {"
//                            + "            console.log('未找到输入框。');"
//                            + "        }"
//                            + "    } else {"
//                            + "        console.log('自动登录失败');"
//                            + "        await sleep(3000);"
//                            + "        getHtmlContent('perpare');"
//                            + "    }"
//                            + "}"
//                            + "main();"
//
//                            + "function triggerOnClick(element) {"
//                            + "    console.log('正在处理元素:', element.tagName + (element.className ? ' 类名: ' + element.className : ''));"
//                            + "    if (element.onclick) {"
//                            + "        console.log('触发 onclick 事件:', element.tagName);"
//                            + "        element.click();"
//                            + "    }"
//                            + "    var children = element.children;"
//                            + "    for (var i = 0; i < children.length; i++) {"
//                            + "        triggerOnClick(children[i]);"
//                            + "    }"
//                            + "}"
//
//                            + "function getFirstElementByClassName(className) {"
//                            + "    return new Promise((resolve) => {"
//                            + "        var elements = document.getElementsByClassName(className);"
//                            + "        if (elements.length > 0) {"
//                            + "            console.log('找到元素，类名为 ' + className + '，共 ' + elements.length + ' 个匹配项，第一个元素类型为 ' + elements[0].tagName + '。');"
//                            + "            resolve(elements[0]);"
//                            + "        } else {"
//                            + "            console.log('未找到任何匹配元素，类名为 ' + className + '，第一次尝试。');"
//                            + "            setTimeout(function() {"
//                            + "                elements = document.getElementsByClassName(className);"
//                            + "                if (elements.length > 0) {"
//                            + "                    console.log('再次尝试后找到元素，类名为 ' + className + '，共 ' + elements.length + ' 个匹配项，第一个元素类型为 ' + elements[0].tagName + '。');"
//                            + "                    resolve(elements[0]);"
//                            + "                } else {"
//                            + "                    console.log('再次尝试仍未找到任何匹配元素，类名为 ' + className + '。');"
//                            + "                    resolve(null);"
//                            + "                }"
//                            + "            }, 1500);"
//                            + "        }"
//                            + "    });"
//                            + "}"
//
//                            + "function getHtmlContent(extraParam) {"
//                            + "    var html = document.documentElement.outerHTML;"
//                            + "    console.log('当前页面HTML内容:', html);"
//                            + "    AndroidFunction.printHtml(html, extraParam);"
//                            + "}"
//
//                            + "function sleep(ms) {"
//                            + "    return new Promise(resolve => setTimeout(resolve, ms));"
//                            + "}"
//
//                            + "function setValueAndTriggerEvents(inputElement, value) {"
//                            + "    var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
//                            + "    nativeInputValueSetter.call(inputElement, value);"
//                            + "    inputElement.dispatchEvent(new Event('input', { bubbles: true }));"
//                            + "    inputElement.dispatchEvent(new Event('change', { bubbles: true }));"
//                            + "}"
//                            + "})()";
//
//
//                    view.evaluateJavascript(jsCode, new ValueCallback<String>() {
//                        @Override
//                        public void onReceiveValue(String s) {
//                            Log.d("JS", "Response: " + s);
//                        }
//                    });
//
//
//                    webView.setWebViewClient(new WebViewClient() {
//
//                        @Nullable
//                        @Override
//                        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//
//                            // 获取请求的URL
//                            Uri uri = request.getUrl();
//                            Log.d("allen==========WebView", "URL: " + uri.toString());
//
//                            // 获取请求的方法
//                            String method = request.getMethod();
//                            Log.d("allen==========WebView", "Method: " + method);
//
//                            // 获取请求头
//                            Map<String, String> headers = request.getRequestHeaders();
//                            Log.d("allen==========WebView", "Headers: " + headers.toString());
//
//                            // 打印请求体（如果可以获取的话）
//                            // 注意：通常你无法直接从WebResourceRequest中获取请求体
//
//                            return super.shouldInterceptRequest(view, request);
//                        }
//                    });
//
//
//                    webView.setWebChromeClient(new WebChromeClient() {
//                        @Override
//                        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                            String message = consoleMessage.message();
//                            ConstraintSet constraintSet = new ConstraintSet();
//                            constraintSet.clone(constraintLayout);
//
//                            Log.d("allen=======", message);
//                            if (message.equals("webView1")) {
//                                webView1.setVisibility(View.VISIBLE);
//                            }else if (message.contains("自动登录失败")) {
//
//                                if (retry) {
//                                    isLogin = false;
//                                    File imageFile = WebViewScreenshotActivity.takeScreenshot(webView);
//                                    upload(-7, "自动登录失败", imageFile);
//
//                                } else {
//
//
//                                    // 调用 initData
//                                    useAccount(new InitDataCallback() {
//                                        @Override
//                                        public void onDataInitialized() {
//
//
//                                            setCookies(webView1, URL, cookie, cookieManager);
//
//                                            webView1.loadUrl(URL);
//
//                                            doWebview1(cookieManager, webView1, "webView1");
//
//
//                                            setCookies(webView2, URL, cookie, cookieManager);
//
//                                            webView2.loadUrl(URL);
//
//                                            doWebview1(cookieManager, webView2, "webView2");
//
//
//                                        }
//                                    });
//
//                                    retry = true;
//
//                                }
//
//
//                            } else if (message.contains("自动登录成功")) {
//
//
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        String str = host + "/midas/account/uploadCK";
//
//                                        String newCookies = CookieManager.getInstance().getCookie(URL);
//
//                                        JSONObject jsonBody = new JSONObject();
//                                        try {
//
//                                            jsonBody.put("userName", userName);
//
//                                            jsonBody.put("cookie", newCookies);
//                                        } catch (JSONException e) {
//                                            throw new RuntimeException(e);
//                                        }
//
//
//                                        System.out.println("allen=======jsonBody" + jsonBody.toString());
//                                        // 调用 postJson 方法发送请
//
//                                        Headers headers = new Headers.Builder()
//                                                .add("Content-Type", "application/json; charset=utf-8")
//                                                .build();
//
//                                        // 调用 postJson 方法发送请求，并传入自定义 headers
//                                        String response = OkHttpUtil.postJson(OkHttpUtil.getOkHttpClient(), str, jsonBody.toString(), headers);
//
//                                        JSONObject obj = null;
//                                        try {
//                                            obj = new JSONObject(response);
//
//                                            int code = obj.getInt("code");
//
//                                            if (code == 1) {
//                                                // 使用Handler切换回主线程
//                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//
//                                                        Toast.makeText(MainActivity.this, "用户CK提交成功！", Toast.LENGTH_LONG).show();
//
//                                                    }
//                                                });
//                                            }
//
//                                        } catch (JSONException e) {
//                                            throw new RuntimeException(e);
//                                        }
//
//                                        System.out.println("allen=======jsonBody Response: " + response);
//
//                                    }
//                                }).start();
//
//
//                                isLogin = true;
//
//                            } else if (message.contains("用户ID无效")) {
//
//
//                                File imageFile = WebViewScreenshotActivity.takeScreenshot(webView);
//                                System.out.println("allen=======imageFile:" + imageFile.toString());
//
//                                upload(-1, "用户ID无效", imageFile);
//
//
//                                //todo
//                            } else if (message.contains("兑换码无效")) {
//
//                                File imageFile = WebViewScreenshotActivity.takeScreenshot(webView);
//                                System.out.println("allen=======imageFile:" + imageFile.toString());
//
//                                upload(-2, "兑换码无效", imageFile);
//
//                                //doUplodSuccess(webView);
//
//
//                                //todo
//                            }
//
//
//                            // 打印获取到的deeplink值
//                            Log.d("ddmessageddmessage", message);
//                            return super.onConsoleMessage(consoleMessage);
//                        }
//                    });
//
//
//                    // 获取 Cookie
//                    String cookies = CookieManager.getInstance().getCookie(url);
//                    saveCookiesToJson(cookies);
//                }
//            }
//        });
//
//    }
//    private void saveCookiesToJson(String cookies) {
//        try {
//            // 解析 cookies 字符串并转换成 JSON 对象
//            JSONObject jsonCookies = new JSONObject();
//            for (String cookie : cookies.split(";")) {
//                String[] keyValue = cookie.split("=");
//                if (keyValue.length > 1) {
//                    jsonCookies.put(keyValue[0], keyValue[1]);
//                }
//            }
//            // 保存 JSON 对象，这里只是打印出来，你可以按需要进行保存
//            System.out.println("allen====cookie:" + jsonCookies.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}