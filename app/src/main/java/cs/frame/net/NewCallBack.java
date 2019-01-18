package cs.frame.net;

/**
 * Created by leisure on 2017/2/10.
 */

public interface NewCallBack<T> {
    //请求成功后的回调 形参就是我们最后解析好的数据
    // ,因为不知道具体的类型,所以我们用T泛型代替
    void onSuccess(T response);
    //失败的回调,形参中我们填写的是异常对象
    void onError(Throwable e);

}
