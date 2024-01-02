package cn.jarlen.richcommon.sample.toolbar;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.util.StatusBarUtil;
import cn.jarlen.richcommon.widget.CommonToast;
import cn.jarlen.richcommon.widget.toolbar.CommonToolBar;
import cn.jarlen.richcommon.widget.toolbar.OnToolBarButtonClickListener;
import cn.jarlen.richcommon.widget.toolbar.ToolBarButtonType;

public class ToolBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
        CommonToolBar commonToolBar = findViewById(R.id.toolbar1);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, commonToolBar);
        StatusBarUtil.darkMode(this);

        ((CommonToolBar) findViewById(R.id.toolbar5)).setBarButtonClickListener(new OnToolBarButtonClickListener() {
            @Override
            public void onClick(View v, ToolBarButtonType type) {
                CommonToast.showBottomToast(ToolBarActivity.this, "" + type);
            }
        });
    }
}