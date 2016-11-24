package cn.jarlen.richcommon2;

import android.app.Activity;
import android.os.Bundle;

import cn.jarlen.richcommon.utils.TimeUtil;
import cn.jarlen.richcommon.utils.ToastUtil;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String today = TimeUtil.getToday2();

        ToastUtil.makeToast(this).setGravity(ToastUtil.CENTER).setImage(R.drawable.ic_launcher).setText("测试").show();

    }
}
