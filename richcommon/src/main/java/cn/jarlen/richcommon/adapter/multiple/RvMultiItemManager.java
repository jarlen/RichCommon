/*
 * Copyright (C) 2016 jarlen
 * fork form https://github.com/sockeqwe/AdapterDelegates
 * and modify
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
package cn.jarlen.richcommon.adapter.multiple;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import cn.jarlen.richcommon.adapter.RvViewHolder;

/**
 * This class is the element that ties {@link RecyclerView.Adapter} together with {@link
 * IRvMultiItemView}.
 * <p>
 * So you have to add / register your {@link IRvMultiItemView}s to this manager by calling {@link
 * #addMultiItemView(IRvMultiItemView)}
 * </p>
 * <p>
 * <p>
 * Next you have to add this AdapterMultiItemViewManager to the {@link RecyclerView.Adapter} by calling
 * corresponding methods:
 * <ul>
 * <li> {@link #getItemViewType(Object, int)}: Must be called from {@link
 * RecyclerView.Adapter#getItemViewType(int)}</li>
 * <li> {@link #onCreateViewHolder(ViewGroup, int)}: Must be called from {@link
 * RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}</li>
 * <li> {@link #onBindViewHolder(Object, int, RvViewHolder)}: Must be called from {@link
 * RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}</li>
 * </ul>
 * <p>
 * You can also set a fallback {@link IRvMultiItemView} by using {@link
 * #setFallbackMultiItemView(IRvMultiItemView)} that will be used if no {@link IRvMultiItemView} is
 * responsible to handle a certain view type. If no fallback is specified, an Exception will be
 * thrown if no {@link IRvMultiItemView} is responsible to handle a certain view type
 * </p>
 *
 * @param <T> The type of the data source of the adapter
 * @author jarlen
 */
public class RvMultiItemManager<T> {

    /**
     * This id is used internally to claim that the {@link}
     */
    static final int FALLBACK_MULTI_VIEW_TYPE = Integer.MAX_VALUE - 1;

    /**
     * Used internally for {@link #onBindViewHolder(Object, int, RvViewHolder)} as empty
     * payload parameter
     */
    private static final List<Object> PAYLOADS_EMPTY_LIST = Collections.emptyList();

    /**
     * Map for ViewType to multiItemView
     */
    protected SparseArrayCompat<IRvMultiItemView<T>> multiItemViews = new SparseArrayCompat();
    protected IRvMultiItemView<T> fallbackMultiItemView;

    /**
     * Adds an {@link IRvMultiItemView}.
     * <b>This method automatically assign internally the view type integer by using the next
     * unused</b>
     * <p>
     * Internally calls {@link #addMultiItemView(int, boolean, IRvMultiItemView)} with
     * allowReplacingMultiItemView = false as parameter.
     *
     * @param multiItemView the multiItemView to add
     * @return self
     * @throws NullPointerException if passed multiItemView is null
     * @see #addMultiItemView(int, IRvMultiItemView)
     * @see #addMultiItemView(int, boolean, IRvMultiItemView)
     */
    public RvMultiItemManager<T> addMultiItemView(@NonNull IRvMultiItemView<T> multiItemView) {
        // algorithm could be improved since there could be holes,
        // but it's very unlikely that we reach Integer.MAX_VALUE and run out of unused indexes
        int viewType = multiItemViews.size();
        while (multiItemViews.get(viewType) != null) {
            viewType++;
            if (viewType == FALLBACK_MULTI_VIEW_TYPE) {
                throw new IllegalArgumentException(
                        "Oops, we are very close to Integer.MAX_VALUE. It seems that there are no more free and unused view type integers left to add another AdapterDelegate.");
            }
        }
        return addMultiItemView(viewType, false, multiItemView);
    }

