package cn.jarlen.richcommon2.adapter.normal;

import android.content.Context;
import android.widget.ListView;

import cn.jarlen.richcommon.adapter.multiple.IMultiManager;
import cn.jarlen.richcommon.adapter.multiple.MultiAdapter;
import cn.jarlen.richcommon2.data.Bean;

/**
 * Created by hjl on 2017/2/22.
 */

public class NormalMulAdapter extends MultiAdapter<Bean> {

    public NormalMulAdapter(Context context, ListView listView) {
        super(context, listView);
    }

    @Override
    protected void registerItemViewType(IMultiManager multiManager) {
        multiManager.registerMultiView(new MultiItemOne(mContext));
        multiManager.registerMultiView(new MultiItemTwo(mContext));
    }
}
