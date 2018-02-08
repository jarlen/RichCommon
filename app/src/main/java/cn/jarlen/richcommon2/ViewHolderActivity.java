package cn.jarlen.richcommon2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.adapter.SimpleBaseAdapter;
import cn.jarlen.richcommon.adapter.ViewHolder;
import cn.jarlen.richcommon.ui.BaseActivity;

/**
 * Created by jarlen on 2018/2/8.
 */

public class ViewHolderActivity extends BaseActivity {

    private ListView listView;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_listview;
    }

    @Override
    protected void onBindView() {
        listView = (ListView) findViewById(R.id.listview);

        SimpleBaseAdapter adapter = new SimpleBaseAdapter<String>(this) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder viewHolder = ViewHolder.getViewHolder(getContext(), parent, convertView, position, R.layout.layout_list_item);

                TextView nameTv = viewHolder.getView(R.id.name);
                nameTv.setText(listData.get(position));
                return viewHolder.getConvertView();
            }
        };


        listView.setAdapter(adapter);

        List<String> data = new ArrayList<>();
        for (int index = 0; index < 1000; index++) {
            data.add("测试 : " + index);
        }

        adapter.addDataList(data);
    }
}
