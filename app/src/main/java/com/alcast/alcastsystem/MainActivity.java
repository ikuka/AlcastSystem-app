
package com.alcast.alcastsystem;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends Activity {
    private WebView webView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();

        String url = "http://192.168.1.254:8812/Login.aspx";
        this.webView = (WebView) this.findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);

        // 如果不设置这个，JS代码中的按钮会显示，但是按下去却不弹出对话框
        // Sets the chrome handler. This is an implementation of WebChromeClient
        // for use in handling JavaScript dialogs, favicons, titles, and the
        // progress. This will replace the current handler.
        webView.setWebChromeClient(new MyWebChromeClient());

        webView.addJavascriptInterface(new JsObject(), "jsObject");
        this.webView.loadUrl(url);
        this.webView.setWebViewClient(new WebViewClientDemo());

    }


    private class WebViewClientDemo extends WebViewClient {
        @Override
        // 在WebView中不在默认浏览器下显示页面
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public class JsObject {
        @JavascriptInterface
        public String ClearCookie() {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.removeSessionCookie();
            cookieManager.removeAllCookie();
            cookieSyncManager.sync();

            return "true";
        }
    }
}
