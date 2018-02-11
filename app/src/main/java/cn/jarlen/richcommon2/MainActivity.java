package cn.jarlen.richcommon2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon2.adapter.TestAdapter;
import cn.jarlen.richcommon2.data.TestItem;
import cn.jarlen.richcommon2.mvp.view.AddActivity;

public class MainActivity extends Activity {

    private RecyclerView mListView;

    private TestAdapter testAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (RecyclerView) findViewById(R.id.rv_list_view);

        testAdapter = new TestAdapter(this);

        mListView.setLayoutManager(new GridLayoutManager(this, 2));

        mListView.setAdapter(testAdapter);

        List<TestItem> itemList = new ArrayList<>();
        itemList.add(new TestItem("ListViewInScrollView", ScrollViewActivity.class));
        itemList.add(new TestItem("WaterMarkView", WaterMarkActivity.class));
        itemList.add(new TestItem("PhotoView", PreviewActivity.class));

        itemList.add(new TestItem("MVP模式", AddActivity.class));

        itemList.add(new TestItem("通用ViewHolder", ViewHolderActivity.class));

        testAdapter.addDataList(itemList);

    }

}