    /**
     * Adds an {@link IRvMultiItemView} with the specified view type.
     * <p>
     * Internally calls {@link #addMultiItemView(int, boolean, IRvMultiItemView)} with
     * allowReplacingaItemView = false as parameter.
     *
     * @param viewType the view type integer if you want to assign manually the view type. Otherwise
     *                 use {@link #addMultiItemView(IRvMultiItemView)} where a viewtype will be assigned manually.
     * @param itemView the multiItemView to add
     * @return self
     * @throws NullPointerException if passed multiItemView is null
     * @see #addMultiItemView(IRvMultiItemView)
     * @see #addMultiItemView(int, boolean, IRvMultiItemView)
     */
    public RvMultiItemManager<T> addMultiItemView(int viewType,
                                             @NonNull IRvMultiItemView<T> itemView) {
        return addMultiItemView(viewType, false, itemView);
    }

    /**
     * Adds an {@link IRvMultiItemView}.
     *
     * @param viewType               The viewType id
     * @param allowReplacingaItemView if true, you allow to replacing the given multiItemView any previous
     *                               multiItemView for the same view type. if false, you disallow and a {@link IllegalArgumentException}
     *                               will be thrown if you try to replace an already registered {@link IRvMultiItemView} for the
     *                               same view type.
     * @param multiItemView               The multiItemView to add
     * @throws IllegalArgumentException if <b>allowReplacingMultiItemView</b>  is false and an {@link
     *                                  IRvMultiItemView} is already added (registered)
     *                                  with the same ViewType.
     * @throws IllegalArgumentException if viewType is {@link #FALLBACK_MULTI_VIEW_TYPE} which is
     *                                  reserved
     * @see #addMultiItemView(IRvMultiItemView)
     * @see #addMultiItemView(int, IRvMultiItemView)
     * @see #setFallbackMultiItemView(IRvMultiItemView)
     */
    public RvMultiItemManager<T> addMultiItemView(int viewType, boolean allowReplacingaItemView,
                                             @NonNull IRvMultiItemView<T> multiItemView) {

        if (multiItemView == null) {
            throw new NullPointerException("multiItemView is null!");
        }

        if (viewType == FALLBACK_MULTI_VIEW_TYPE) {
            throw new IllegalArgumentException("The view type = "
                    + FALLBACK_MULTI_VIEW_TYPE
                    + " is reserved for fallback adapter itemView (see setFallbackMultiItemView() ). Please use another view type.");
        }

        if (!allowReplacingaItemView && multiItemViews.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An multiItemView is already registered for the viewType = "
                            + viewType
                            + ". Already registered multiItemView is "
                            + multiItemViews.get(viewType));
        }

        multiItemViews.put(viewType, multiItemView);

