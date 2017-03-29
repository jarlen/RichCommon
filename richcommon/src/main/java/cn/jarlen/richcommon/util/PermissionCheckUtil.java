package cn.jarlen.richcommon.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hjl on 2017/3/29.
 */

public class PermissionCheckUtil {
    private static final String TAG = PermissionCheckUtil.class.getSimpleName();

    public PermissionCheckUtil() {
    }

    public static boolean checkPermissions(Context context, @NonNull String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else if (permissions != null && permissions.length != 0) {
            String[] arr$ = permissions;
            int len$ = permissions.length;

            for (int i$ = 0; i$ < len$; ++i$) {
                String permission = arr$[i$];
                if (context.checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    private static String getNotGrantedPermissionMsg(Context context, List<String> permissions) {
        HashSet permissionsValue = new HashSet();
        Iterator result = permissions.iterator();

        while (result.hasNext()) {
            String i$ = (String) result.next();
            String permissionValue = context.getString(context.getResources().getIdentifier("rc_" + i$, "string", context.getPackageName()), new Object[]{Integer.valueOf(0)});
            permissionsValue.add(permissionValue);
        }

        String result1 = "(";

        String value;
        for (Iterator i$1 = permissionsValue.iterator(); i$1.hasNext(); result1 = result1 + value + " ") {
            value = (String) i$1.next();
        }

        result1 = result1.trim() + ")";
        return result1;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean canDrawOverlays(Context context) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                boolean booleanValue = ((Boolean) Settings.class.getDeclaredMethod("canDrawOverlays", new Class[]{Context.class}).invoke((Object) null, new Object[]{context})).booleanValue();
                LogUtils.d(TAG, "isFloatWindowOpAllowed allowed: " + booleanValue);
                return booleanValue;
            } catch (Exception var6) {
                LogUtils.e(TAG, String.format("getDeclaredMethod:canDrawOverlays! Error:%s, etype:%s", new Object[]{var6.getMessage(), var6.getClass().getCanonicalName()}));
                return true;
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        } else {
            Object systemService = context.getSystemService(Context.APP_OPS_SERVICE);

            Method method;
            try {
                method = Class.forName("android.app.AppOpsManager").getMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class});
            } catch (NoSuchMethodException var8) {
                LogUtils.e(TAG, String.format("NoSuchMethodException method:checkOp! Error:%s", new Object[]{var8.getMessage()}));
                method = null;
            } catch (ClassNotFoundException var9) {
                var9.printStackTrace();
                method = null;
            }

            if (method != null) {
                try {
                    Integer e = (Integer) method.invoke(systemService, new Object[]{Integer.valueOf(24), Integer.valueOf(context.getApplicationInfo().uid), context.getPackageName()});
                    result = e.intValue() == 0;
                } catch (Exception var7) {
                    LogUtils.e(TAG, String.format("call checkOp failed: %s etype:%s", new Object[]{var7.getMessage(), var7.getClass().getCanonicalName()}));
                }
            }

            LogUtils.d(TAG, "isFloatWindowOpAllowed allowed: " + result);
            return result;
        }
    }
}
