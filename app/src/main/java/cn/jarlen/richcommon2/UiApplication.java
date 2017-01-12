package cn.jarlen.richcommon2;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * DESCRIBE:
 * Created by hjl on 2017/1/12.
 */

public class UiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
