package cn.jarlen.richcommon2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.adapter.SimpleBaseAdapter;
import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon2.R;

/**
 * DESCRIBE:
 * Created by hjl on 2016/11/10.
 */

public class NormalAdapterActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adapter;
    }

    @Override
    protected void onBindView() {
        ListView listView = (ListView) findViewById(R.id.listview);
        NormalAdapter normalAdapter = new NormalAdapter(this);
        listView.setAdapter(normalAdapter);

        List<String> data = new ArrayList<>();
        for (int index = 0;index < 1000;index++){
            data.add("测试 : "+index);
        }

        normalAdapter.addDataList(data);
    }

    @Override
    protected void preBindView() {

    }

    private class NormalAdapter extends SimpleBaseAdapter<String>{

        public NormalAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.layout_list_item,parent, false);
                viewHolder =  new ViewHolder();
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tv = (TextView) convertView.findViewById(R.id.name);
            viewHolder.tv.setText(listData.get(position));

            return convertView;
        }

        private class ViewHolder{
            TextView tv;
        }
    }
}
