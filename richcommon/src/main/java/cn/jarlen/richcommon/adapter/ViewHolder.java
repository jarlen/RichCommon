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
package cn.jarlen.richcommon.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * DESCRIBE: reusable ViewHolder
 * <br>
 * <br>ViewHolder viewHolder = ViewHolder.getViewHolder(context,parent,convertView, position, layoutId);
 * <br>View view = viewHolder.getView(resId);
 * <br>return viewHolder.getConvertView();
 * <br>
 * Created by jarlen on 2016/6/22.
 */
public class ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;
    private int mPosition;

    private ViewHolder(Context context, int position, int layoutId,
                       ViewGroup parent) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId,
                parent, false);
        mConvertView.setTag(this);
    }

    public static ViewHolder getViewHolder(Context context, ViewGroup parent,
                                           View convertView, int position, int layoutId) {

        if (convertView == null) {
            return new ViewHolder(context, position, layoutId, parent);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
            return viewHolder;
        }
    }

    @SuppressWarnings({"unchecked", "hiding"})
    public <T extends View> T getView(int resId) {
        View view = mViews.get(resId);

        if (view == null) {
            view = mConvertView.findViewById(resId);
            mViews.put(resId, view);
        }

        return (T) view;
    }

    public int getPosition() {
        return mPosition;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
