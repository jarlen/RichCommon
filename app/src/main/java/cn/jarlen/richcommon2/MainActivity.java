package cn.jarlen.richcommon2;

import android.app.Activity;
import android.os.Bundle;

import cn.jarlen.richcommon.util.TimeUtils;
import cn.jarlen.richcommon.util.ToastUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String today = TimeUtils.getToday2();

        ToastUtils.makeToast(this).setGravity(ToastUtils.CENTER).setImage(R.drawable.ic_launcher).setText("测试").show();

    }
}
