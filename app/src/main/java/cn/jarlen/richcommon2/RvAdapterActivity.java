package cn.jarlen.richcommon2;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.adapter.RvCommonAdapter;
import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon.utils.ToastUtil;

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

        for (int index = 0; index < 15; index++) {
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

    private class TestAdapter extends RvCommonAdapter<Bean> {

        public TestAdapter(Context context) {
            super(context);
        }

        @Override
        public void onBindView(RvViewHolder viewHolder, final Bean item) {
            TextView tv = viewHolder.getView(R.id.name);
            tv.setText(item.getName());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.makeToast(mContext).setText(item.getName()).show();
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return listData.get(position).getType();
        }

        @Override
        public int getLayoutResId(int viewType) {

            switch (viewType) {
                case 0:
                    return R.layout.layout_rv_item_one;
                case 1:
                    return R.layout.layout_rv_item_two;
                case 2:
                    return R.layout.layout_rv_item_three;
                case 3:
                    return R.layout.layout_rv_item_four;

                default:

                    return R.layout.layout_rv_item_one;

            }
        }
    }

    private class Bean {
        int type;

        String name;

        public void setName(String name) {
            this.name = name;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }


}
