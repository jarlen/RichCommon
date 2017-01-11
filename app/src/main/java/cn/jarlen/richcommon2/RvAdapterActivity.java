package cn.jarlen.richcommon2;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon2.data.Bean;

/**
 * Created by jarlen on 2016/11/26.
 */
public class RvAdapterActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rv_adapter;
    }

    @Override
    protected void onBindView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        testAdapter = new TestAdapter(this);
        recyclerView.setAdapter(testAdapter);

        List<Bean> datas = new ArrayList<Bean>();

        for (int index = 0; index < 1000; index++) {
            Bean bean = new Bean();
            bean.setType(index % 3);
            bean.setName("test " + index);

            datas.add(bean);
        }

        testAdapter.addDataList(datas);
    }

    @Override
    protected void preBindView() {

    }


    private TestAdapter testAdapter = null;

    private class TestAdapter extends RvCommonAdapter2<Bean> {

        public TestAdapter(Context context) {
            super(context);
        }
    }
}
