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
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

/**
 * DESCRIBE: App tools
 * Created by jarlen on 2016/6/22.
 */
public class AppUtils {


    /**
     * get version of app
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * uninstall
     * @param context
     * @param packageName
     */
    public static void uninstallApp(Context context, String packageName) {
        boolean installed = isAppInstalled(context, packageName);
        if (!installed) {
            //TipUtils.ShowText(context, "package_not_installed");
            return;
        }

        boolean isRooted = SystemUtils.isRooted();
        if (isRooted) {

        } else {
            Uri uri = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * check if installed
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {

        if (TextUtils.isEmpty(packageName)) {
            return false;
        }

        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;

        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationInfo != null;
    }

    /**
     * get info of app
     *
     * @param context
     * @param packageName
     * @return
     */
    public static ApplicationInfo getApplicationInfo(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    /**
     * get list of app
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getPackageInfos(Context context) {
        List<PackageInfo> appInfos = null;
        PackageManager pm = context.getPackageManager();
        appInfos = pm.getInstalledPackages(0);
        return appInfos;
    }

    /**
     * start app
     *
     * @param ctx
     * @param packageName
     */
    public static void startApp(Context ctx, String packageName) {

        if(!isAppInstalled(ctx,packageName)){
            return;
        }

        PackageManager packageManager = ctx.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(intent);
    }


}
