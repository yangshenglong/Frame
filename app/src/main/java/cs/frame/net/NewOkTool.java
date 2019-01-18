package cs.frame.net;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;


import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cs.frame.Constant;
import cs.frame.MyApp;
import cs.frame.util.LogUtil;
import cs.frame.util.SharedPreferencesUtils;
import cs.frame.util.Utils;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import cs.cs.com.frame.R;


/**
 * Created by leisure on 2017/2/10.
 */

public class NewOkTool implements NewNetInterface {
    private boolean isSub = true;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Gson mGson;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private String token = "token";

    public NewOkTool() {
        //初始化Gson对象
        mGson = new Gson();
        //初始化对象
        mOkHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(Environment.getExternalStorageDirectory(), 10 * 1024 * 1024))
                .build();
    }

    //传入个网址即可
    @Override
    public void startRequest(Context context, String url, Map map, final Boolean isShowLoading, final NewCallBack<String> callBack) {

        SharedPreferencesUtils.getSon(MyApp.getmContext());
        final String jsonStr = mGson.toJson(map);
        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .addHeader("Content-Type", StaticValues.CONTENT)
                .addHeader(token, SharedPreferencesUtils.getToken(MyApp.getmContext()))
                .build();
        if(isShowLoading){
            Utils.ShowProgressDialog(context);
        }

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                        if(isShowLoading){
                            Utils.DismissProgressDialog();
                        }
                        Utils.showShortToast(MyApp.getmContext().getResources().getString(R.string.net_error));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(str);
                        if(isShowLoading){
                            Utils.DismissProgressDialog();
                        }
                    }
                });
            }
        });
    }


    /**
     * 调用接口，有错误码判断
     */

    @Override
    public <T> void startRequest(Context context, final Boolean isShowLoading, final String url, Map map, final Class<T> tClass, final NewCallBack<T> callBack) {
        isSub =  SharedPreferencesUtils.getSon(MyApp.getmContext());
        if (isSub) {
            token = "token";
        } else {
            token = "subToken";
        }
        final String jsonStr = mGson.toJson(map);
        //接口回调错误信息

        LogUtil.d("*********"+url+"**********"+jsonStr);
        LogUtil.d("*********"+url+"**********"+SharedPreferencesUtils.getToken(MyApp.getmContext()));

        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .addHeader("Content-Type", StaticValues.CONTENT)
                .addHeader(token, SharedPreferencesUtils.getToken(MyApp.getmContext()))
                .build();
        if(isShowLoading){
            Utils.ShowProgressDialog(context);
        }

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(isShowLoading){
                            Utils.DismissProgressDialog();
                        }
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                LogUtil.d("*********"+url+"**********"+str);

                final T result;

                String rstError="";
                //接口回调错误码
                String rstCode="";
                if(isShowLoading){
                    Utils.DismissProgressDialog();
                }
                try {
                    JSONObject jsonObject=new JSONObject(str);

                    rstCode=jsonObject.getString("retCode");
                    rstError=jsonObject.getString("retMsg");
                    if(Constant.KEY_SUCCESS.equals(rstCode)){
                        result = mGson.fromJson(str, tClass);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(result);
                            }
                        });
                    }else{
                        if(TextUtils.isEmpty(rstError)){
                            Utils.showShortToast(MyApp.getmContext().getResources()
                                    .getString(R.string.net_error));
                        }else{
                            Utils.showShortToast(rstError);
                        }
                    }
                } catch (final Throwable e) {
                    final String finalRstError = rstError;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(e);
                            if(TextUtils.isEmpty(finalRstError)){
                                Utils.showShortToast(MyApp.getmContext().getResources()
                                        .getString(R.string.net_error));
                            }else{
                                Utils.showShortToast(finalRstError);
                            }
                        }
                    });
                }
            }
        });
    }


    /**
     * 调用接口，无返回状态判断
     */
    @Override
    public <T> void startRequestNoSuccess(Context context, final Boolean isShowLoading
            , final String url, Map<String, String> map, final Class<T> tClass
            , final NewCallBack<T> callBack) {
        isSub =  SharedPreferencesUtils.getSon(MyApp.getmContext());
        if (isSub) {
            token = "token";
        } else {
            token = "subToken";
        }
        final String jsonStr = mGson.toJson(map);

        LogUtil.d("*********"+url+"**********"+jsonStr);

        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder().url(url)
                .post(requestBody)
                .addHeader("Content-Type", StaticValues.CONTENT)
                .addHeader(token, SharedPreferencesUtils.getToken(MyApp.getmContext()))
                .build();
        if(isShowLoading){
            Utils.ShowProgressDialog(context);
        }

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(isShowLoading){
                            Utils.DismissProgressDialog();
                        }
                        callBack.onError(e);
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                LogUtil.d("*********"+url+"**********"+str);

                final T result;

                if(isShowLoading){
                    Utils.DismissProgressDialog();
                }
                try {
                    result = mGson.fromJson(str, tClass);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(result);
                        }
                    });
                } catch (final Throwable e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(e);
                            Utils.showShortToast(MyApp.getmContext().getResources()
                                    .getString(R.string.net_error));
                        }
                    });
                }
            }
        });
    }

    /**
     * get 请求 有返回值判断
     * @param context
     * @param isShowLoading
     * @param url
     * @param map
     * @param tClass
     * @param callBack
     * @param <T>
     */
    @Override
    public <T> void startGetRequest(Context context, final Boolean isShowLoading
            , final String url, Map map, final Class<T> tClass, final NewCallBack<T> callBack) {
        isSub =  SharedPreferencesUtils.getSon(MyApp.getmContext());
        if (isSub) {
            token = "token";
        } else {
            token = "subToken";
        }
        final String jsonStr = mGson.toJson(map);
        //接口回调错误信息

        LogUtil.d("*********"+url+"**********"+jsonStr);
        LogUtil.d("*********"+url+"**********"+SharedPreferencesUtils.getToken(MyApp.getmContext()));

        RequestBody requestBody = RequestBody.create(JSON, jsonStr);
        Request request = new Request.Builder().url(url)
                .get()
                .addHeader("Content-Type", StaticValues.CONTENT)
                .addHeader(token, SharedPreferencesUtils.getToken(MyApp.getmContext()))
                .build();
        if(isShowLoading){
            Utils.ShowProgressDialog(context);
        }

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(isShowLoading){
                            Utils.DismissProgressDialog();
                        }
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                LogUtil.d("*********"+url+"**********"+str);

                final T result;

                String rstError="";
                //接口回调错误码
                String rstCode="";
                if(isShowLoading){
                    Utils.DismissProgressDialog();
                }
                try {
                    JSONObject jsonObject=new JSONObject(str);

                    rstCode=jsonObject.getString("retCode");
                    rstError=jsonObject.getString("retMsg");
                    if(Constant.KEY_SUCCESS.equals(rstCode)){
                        result = mGson.fromJson(str, tClass);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(result);
                            }
                        });
                    }else{
                        if(TextUtils.isEmpty(rstError)){
                            Utils.showShortToast(MyApp.getmContext().getResources()
                                    .getString(R.string.net_error));
                        }else{
                            Utils.showShortToast(rstError);
                        }
                    }
                } catch (final Throwable e) {
                    final String finalRstError = rstError;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onError(e);
                            if(TextUtils.isEmpty(finalRstError)){
                                Utils.showShortToast(MyApp.getmContext().getResources()
                                        .getString(R.string.net_error));
                            }else{
                                Utils.showShortToast(finalRstError);
                            }
                        }
                    });
                }
            }
        });
    }

}
