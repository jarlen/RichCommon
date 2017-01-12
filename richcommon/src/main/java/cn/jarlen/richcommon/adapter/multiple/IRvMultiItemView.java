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
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import cn.jarlen.richcommon.adapter.RvViewHolder;

/**
 * This delegate provide method to hook in this delegate to {@link RecyclerView.Adapter} lifecycle.
 * This "hook in" mechanism is provided by {@link RvMultiItemManager} and that is the
 * component
 * you have to use.
 *
 * @param <T> The type of the data source
 * @author jarlen
 * @since 1.0
 */
public abstract class IRvMultiItemView<T> {

  /**
   * Called to determine whether this AdapterDelegate is the responsible for the given data
   * element.
   *
   * @param item The data source of the item
   * @param position The position in the datasource
   * @return true, if this item is responsible,  otherwise false
   */
  protected abstract boolean isForViewType(@NonNull T item, int position);

  /**
   * Creates the  {@Link RvViewHolder } for the given data source item
   *
   * @param parent The ViewGroup parent of the given datasource
   * @return The new instantiated {@link RvViewHolder}
   */
  @NonNull
  abstract protected RvViewHolder onCreateViewHolder(ViewGroup parent);

  /**
   * Called to bind the {@link RvViewHolder} to the item of the datas source set
   *
   * @param item The data source
   * @param position The position in the datasource
   * @param holder The {@link RvViewHolder} to bind
   * @param payloads A non-null list of merged payloads. Can be empty list if requires full update.
   */
  protected abstract void onBindViewHolder(@NonNull T item, int position,
                                           @NonNull RvViewHolder holder, @NonNull List<Object> payloads);

  /**
   * Called when a view created by this adapter has been recycled.
   *
   * <p>A view is recycled when a {@link RecyclerView.LayoutManager} decides that it no longer
   * needs to be attached to its parent {@link RecyclerView}. This can be because it has
   * fallen out of visibility or a set of cached views represented by views still
   * attached to the parent RecyclerView. If an item view has large or expensive data
   * bound to it such as large bitmaps, this may be a good place to release those
   * resources.</p>
   * <p>
   * RecyclerView calls this method right before clearing ViewHolder's internal data and
   * sending it to RecycledViewPool. This way, if ViewHolder was holding valid information
   * before being recycled, you can call {@link RecyclerView.ViewHolder#getAdapterPosition()} to
   * get
   * its adapter position.
   *
   * @param viewHolder The RvViewHolder for the view being recycled
   */
  protected void onViewRecycled(@NonNull RvViewHolder viewHolder) {
  }

  /**
   * Called by the RecyclerView if a ViewHolder created by this Adapter cannot be recycled
   * due to its transient state. Upon receiving this callback, Adapter can clear the
   * animation(s) that effect the View's transient state and return <code>true</code> so that
   * the View can be recycled. Keep in mind that the View in question is already removed from
   * the RecyclerView.
   * <p>
   * In some cases, it is acceptable to recycle a View although it has transient state. Most
   * of the time, this is a case where the transient state will be cleared in
   * {@link #onBindViewHolder(T,int,RvViewHolder,int)} call when View is
   * rebound to a new position.
   * For this reason, RecyclerView leaves the decision to the Adapter and uses the return
   * value of this method to decide whether the View should be recycled or not.
   * <p>
   * Note that when all animations are created by {@link RecyclerView.ItemAnimator}, you
   * should never receive this callback because RecyclerView keeps those Views as children
   * until their animations are complete. This callback is useful when children of the item
   * views create animations which may not be easy to implement using an {@link
   * RecyclerView.ItemAnimator}.
   * <p>
   * You should <em>never</em> fix this issue by calling
   * <code>holder.itemView.setHasTransientState(false);</code> unless you've previously called
   * <code>holder.itemView.setHasTransientState(true);</code>. Each
   * <code>View.setHasTransientState(true)</code> call must be matched by a
   * <code>View.setHasTransientState(false)</code> call, otherwise, the state of the View
   * may become inconsistent. You should always prefer to end or cancel animations that are
   * triggering the transient state instead of handling it manually.
   *
   * @param holder The ViewHolder containing the View that could not be recycled due to its
   * transient state.
   * @return True if the View should be recycled, false otherwise. Note that if this method
   * returns <code>true</code>, RecyclerView <em>will ignore</em> the transient state of
   * the View and recycle it regardless. If this method returns <code>false</code>,
   * RecyclerView will check the View's transient state again before giving a final decision.
   * Default implementation returns false.
   */
  protected boolean onFailedToRecycleView(@NonNull RvViewHolder holder) {
    return false;
  }

  /**
   * Called when a view created by this adapter has been attached to a window.
   *
   * <p>This can be used as a reasonable signal that the view is about to be seen
   * by the user. If the adapter previously freed any resources in
   * {@link #onViewDetachedFromWindow(RvViewHolder)
   * onViewDetachedFromWindow}
   * those resources should be restored here.</p>
   *
   * @param holder Holder of the view being attached
   */
  protected void onViewAttachedToWindow(@NonNull RvViewHolder holder) {
  }

  /**
   * Called when a view created by this adapter has been detached from its window.
   *
   * <p>Becoming detached from the window is not necessarily a permanent condition;
   * the consumer of an Adapter's views may choose to cache views offscreen while they
   * are not visible, attaching an detaching them as appropriate.</p>
   *
   * @param holder Holder of the view being detached
   */
  protected void onViewDetachedFromWindow(RvViewHolder holder) {
  }
}
