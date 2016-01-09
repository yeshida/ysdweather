package com.ysd.ysdweather.util;

/**
 * Created by Administrator on 2016/1/6.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
