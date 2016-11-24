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
package cn.jarlen.richcommon.ui;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Stack;

/**
 * Describe: Stacks Manager of Activity
 * <br>Created by jarlen
 * <br>Date: 2016/5/3
 */
public class ActivityStack {

    private volatile static ActivityStack instance;

    /**
     * Activity Stacks
     */
    private Stack<Activity> activities;

    public static ActivityStack getInstanse() {
        if (instance == null) {
            synchronized (ActivityStack.class) {
                if (instance == null) {
                    instance = new ActivityStack();
                }
            }
        }
        return instance;
    }

    private ActivityStack() {
        activities = new Stack<Activity>();
    }


    /**
     * get all the activities in stacks
     *
     * @return
     */
    public Stack<Activity> getAllActivities() {
        return activities;
    }

    /**
     * put one activity into stacks
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        activities.push(activity);
    }

    /**
     * pop one object(Activity) out of stacks
     *
     * @param activity
     * @param isFinish
     * @return
     */
    public boolean popActivity(Activity activity, boolean isFinish) {
        if (activities.isEmpty()) {
            return false;
        }
        if (activities.contains(activity)) {
            if (isFinish) {
                activity.finish();
            }
            return activities.remove(activity);
        }
        return false;
    }

    /**
     * @param isFinish
     * @return
     */
    public boolean popActivity(boolean isFinish) {
        if (activities.isEmpty()) {
            return false;
        }

        Activity activity = activities.pop();

        if (activity == null) {
            return false;
        }

        if (isFinish) {
            activity.finish();
        }

        return true;
    }

    /**
     * @param isFinish
     */
    public void popAllActivities(boolean isFinish) {
        if (activities.isEmpty()) {
            return;
        }
        for (int i = 0, size = activities.size(); i < size; i++) {
            if (null != activities.get(i)) {
                activities.get(i).finish();
            }
        }

        activities.clear();
    }

    /**
     * @param position
     * @param isFinish
     */
    public void popActivitiesUntil(int position, boolean isFinish) {

        if (activities.isEmpty() || position < 0) {
            return;
        }

        for (int i = 0; i <= position; i++) {

            Activity activity = activities.firstElement();
            if (isFinish) {
                activity.finish();
            }
            activities.remove(activity);
        }


    }

    /**
     * @param activity
     * @param isFinish
     */
    public void popActivitiesUntil(Activity activity, boolean isFinish) {
        if (activities.isEmpty() || !activities.contains(activity)) {
            return;
        }
        int posistion = activities.indexOf(activity) - 1;
        popActivitiesUntil(posistion, isFinish);
    }

    /**
     * @param cls
     * @param isFinish
     * @param <T>
     */
    public <T extends Activity> void popActivitiesUntil(Class<T> cls, boolean isFinish) {
        if (activities.isEmpty()) {
            return;
        }
        int posistion = -1;
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getClass().equals(cls)) {
                posistion = i;
                break;
            }
        }
        popActivitiesUntil(posistion, isFinish);
    }

    /**
     *
     */
    public void exitApp() {
        popAllActivities(true);
    }

    /**
     * @param activityName
     * @return
     */
    public Activity getActivityByClassName(String activityName) {
        if (activities.isEmpty()) {
            return null;
        }
        for (Activity ac : activities) {
            if (ac.getClass().getName().indexOf(activityName) >= 0) {
                return ac;
            }
        }
        return null;
    }

    /**
     * @param cs
     * @return
     */
    public Activity getActivityByClass(Class<Activity> cs) {
        if (activities.isEmpty()) {
            return null;
        }
        for (Activity ac : activities) {
            if (ac.getClass().equals(cs)) {
                return ac;
            }
        }
        return null;
    }

    /**
     * release all resourse for view
     * @param view
     */
    public static void unbindReferences(View view) {
        try {
            if (view != null) {
                view.destroyDrawingCache();
                unbindViewReferences(view);
                if (view instanceof ViewGroup){
                    unbindViewGroupReferences((ViewGroup) view);
                }
            }
        } catch (Throwable e) {
            // whatever exception is thrown just ignore it because a crash is
            // always worse than this method not doing what it's supposed to do
        }
    }

    private static void unbindViewReferences(View view) {
        // set all listeners to null (not every view and not every API level
        // supports the methods)
        try {
            view.setOnClickListener(null);
            view.setOnCreateContextMenuListener(null);
            view.setOnFocusChangeListener(null);
            view.setOnKeyListener(null);
            view.setOnLongClickListener(null);
            view.setOnClickListener(null);
        } catch (Throwable mayHappen) {
            //todo
        }

        // set background to null
        Drawable d = view.getBackground();
        if (d != null) {
            d.setCallback(null);
        }

        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            d = imageView.getDrawable();
            if (d != null) {
                d.setCallback(null);
            }
            imageView.setImageDrawable(null);
            imageView.setBackgroundDrawable(null);
        }

        // destroy WebView
        if (view instanceof WebView) {
            WebView webview = (WebView) view;
            webview.stopLoading();
            webview.clearFormData();
            webview.clearDisappearingChildren();
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.destroyDrawingCache();
            webview.destroy();
            webview = null;
        }

        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            try {
                listView.removeAllViewsInLayout();
            } catch (Throwable mayHappen) {
            }
            ((ListView) view).destroyDrawingCache();
        }
    }

    private static void unbindViewGroupReferences(ViewGroup viewGroup) {
        int nrOfChildren = viewGroup.getChildCount();
        for (int i = 0; i < nrOfChildren; i++) {
            View view = viewGroup.getChildAt(i);
            unbindViewReferences(view);
            if (view instanceof ViewGroup)
                unbindViewGroupReferences((ViewGroup) view);
        }
        try {
            viewGroup.removeAllViews();
        } catch (Throwable mayHappen) {
            // AdapterViews, ListViews and potentially other ViewGroups don't
            // support the removeAllViews operation
        }
    }
}
