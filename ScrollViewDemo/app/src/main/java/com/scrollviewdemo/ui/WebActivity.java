package com.scrollviewdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scrollviewdemo.R;
import com.scrollviewdemo.base.BaseActivity;
import com.scrollviewdemo.impl.ActionBarClickListener;
import com.scrollviewdemo.weight.TranslucentActionBar;


public class WebActivity extends BaseActivity implements ActionBarClickListener {
    WebView webview;
    ProgressBar myProgressBar;
    private String title;
    private String url;
    private TranslucentActionBar actionbar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_web);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        actionbar.setData(title + "", R.mipmap.ic_left_light, null, 0, null, this);
        StatusBarAlpha(this, 112);
        webview = (WebView) findViewById(R.id.webview);
        webview.setVisibility(View.VISIBLE);
        myProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
        //支持javascript   表示不支持js，如果想让java和js交互或者本身希望js完成一定的功能请把false改为true。
        webview.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放(设置WebView支持viewpoart)
        webview.getSettings().setUseWideViewPort(true);
        //自适应屏幕(设置网页超过屏幕宽度时重新布局为屏幕宽度)
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.loadUrl(url);
//        webview.loadUrl("http://m.cnr.cn/news/20171103/t20171103_524010777.html");
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.GONE);
                    webview.setVisibility(View.VISIBLE);
                    dismissProgress();
                } else {
                    if (View.GONE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });

    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onLeftClick(View view) {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
