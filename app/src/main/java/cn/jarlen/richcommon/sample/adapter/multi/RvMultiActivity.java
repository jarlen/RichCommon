package cn.jarlen.richcommon.sample.adapter.multi;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.jarlen.richcommon.adapter.multiple.RvMultiAdapter;
import cn.jarlen.richcommon.adapter.multiple.RvMultiItemManager;
import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.sample.data.Bean;
import cn.jarlen.richcommon.ui.BaseActivity;

/**
 * DESCRIBE:
 * Created by hjl on 2017/1/12.
 */

public class RvMultiActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rv_adapter;
    }

    @Override
    protected void onBindView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        MultiTestAdapter testAdapter = new MultiTestAdapter(this);
        recyclerView.setAdapter(testAdapter);

        List<Bean> datas = new ArrayList<Bean>();

        for (int index = 0; index < 1000; index++) {
            Bean bean = new Bean();
            bean.setType(index % 2);
            bean.setName("test " + index);
            datas.add(bean);
        }

        testAdapter.addDataList(datas);
    }

    @Override
    protected void preBindView() {

    }


    private class MultiTestAdapter extends RvMultiAdapter<Bean> {

        public MultiTestAdapter(Context context) {
            super(context);
        }

        @Override
        protected void preMultiItemView(RvMultiItemManager itemManager) {
//            itemManager.addMultiItemView(new NormalMessageView(mContext));
//            itemManager.addMultiItemView(new MultiItemView0(mContext));
//            itemManager.addMultiItemView(new MultiItemView3(mContext));
//            itemManager.addMultiItemView(new MultiItemView4(mContext));
//            itemManager.addMultiItemView(new MultiItemView1(mContext));
//            itemManager.addMultiItemView(new MultiItemView2(mContext));
            itemManager.addMultiItemView(new MessageTextView(mContext));
        }
    }
}
