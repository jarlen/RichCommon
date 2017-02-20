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
package cn.jarlen.richcommon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashSet;
import java.util.Set;

public class SpUtils {

    SharedPreferences sp;
    Context context;
    String TAG = SpUtils.class.getSimpleName();
    Editor ed;

    private volatile static SpUtils INSTANCE = null;

    public static SpUtils newInstance(Context context, String name) {
        if (INSTANCE == null) {
            synchronized (SpUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SpUtils(context, name,
                            Context.MODE_PRIVATE);
                }
            }
        }
        return INSTANCE;
    }

    public SpUtils(Context context, String name, int mode) {
        this.context = context;
        sp = this.context.getSharedPreferences(name, mode);
        ed = sp.edit();
    }

    public void addMess(String key, long value) {
        ed.putLong(key, value);
        ed.commit();
    }

    public void addMess(String key, int value) {
        ed.putInt(key, value);
        ed.commit();
    }

    public void addMess(String key, float value) {
        ed.putFloat(key, value);
        ed.commit();
    }

    public void addMess(String key, boolean value) {
        ed.putBoolean(key, value);
        ed.commit();
    }

    public boolean getMessBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public boolean getMessBoolean(String key) {
        return getMessBoolean(key, false);
    }

    public void deteletMess() {
        ed.clear();
        ed.commit();
    }

    public void addMess(String key, Set<String> set) {
        ed.putStringSet(key, set);
        ed.commit();
    }

    public String getMessString(String key) {
        return sp.getString(key, "");
    }

    public String getMessString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public int getMessInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public float getMessLiht(String key) {
        return sp.getFloat(key, -1);
    }

    public long getMessLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public Set<String> getMessSet(String key) {
        return sp.getStringSet(key, new HashSet<String>());
    }

    public void addMess(String key, String value) {
        ed.putString(key, value);
        ed.commit();
    }
}
