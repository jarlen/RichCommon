/*
 * Copyright (C) 2016 jarlen
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.jarlen.richcommon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * For normal adapter
 * Created by jarlen on 2016/11/9.
 */
public abstract class CommonAdapter<D> extends BaseAdapter {

    public Context mContext = null;
    protected List<D> listData = new ArrayList<D>();

    public CommonAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (listData == null) {
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

    public void addDataList(List<D> mList) {
        if (listData != null) {
            listData.addAll(mList);
        }
        this.notifyDataSetChanged();
    }

    public void addData(D data) {
        if (listData != null) {
            listData.add(data);
        }
        this.notifyDataSetChanged();
    }

    public void clearDataList() {
        if (listData != null) {
            listData.clear();
        }
        this.notifyDataSetChanged();
    }

    public void removeData(int position) {
        if (listData != null) {
            listData.remove(position);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, parent, convertView, position, getLayoutResId());
        onBindView(viewHolder, listData.get(position));
        return viewHolder.getConvertView();
    }

    public abstract void onBindView(ViewHolder viewHolder, D item);

    public abstract int getLayoutResId();
}
