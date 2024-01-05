package cn.jarlen.richcommon.sample.adapter.multi;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.sample.adapter.normal.NormalMulAdapter;
import cn.jarlen.richcommon.sample.data.Bean;
import cn.jarlen.richcommon.ui.BaseActivity;

/**
 * Created by hjl on 2017/2/22.
 */

public class MultiActivity extends BaseActivity {

    private ListView listView;

    private NormalMulAdapter normalMulAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_listview;
    }

    @Override
    protected void onBindView() {
        listView = (ListView) findViewById(R.id.listview);

        normalMulAdapter = new NormalMulAdapter(this, listView);

        listView.setAdapter(normalMulAdapter);

        List<Bean> datas = new ArrayList<Bean>();

        for (int index = 0; index < 1000; index++) {
            Bean bean = new Bean();
            bean.setType(index % 2);
            bean.setName("test " + index);
            datas.add(bean);
        }

        normalMulAdapter.addDatas(datas);
    }
}
