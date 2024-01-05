/*
 *          Copyright (C) 2016 jarlen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cn.jarlen.richcommon.adapter.multiple;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Data adapter supporting multiple types of item
 *
 * Created by jarlen on 2016/9/4.
 */

public abstract class MultiAdapter<D> extends BaseAdapter {

    protected List<D> listData = new ArrayList<>();
    private IMultiManager multiManager = null;
    protected Context mContext;

    protected ListView mListView;

    public MultiAdapter(Context context) {
        this(context, null, null);
    }

    public MultiAdapter(Context context, ListView listView) {
        this(context, null, listView);
    }

    public MultiAdapter(Context context, List<D> datas) {
        this(context, datas, null);
    }

    public IMultiManager getMultiManager() {
        return multiManager;
    }

    public MultiAdapter(Context context, List<D> datas, ListView listView) {
        this.mContext = context;
        if (datas != null) {
            this.listData = datas;
        }
        this.mListView = listView;
        multiManager = new MultiManager(context);
        registerItemViewType(multiManager);
    }

    /**
     * Add multiple data
     * @param datas
     */
    public void addDatas(List<D> datas) {
        if (listData != null) {
            listData.addAll(datas);
        }
        notifyDataSetChanged();
    }

    /**
     * Add single data
     * @param data
     */
    public void addDatas(D data) {
        if (listData != null) {
            listData.add(data);
        }
        notifyDataSetChanged();
    }

    /**
     * Delete a single data by position
     * @param position
     */
    public void delete(int position) {
        if (listData != null) {
            listData.remove(position);
        }
        notifyDataSetChanged();
    }

    /**
     * Delete all the datas
     */
    public void clearAll() {
        if (listData != null) {
            listData.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (listData == null || listData.isEmpty()) {
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
    public int getItemViewType(int position) {
        return multiManager.getItemViewType(listData.get(position), position);
    }

    @Override
    public int getViewTypeCount() {
        return multiManager.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = multiManager.getView(listData.get(position), position, convertView, parent);
        return view;
    }

    /**
     * update single item by position
     * @param position
     */
    public void updateItem(int position) {
        if (mListView != null) {
            int visiblePosition = mListView.getFirstVisiblePosition();
            View view = mListView.getChildAt(position - visiblePosition);
            multiManager.getView(listData.get(position), position, view, mListView);
        } else {
            throw new NullPointerException(
                    "listview is null");
        }
    }

    /**
     * Register list item type
     * @param multiManager
     */
    protected abstract void registerItemViewType(IMultiManager multiManager);
}
