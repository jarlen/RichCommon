package cn.jarlen.richcommon.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public interface PermissionCallbacks extends ActivityCompat.OnRequestPermissionsResultCallback {

        void onPermissionsGranted(int requestCode, List<String> perms);

        void onPermissionsDenied(int requestCode, List<String> perms);

    }

    /**
     * Request a set of permissions, showing rationale if the system requests it.
     *
     * @param object              Activity or Fragment requesting permissions. Should implement
     *                            {@link ActivityCompat.OnRequestPermissionsResultCallback}
     *                            or {@link android.support.v13.app##FragmentCompat#OnRequestPermissionsResultCallback}
     * @param rationale           a message explaining why the application needs this set of permissions, will
     *                            be displayed if the user rejects the request the first time.
     * @param snackbarActionResId custom text for snackbar action button
     * @param requestCode         request code to track this request, must be < 256.
     * @param perms               a set of permissions to be requested.
     */
    public static void requestPermissions(final Object object, String rationale, int snackbarActionResId,
                                          final int requestCode, final String... perms) {

        checkCallingObjectSuitability(object);

        boolean shouldShowRationale = false;
        for (String perm : perms) {
            shouldShowRationale = shouldShowRationale || shouldShowRequestPermissionRationale(object, perm);
        }

        if (shouldShowRationale) {
            Activity activity = getActivity(object);
            if (null == activity) {
                return;
            }

//            Snackbar.make(activity.findViewById(android.R.id.content), rationale, Snackbar.LENGTH_LONG)
//                    .setAction(snackbarActionResId , new View.OnClickListener(){
//                        @Override
//                        public void onClick(View v) {
//                            executePermissionsRequest(object, perms, requestCode);
//                        }
//                    })
//                    .show();

        } else {
            executePermissionsRequest(object, perms, requestCode);
        }
    }


    /**
     * prompt the user to go to the app's settings screen and enable permissions. If the
     * user clicks snackbar's action, they are sent to the settings screen. The result is returned
     * to the Activity via {@link Activity#onActivityResult(int, int, Intent)}.
     *
     * @param object         Activity or Fragment requesting permissions.
     * @param rationale      a message explaining why the application needs this set of permissions, will
     *                       be displayed on the snackbar.
     * @param snackbarAction text disPlayed on the snackbar's action
     * @param requestCode    If >= 0, this code will be returned in onActivityResult() when the activity exits.
     */
    public static void goSettings2Permissions(final Object object, String rationale, String snackbarAction, final int requestCode) {
        checkCallingObjectSuitability(object);

        final Activity activity = getActivity(object);
        if (null == activity) {
            return;
        }

//        Snackbar.make(activity.findViewById(android.R.id.content), rationale, Snackbar.LENGTH_LONG)
//                .setAction(snackbarAction, new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
//                        intent.setData(uri);
//                        startForResult(object, intent, requestCode);
//                    }
//                })
//                .show();
    }


    /**
     * Check if at least one permission in the list of denied permissions has been permanently
     * denied (user clicked "Never ask again").
     *
     * @param object            Activity or Fragment requesting permissions.
     * @param deniedPermissions list of denied permissions, usually from
     *                          {@link PermissionCallbacks#onPermissionsDenied(int, List)}
     * @return {@code true} if at least one permission in the list was permanently denied.
     */
    public static boolean somePermissionPermanentlyDenied(Object object, final String... deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (permissionPermanentlyDenied(object, deniedPermission)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Request a set of permissions, showing rationale if the system requests it.
     *
     * @param object      Activity or Fragment requesting permissions. Should implement
     *                    {@link ActivityCompat.OnRequestPermissionsResultCallback}
     *                    or {@link android.support.v13.app##FragmentCompat#OnRequestPermissionsResultCallback}
     * @param rationale   a message explaining why the application needs this set of permissions, will
     *                    be displayed if the user rejects the request the first time.
     * @param requestCode request code to track this request, must be < 256.
     * @param perms       a set of permissions to be requested.
     */
    public static void requestPermissions(Object object, String rationale, int requestCode, String... perms) {
        requestPermissions(object, rationale, android.R.string.ok, requestCode, perms);
    }


    /**
     * Check if a permission has been permanently denied (user clicked "Never ask again").
     *
     * @param object           Activity or Fragment requesting permissions.
     * @param deniedPermission denied permission.
     * @return {@code true} if the permissions has been permanently denied.
     */
    public static boolean permissionPermanentlyDenied(Object object, String deniedPermission) {
        return !shouldShowRequestPermissionRationale(object, deniedPermission);
    }


    /**
     * Handle the result of a permission request, should be called from the calling Activity's
     * {@link ActivityCompat.OnRequestPermissionsResultCallback#onRequestPermissionsResult(int, String[], int[])}
     * method.
     * <p>
     * If any permissions were granted or denied, the {@code object} will receive the appropriate
     * callbacks through {@link PermissionCallbacks}
     *
     * @param requestCode  requestCode argument to permission result callback.
     * @param permissions  permissions argument to permission result callback.
     * @param grantResults grantResults argument to permission result callback.
     * @param receivers    an array of objects that implement {@link PermissionCallbacks}.
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                  int[] grantResults, Object... receivers) {

        // Make a collection of granted and denied permissions from the request.
        ArrayList<String> granted = new ArrayList<>();
        ArrayList<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        // iterate through all receivers
        for (Object object : receivers) {
            // Report granted permissions, if any.
            if (!granted.isEmpty()) {
                if (object instanceof PermissionCallbacks) {
                    ((PermissionCallbacks) object).onPermissionsGranted(requestCode, granted);
                }
            }

            // Report denied permissions, if any.
            if (!denied.isEmpty()) {
                if (object instanceof PermissionCallbacks) {
                    ((PermissionCallbacks) object).onPermissionsDenied(requestCode, denied);
                }
            }
        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    private static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);

        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context the calling context.
     * @param perms   one ore more permissions, such as {@code android.Manifest.permission.CAMERA}.
     * @return true if all permissions are already granted, false if at least one permission
     * is not yet granted.
     */
    public static boolean hasPermissions(Context context, String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            LogUtils.w(TAG, "hasPermissions: API version < M, returning true by default");
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = (ActivityCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    private static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    private static void startForResult(Object object, Intent intent, int requestCode) {
        if (object instanceof Activity) {
            ((Activity) object).startActivityForResult(intent, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).startActivityForResult(intent, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).startActivityForResult(intent, requestCode);
        }
    }

    private static void checkCallingObjectSuitability(Object object) {
        // Make sure Object is an Activity or Fragment
        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

        if (!(isSupportFragment || isActivity || (isAppFragment && isMinSdkM))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
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
