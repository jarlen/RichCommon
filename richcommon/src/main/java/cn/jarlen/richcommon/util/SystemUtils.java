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

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.lang.reflect.Field;

/**
 * DESCRIBE:System util
 * Created by jarlen on 2016/6/22.
 */
public class SystemUtils {

    /**
     * check whether it is in MainThread
     *
     * @return
     */
    public final static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * get unused memory
     *
     * @param mContext
     * @return
     */
    public static long getMemoryUnused(Context mContext) {
        long memoryValue;
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        memoryValue = mi.availMem / 1024;
        return memoryValue;
    }

    /**
     * get Uid of current app
     *
     * @param context
     * @return
     */
    public static int getUid(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(
                    context.getPackageName(), 0);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e(e.toString());
        }
        return 0;
    }

    /**
     * restart app
     *
     * @param context
     * @param clazz
     */
    public static void reStartApp(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        int pendingIntentId = 198964;
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                pendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC, System.currentTimeMillis() + 500,
                pendingIntent);
        System.exit(0);
    }

    /**
     * check whether system is rooted
     *
     * @return
     */
    public static boolean isRooted() {
        String binaryName = "su";
        boolean rooted = false;
        String[] places = {"/sbin/", "/system/bin/", "/system/xbin/",
                "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/"};
        for (String where : places) {
            if (new File(where + binaryName).exists()) {
                rooted = true;
                break;
            }
        }
        return rooted;
    }

    /**
     * lock your screen
     * <br>need permission:
     * <br>1.android.permission.DISABLE_KEYGUARD
     * <br>2.android.permission.WAKE_LOCK
     *
     * @param context
     */
    public static void lockScreen(Context context) {
        DevicePolicyManager deviceManager = (DevicePolicyManager) context
                .getSystemService(Context.DEVICE_POLICY_SERVICE);
        deviceManager.lockNow();
    }

    /**
     * get imei of system
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        String deviceIMEI = null;
        try {
            TelephonyManager teleManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceIMEI = teleManager.getDeviceId();
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return deviceIMEI;
    }

    /**
     * get mac address of bluetooth
     * <br>need permission:android.permission.BLUETOOTH
     *
     * @return
     */
    public static String getBluetoothMac() {
        BluetoothAdapter adapter = null;
        String bluetoothMac = null;
        try {
            adapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothMac = adapter.getAddress();
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return bluetoothMac;
    }

    /**
     * get mac address of wlan
     * <br>need permission:android.permission.ACCESS_WIFI_STATE
     *
     * @param context
     * @return
     */
    public static String getWlanMac(Context context) {
        String wlanMac = null;
        try {
            WifiManager wm = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            wlanMac = wm.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        return wlanMac;
    }

    /**
     * get android version
     *
     * @return
     */
    public static float getAndroidVersion() {
        return Float.valueOf(android.os.Build.VERSION.RELEASE);
    }

    /**
     * convert dp to px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * convert px to dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        int dp = Math.round(px
                / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    /**
     * convert sp to px
     *
     * @param context
     * @param sp
     * @return
     */
    public static int sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        int px = Math.round(sp * scale);
        return px;
    }

    /**
     * get height of status bar
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj;
            obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int width = Integer.parseInt(field.get(obj).toString());
            int height = context.getResources().getDimensionPixelSize(width);
            return height;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * get height of status bar
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight2(Context context) {
        Rect frame = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    /**
     * get inches of screen
     *
     * @param context
     * @return
     */
    public static float getScreenInches(Context context) {
        float screenInches = -1;
        try {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            double width = Math.pow(dm.widthPixels / dm.xdpi, 2);
            double height = Math.pow(dm.heightPixels / dm.ydpi, 2);
            screenInches = (float) (Math.sqrt(width + height));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenInches;
    }

    /**
     * get density of screen
     *
     * @param context
     * @return
     */
    public static int getDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;
        return density;
    }

    /**
     * get total size of rom
     *
     * @param context
     * @return
     */
    public static String getRomTotalSize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * get available size of rom
     *
     * @param context
     * @return
     */
    public static String getRomAvailableSize(Context context) {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    /**
     * scan all files of system
     *
     * @param context
     */
    public static void scanSdCard(Context context) {
        Uri data = Uri.parse("file://" + Environment.getExternalStorageDirectory());
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    /**
     * scan system for some file
     *
     * @param context
     * @param filePath file path
     * @param listener cb
     */
    public static void scanSdCard(Context context, String filePath, MediaScannerConnection.OnScanCompletedListener listener) {
        MediaScannerConnection.scanFile(context,
                new String[]{filePath}, null,
                listener);
    }

}
