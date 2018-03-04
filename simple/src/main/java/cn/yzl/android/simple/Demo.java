package cn.yzl.android.simple;

import com.orhanobut.logger.Logger;

/**
 * Created by YZL on 2018/3/4.
 */

public class Demo {
    public static void logger() {
        Logger.e("aaaa");
        Exception e = new Exception();
        Logger.e("1",e);

    }
}
