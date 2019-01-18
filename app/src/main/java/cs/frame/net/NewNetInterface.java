package cs.frame.net;

import android.content.Context;

import java.util.Map;

/**
 * Created by leisure on 2017/2/10.
 */

public interface NewNetInterface {
    //String 类型
    void startRequest(Context context, String url, Map map, final Boolean isShowLoading, NewCallBack<String> callBack);

    //正常请求，回调判断0000状态
    <T> void startRequest(Context context, Boolean isShowLoading, String url, Map map, Class<T> tClass, NewCallBack<T> callBack);


    //正常请求，回调不判断0000状态
    <T> void startRequestNoSuccess(Context context, Boolean isShowLoading, String url, Map<String, String> map, Class<T> tClass, NewCallBack<T> callBack);

    //正常请求，回调判断0000状态
    <T> void startGetRequest(Context context, Boolean isShowLoading, String url, Map map, Class<T> tClass, NewCallBack<T> callBack);



}
