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
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * For RecyclerView.Adapter
 * Created by jarlen on 2016/11/9.
 */
public abstract class RvCommonAdapter<D> extends RecyclerView.Adapter<RvViewHolder> {

	public Context mContext = null;
	protected List<D> listData = new ArrayList<D>();

	public RvCommonAdapter(Context context) {
		this.mContext = context;
	}

	@Override
	public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RvViewHolder viewHolder = RvViewHolder.getViewHolder(mContext, parent, getLayoutResId(viewType));
		return viewHolder;
	}

	public List<D> getListData() {
		return listData;
	}

	@Override
	public void onBindViewHolder(RvViewHolder viewHolder, int position) {
		D data = null;
		int dataPosition = getDataPosition(position);
		if (dataPosition >= 0 && dataPosition < listData.size()) {
			data = listData.get(dataPosition);
		}

		onBindView(viewHolder, data, position);
	}

	/**
	 * 获取数据的真实索引,可由子类决定是否重新计算
	 *
	 * @param position
	 * @return
	 */
	public int getDataPosition(int position) {
		return position;
	}

	public Context getContext() {
		return mContext;
	}

	@Override
	public int getItemCount() {
		if (listData == null) {
			return 0;
		}
		return listData.size();
	}

	public abstract void onBindView(RvViewHolder viewHolder, D item, int position);

	public abstract int getLayoutResId(int viewType);

	public void addDataList(List<D> mList) {
		if (mList == null) {
			return;
		}
		if (listData != null) {
			listData.addAll(mList);
		}
		this.notifyDataSetChanged();
	}

	public void addDataList(int index, List<D> mList) {
		if (mList == null || index > listData.size() || index < 0) {
			return;
		}
		if (index + 1 > listData.size()) {
			listData.addAll(mList);
		} else {
			listData.addAll(index, mList);

		}
		this.notifyItemRangeChanged(index, mList.size());
	}

	public void addData(D data) {
		if (data == null) {
			return;
		}
		if (listData != null) {
			listData.add(data);
			notifyItemInserted(listData.indexOf(data));
		}
	}

	public void addData(int index, D data) {
		if (data == null || index > listData.size() || index < 0) {
			return;
		}
		listData.add(index, data);
		notifyItemInserted(listData.indexOf(data));
	}

	public void setDataList(List<D> datas) {
		if (datas == null || datas.isEmpty()) {
			listData.clear();
			this.notifyDataSetChanged();
			return;
		}
		listData = datas;
		this.notifyDataSetChanged();
	}

	public void clearDataList() {
		if (listData != null) {
			listData.clear();
		}
		this.notifyDataSetChanged();
	}

	public boolean isEmpty() {
		return listData == null || listData.isEmpty();
	}

	public void removeData(int position) {
		if (listData != null) {
			listData.remove(position);
		}
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, listData.size() - position);
	}

	public void removeDataList(List<D> dataList) {
		if (dataList == null) {
			return;
		}
		if (listData != null) {
			listData.removeAll(dataList);
		}
		this.notifyDataSetChanged();
	}

	public void removeData(D data) {
		if (data == null) {
			return;
		}
		if (listData != null) {
			listData.remove(data);
		}
		this.notifyDataSetChanged();
	}
}
