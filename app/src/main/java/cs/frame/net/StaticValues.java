package cs.frame.net;

import android.os.Environment;

/**
 * Created by zxc on 2017/2/9.
 */

public class StaticValues {
    //后台本地环境
//    public final static String PRE = "http://192.168.1.141:8080";
    //正式环境前缀
    public final static String PRE = "http://carsok.utonw.com/carsokApi";
    //Header - Content
    public final static String CONTENT = "application/json";

    public final static String VERSION = PRE + "VersionCodeApi/versionCodeForAndroid.htm";

    /**
     * 本地存储的根路径
     */
    public static final String EXT_STORAGE_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 本地存储根目录名
     */
    public static final String CACHE_ROOT_NAME = "ZheDing";

    /**
     * 本地存储缓存根目录名
     */
    public static final String CACHE_ROOT_CACHE_NAME = "cache";

    /**
     * apk安装包名称
     */
    public static final String APK_NAME = "ZD.apk";

    /**
     * 本地存储图片根目录名
     */
    public static final String CACHE_PIC_ROOT_NAME = "浙鼎";
    public static final String ACTION_BASE_PREFIX = "App.action.";


}