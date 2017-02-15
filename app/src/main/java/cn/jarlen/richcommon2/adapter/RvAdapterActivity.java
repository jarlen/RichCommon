package cn.jarlen.richcommon2.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.adapter.RvCommonAdapter;
import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon2.R;
import cn.jarlen.richcommon2.data.Bean;

/**
 * Created by jarlen on 2016/11/26.
 */
public class RvAdapterActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rv_adapter;
    }

    @Override
    protected void onBindView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        TestAdapter testAdapter = new TestAdapter(this);
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


    private class TestAdapter extends RvCommonAdapter<Bean> {

        public TestAdapter(Context context) {
            super(context);
        }


        @Override
        public int getItemViewType(int position) {
            if (listData.isEmpty()) {
                return super.getItemViewType(position);
            }
            return listData.get(position).getType();
        }

        @Override
        public void onBindView(RvViewHolder viewHolder, Bean item) {
            TextView name = viewHolder.getView(R.id.name);
            name.setText(item.getName());
        }

        @Override
        public int getLayoutResId(int viewType) {
            switch (viewType) {
                case 0:

                    return R.layout.layout_list_item;
                case 1:

                    return R.layout.layout_rv_item_one;
                case 2:

                    return R.layout.layout_rv_item_two;
                case 3:

                    return R.layout.layout_rv_item_three;
                case 4:

                    return R.layout.layout_rv_item_four;
                default:
                    return R.layout.layout_list_item;
            }
        }
    }
}
