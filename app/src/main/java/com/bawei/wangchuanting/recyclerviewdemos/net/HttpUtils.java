package com.bawei.wangchuanting.recyclerviewdemos.net;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * 作者：${王传亭} on 2017/1/9 17:57
 */

public class HttpUtils {
    private static HttpUtils httpUtils;
    private final OkHttpClient okHttpClient;
    private final Gson gson;
    private Handler mHandler;

    private HttpUtils() {
        okHttpClient = new OkHttpClient();
        gson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
    }

    //单例模式.返回该类的实例
    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    //从网络请求数据的方法(GET)
    public <T> void loadDataFromNet(String url, final Class<T> clazz, final OnCallBack<T> callBack) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                final T result = gson.fromJson(json, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(result);
                    }
                });
            }
        });

    }

    public interface OnCallBack<T> {
        void onSuccess(T result);

        void onFail();
    }

}
