package cs.frame;


import cs.frame.net.StaticValues;

/**
 * Created by Server on 2017/3/14.
 * 常量类
 */

public class Constant {

    //接口回调 成功
    public final static String KEY_SUCCESS = "0000";
    public static class BaseReceiverAction {

        public static final String ACTION_PREFIX = StaticValues.ACTION_BASE_PREFIX;
        /**
         * token过期
         */
        public static final String ACTION_TOKEN_EXPIRE = ACTION_PREFIX + "token.expire";
    }

}
