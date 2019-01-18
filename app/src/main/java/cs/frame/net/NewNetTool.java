package cs.frame.net;

import android.content.Context;

import java.util.Map;

/**
 * Created by leisure on 2017/2/10.
 */
public class NewNetTool implements NewNetInterface {
    private static NewNetTool sNetTool;
    private NewNetInterface mInterface;
    public static NewNetTool getInstance() {
        //双重校验锁单例模式
        if (sNetTool == null){
            synchronized (NewNetTool.class){
                if (sNetTool == null){
                    sNetTool = new NewNetTool();
                }
            }
        }
        return sNetTool;
    }

    private NewNetTool() {
        mInterface = new NewOkTool();
    }

    //对外提供的方法
    //有token , content_type 和 body

    //String 类型


    @Override
    public void startRequest(Context context, String url, Map map, Boolean isShowLoading, NewCallBack<String> callBack) {
        mInterface.startRequest(context,url,map,isShowLoading,callBack);
    }

    @Override
    public <T> void startRequest(Context context, Boolean isShowLoading, String url, Map map, Class<T> tClass, NewCallBack<T> callBack) {
        mInterface.startRequest(context,isShowLoading,url,map,tClass,callBack);
    }

    @Override
    public <T> void startRequestNoSuccess(Context context, Boolean isShowLoading, String url, Map<String, String> map, Class<T> tClass, NewCallBack<T> callBack) {
        mInterface.startRequestNoSuccess(context,isShowLoading,url,map,tClass,callBack);
    }

    @Override
    public <T> void startGetRequest(Context context, Boolean isShowLoading, String url, Map map, Class<T> tClass, NewCallBack<T> callBack) {
        mInterface.startGetRequest(context,isShowLoading,url,map,tClass,callBack);

    }


}
