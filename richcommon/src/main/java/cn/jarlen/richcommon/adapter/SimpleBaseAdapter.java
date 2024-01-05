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
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIBE: reusable BaseAdapter
 * Created by jarlen on 2016/6/22.
 */
public abstract class SimpleBaseAdapter<D> extends BaseAdapter {

    public Context context = null;
    protected LayoutInflater inflater = null;
    protected List<D> listData = new ArrayList<D>();

    public SimpleBaseAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (listData == null || listData.isEmpty()) {
            return 0;
        }
        return listData.size();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public Object getItem(int arg0) {
        return listData.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
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
}
