package cn.jarlen.richcommon2;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.adapter.CommonAdapter;
import cn.jarlen.richcommon.adapter.ViewHolder;
import cn.jarlen.richcommon.util.ToastUtils;
import cn.jarlen.richcommon.view.ListViewInScrollView;

public class ScrollViewActivity extends Activity {

    private ListViewInScrollView listView;

    private CommonAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        listView = (ListViewInScrollView) findViewById(R.id.listview_in_scroll_view);
        adapter = new CommonAdapter<String>(this) {
            @Override
            public void onBindView(ViewHolder viewHolder, String item) {
                TextView tv = viewHolder.getView(R.id.name);
                tv.setText(item);
            }

            @Override
            public int getLayoutResId() {
                return R.layout.layout_list_item;
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String warnContent = getResources().getString(R.string.warning_click, adapter.getItem(position));

                ToastUtils.makeToast(ScrollViewActivity.this).setText(warnContent).show();
            }
        });
        initData();
    }


    private void initData() {

        List<String> datas = new ArrayList<>();

        for (int index = 0; index < 50; index++) {
            datas.add("测试 : " + index);
        }

        adapter.addDataList(datas);
    }

}
