package cn.jarlen.richcommon2.adapter;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.adapter.CommonAdapter;
import cn.jarlen.richcommon.adapter.ViewHolder;
import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon2.R;

public class AdapterActivity extends BaseActivity {

    private ListView mListView;

    private CommonAdapter commonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adapter;
    }

    @Override
    protected void onBindView() {
        mListView = (ListView) findViewById(R.id.listview);
        commonAdapter = new CommonAdapter<String>(this) {

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
        mListView.setAdapter(commonAdapter);

        List<String> data = new ArrayList<>();
        for(int index = 0;index < 1000;index++){
            data.add("测试: "+index);
        }
        commonAdapter.addDataList(data);
    }

    @Override
    protected void preBindView() {

    }
}
