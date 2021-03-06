package com.lqcode.lucytv.network;

import android.util.Log;

import com.liqiong.lucy.http.MyRequest;
import com.lqcode.lucytv.Constants;

/**
 * Created by Administrator on 2016/9/7.
 */
public abstract class CCTVPlayerUrlRequest extends MyRequest {
    public CCTVPlayerUrlRequest(String tvName) {
        addParam("tvName", tvName);
        this.url = Constants.cctvPlayerUrl;
        Log.e("url", this.url);
        connect();
    }
}
