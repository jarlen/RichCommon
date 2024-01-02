package cn.jarlen.richcommon.sample;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.sample.adapter.TestAdapter;
import cn.jarlen.richcommon.sample.adapter.ViewHolderActivity;
import cn.jarlen.richcommon.sample.data.TestItem;
import cn.jarlen.richcommon.sample.mvp.view.AddActivity;
import cn.jarlen.richcommon.sample.toolbar.ToolBarActivity;
import cn.jarlen.richcommon.sample.watermark.WaterMarkActivity;
import cn.jarlen.richcommon.util.StatusBarUtil;
import cn.jarlen.richcommon.widget.toolbar.CommonToolBar;

public class MainActivity extends Activity {

    private RecyclerView mListView;
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommonToolBar commonToolBar = findViewById(R.id.toolbar);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, commonToolBar);
        StatusBarUtil.darkMode(this);

        mListView = (RecyclerView) findViewById(R.id.rv_list_view);
        testAdapter = new TestAdapter(this);

        List<TestItem> itemList = new ArrayList<>();
        itemList.add(new TestItem("ListViewInScrollView", ScrollViewActivity.class));
        itemList.add(new TestItem("WaterMarkView", WaterMarkActivity.class));
        itemList.add(new TestItem("PhotoView", PreviewActivity.class));
        itemList.add(new TestItem("MVP模式", AddActivity.class));
        itemList.add(new TestItem("通用ViewHolder", ViewHolderActivity.class));
        itemList.add(new TestItem("ToolBar", ToolBarActivity.class));
        testAdapter.addDataList(itemList);

        mListView.setLayoutManager(new GridLayoutManager(this, 2));
        mListView.setAdapter(testAdapter);
    }

}
