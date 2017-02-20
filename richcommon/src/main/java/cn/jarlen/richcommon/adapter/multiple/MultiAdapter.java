package cn.jarlen.richcommon.adapter.multiple;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIBE:
 * Created by hjl on 2017/2/20.
 */

public class MultiAdapter<D> extends BaseAdapter {

    public Context mContext = null;

    protected List<D> listData = new ArrayList<D>();

    @Override
    public int getCount() {
        if(listData == null || listData.isEmpty()){
            return 0;
        }
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