        return this;
    }

    /**
     * Removes a previously registered itemView if and only if the passed itemView is registered
     * (checks the reference of the object). This will not remove any other itemView for the same
     * viewType (if there is any).
     *
     * @param multiItemView The itemView to remove
     * @return self
     */
    public RvMultiItemManager<T> removeMultiItemView(@NonNull IRvMultiItemView<T> multiItemView) {

        if (multiItemView == null) {
            throw new NullPointerException("multiItemView is null");
        }

        int indexToRemove = multiItemViews.indexOfValue(multiItemView);

        if (indexToRemove >= 0) {
            multiItemViews.removeAt(indexToRemove);
        }
        return this;
    }

    /**
     * Removes the multiItemView for the given view types.
     *
     * @param viewType The Viewtype
     * @return self
     */
    public RvMultiItemManager<T> removeMultiItemView(int viewType) {
        multiItemViews.remove(viewType);
        return this;
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#getItemViewType(int)}. Internally it scans all
     * the registered {@link IRvMultiItemView} and picks the right one to return the ViewType integer.
     *
     * @param item     Adapter's data source
     * @param position the position in adapters data source
     * @return the ViewType (integer). Returns {@link #FALLBACK_MULTI_VIEW_TYPE} in case that the
     * fallback adapter ItemView should be used
     * @throws NullPointerException if no {@link IRvMultiItemView} has been found that is
     *                              responsible for the given data element in data set (No {@link IRvMultiItemView} for the given
     *                              ViewType)
     * @throws NullPointerException if item is null
     */
    public int getItemViewType(@NonNull T item, int position) {

        if (item == null) {
            throw new NullPointerException("Item data source is null!");
        }

        int itemViewCount = multiItemViews.size();
        for (int i = 0; i < itemViewCount; i++) {
            IRvMultiItemView<T> itemView = multiItemViews.valueAt(i);
            boolean match = itemView.isForViewType(item, position);
            if (match) {
                return multiItemViews.keyAt(i);
            }
        }

        if (fallbackMultiItemView != null) {
            return FALLBACK_MULTI_VIEW_TYPE;
        }

        throw new NullPointerException(
                "No multiItemView added that matches position=" + position + " in data source");
    }

    /**
     * This method must be called in {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     *
     * @param parent   the parent
     * @param viewType the view type
     * @return The new created ViewHolder
     * @throws NullPointerException if no multiItemView has been registered for ViewHolders
     *                              viewType
     */
    @NonNull
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IRvMultiItemView<T> multiItemView = getItemForViewType(viewType);
        if (multiItemView == null) {
            throw new NullPointerException("No multiItemView added for ViewType " + viewType);
        }

        RvViewHolder vh = multiItemView.onCreateViewHolder(parent);
        if (vh == null) {
            throw new NullPointerException("ViewHolder returned from multiItemView "
                    + multiItemView
                    + " for ViewType ="
                    + viewType
                    + " is null!");
        }
        return vh;
    }

    /**
     * Must be called from{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int,
     * List)}
     *
     * @param item       Adapter's data source
     * @param position   the position in data source
     * @param viewHolder the ViewHolder to bind
     * @param payloads   A non-null list of merged payloads. Can be empty list if requires full update.
     * @throws NullPointerException if no multiItemView has been registered for ViewHolders
     *                              viewType
     */
    public void onBindViewHolder(@NonNull T item, int position,
                                 @NonNull RvViewHolder viewHolder, List payloads) {

        IRvMultiItemView<T> multiItemView = getItemForViewType(viewHolder.getItemViewType());
        if (multiItemView == null) {
            throw new NullPointerException("No multiItemView found for item at position = "
                    + position
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        multiItemView.onBindViewHolder(item, position, viewHolder,
                payloads != null ? payloads : PAYLOADS_EMPTY_LIST);
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int,
     * List)}
     *
     * @param item       Adapter's data source
     * @param position   the position in data source
     * @param viewHolder the ViewHolder to bind
     * @throws NullPointerException if no multiItemView has been registered for ViewHolders
     *                              viewType
     */
    public void onBindViewHolder(@NonNull T item, int position,
                                 @NonNull RvViewHolder viewHolder) {
        onBindViewHolder(item, position, viewHolder, PAYLOADS_EMPTY_LIST);
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#onViewRecycled(RecyclerView.ViewHolder)}
     *
     * @param viewHolder The ViewHolder for the view being recycled
     */
    public void onViewRecycled(@NonNull RvViewHolder viewHolder) {
        IRvMultiItemView<T> multiItemView = getItemForViewType(viewHolder.getItemViewType());
        if (multiItemView == null) {
            throw new NullPointerException("No multiItemView found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        multiItemView.onViewRecycled(viewHolder);
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#onFailedToRecycleView(RecyclerView.ViewHolder)}
     *
     * @param viewHolder The ViewHolder containing the View that could not be recycled due to its
     *                   transient state.
     * @return True if the View should be recycled, false otherwise. Note that if this method
     * returns <code>true</code>, RecyclerView <em>will ignore</em> the transient state of
     * the View and recycle it regardless. If this method returns <code>false</code>,
     * RecyclerView will check the View's transient state again before giving a final decision.
     * Default implementation returns false.
     */
    public boolean onFailedToRecycleView(@NonNull RvViewHolder viewHolder) {
        IRvMultiItemView<T> multiItemView = getItemForViewType(viewHolder.getItemViewType());
        if (multiItemView == null) {
            throw new NullPointerException("No multiItemView found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        return multiItemView.onFailedToRecycleView(viewHolder);
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#onViewAttachedToWindow(RecyclerView.ViewHolder)}
     *
     * @param viewHolder Holder of the view being attached
     */
    public void onViewAttachedToWindow(RvViewHolder viewHolder) {
        IRvMultiItemView<T> multiItemView = getItemForViewType(viewHolder.getItemViewType());
        if (multiItemView == null) {
            throw new NullPointerException("No multiItemView found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        multiItemView.onViewAttachedToWindow(viewHolder);
    }

    /**
     * Must be called from {@link RecyclerView.Adapter#onViewDetachedFromWindow(RecyclerView.ViewHolder)}
     *
     * @param viewHolder Holder of the view being attached
     */
    public void onViewMultiItemViewFromWindow(RvViewHolder viewHolder) {
        IRvMultiItemView<T> multiItemView = getItemForViewType(viewHolder.getItemViewType());
        if (multiItemView == null) {
            throw new NullPointerException("No multiItemView found for "
                    + viewHolder
                    + " for item at position = "
                    + viewHolder.getAdapterPosition()
                    + " for viewType = "
                    + viewHolder.getItemViewType());
        }
        multiItemView.onViewDetachedFromWindow(viewHolder);
    }

    /**
     * Set a fallback multiItemView that should be used if no {@link IRvMultiItemView} has been found that
     * can handle a certain view type.
     *
     * @param multiItemView The {@link IRvMultiItemView} that should be used as fallback if no
     *                         other IRvMultiItemView has handled a certain view type. <code>null</code> you can set this to
     *                         null if
     *                         you want to remove a previously set fallback IRvMultiItemView
     */
    public RvMultiItemManager<T> setFallbackMultiItemView(
            @Nullable IRvMultiItemView<T> multiItemView) {
        this.fallbackMultiItemView = multiItemView;
        return this;
    }

    /**
     * Get the view type integer for the given {@link IRvMultiItemView}
     *
     * @param multiItemView The multiItemView we want to know the view type for
     * @return -1 if passed multiItemView is unknown, otherwise the view type integer
     */
    public int getViewType(@NonNull IRvMultiItemView<T> multiItemView) {
        if (multiItemView == null) {
            throw new NullPointerException("multiItemView is null");
        }

        int index = multiItemViews.indexOfValue(multiItemView);
        if (index == -1) {
            return -1;
        }
        return multiItemViews.keyAt(index);
    }

    /**
     * Get the {@link IRvMultiItemView} associated with the given view type integer
     *
     * @param viewType The view type integer we want to retrieve the associated
     *                 multiItemView for.
     * @return The {@link IRvMultiItemView} associated with the view type param if it exists,
     * the fallback multiItemView otherwise if it is set or returns <code>null</code> if no multiItemView is
     * associated to this viewType (and no fallback has been set).
     */
    @Nullable
    public IRvMultiItemView<T> getItemForViewType(int viewType) {
        IRvMultiItemView<T> multiItemView = multiItemViews.get(viewType);
        if (multiItemView == null) {
            if (fallbackMultiItemView == null) {
                return null;
            } else {
                return fallbackMultiItemView;
            }
        }

        return multiItemView;
    }

    /**
     * Get the fallback multiItemView
     *
     * @return The fallback multiItemView or <code>null</code> if no fallback multiItemView has been set
     * @see #setFallbackMultiItemView(IRvMultiItemView)
     */
    @Nullable
    public IRvMultiItemView<T> getFallbackMultiItemView() {
        return fallbackMultiItemView;
    }
}
