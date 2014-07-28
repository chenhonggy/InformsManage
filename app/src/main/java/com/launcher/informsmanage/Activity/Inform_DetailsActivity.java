package com.launcher.informsmanage.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.launcher.informsmanage.ApiManager.InformApiManager;
import com.launcher.informsmanage.Model.InformData;
import com.launcher.informsmanage.Utils.ConstansUtils;
import com.launcher.informsmanage.main.R;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.UnsupportedEncodingException;

import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-7-26.
 */
public class Inform_DetailsActivity extends MainActivity{
    private String id;
    private Handler handler;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inform_details);
        init();
        getAsyInformData(id);

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub

                switch(msg.what)
                {
                    case ConstansUtils.MSG_SUCCESS:
                        InformData informData = (InformData)msg.obj;
//                        StringBuilder data = new StringBuilder(informData.context);

                        setWebviewHtml(informData);
                        break;
                }
                super.handleMessage(msg);
            }

        };
    }
    private void init()
    {
        webView = (WebView)findViewById(R.id.inform_details_webview);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        id = getIntent().getExtras().getString("id");
    }

    private void setWebviewHtml(InformData informData){
        String data = informData.context;
            webView.loadDataWithBaseURL("", Unescape(data), "text/html", "UTF-8", "");
    }

    public String Unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();

}

    private void getAsyInformData(String id)
    {
        InformApiManager.getInformData(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<InformData>() {
                    @Override
                    public void call(InformData informData) {
                        // 获取一个Message对象，设置what为1
                        Message msg = Message.obtain();
                        msg.obj = informData;
                        msg.what = ConstansUtils.MSG_SUCCESS;
                        // 发送这个消息到消息队列中
                        handler.sendMessage(msg);
                    }
                });
    }
}
