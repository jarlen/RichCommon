package cn.jarlen.richcommon.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by hjl on 2017/3/14.
 */

public class NotificationUtils {

    public static void showNotification(Context context, String title, String content, PendingIntent intent, int notificationId, int defaults) {
        Notification notification = createNotification(context, title, content, intent, defaults);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notification != null) {
            nm.notify(notificationId, notification);
        }
    }

    private static Notification createNotification(Context context, String title, String content, PendingIntent pendingIntent, int defaults) {
        String tickerText = context.getResources().getString(context.getResources().getIdentifier("rc_notification_ticker_text", "string", context.getPackageName()));
        Notification notification;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            try {
                notification = new Notification(context.getApplicationInfo().icon, tickerText, System.currentTimeMillis());
                Class smallIcon = Notification.class;
                Method isLollipop = smallIcon.getMethod("setLatestEventInfo", new Class[]{Context.class, CharSequence.class, CharSequence.class, PendingIntent.class});
                isLollipop.invoke(notification, new Object[]{context, title, content, pendingIntent});
                notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_AUTO_CANCEL;
                notification.defaults = Notification.DEFAULT_ALL;
            } catch (Exception var12) {
                var12.printStackTrace();
                return null;
            }
        } else {
            boolean isLollipop1 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
            int smallIcon1 = context.getResources().getIdentifier("notification_small_icon", "drawable", context.getPackageName());
            if (smallIcon1 <= 0 || !isLollipop1) {
                smallIcon1 = context.getApplicationInfo().icon;
            }

            BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getApplicationInfo().loadIcon(context.getPackageManager());
            Bitmap appIcon = bitmapDrawable.getBitmap();
            Notification.Builder builder = new Notification.Builder(context);
            builder.setLargeIcon(appIcon);
            builder.setSmallIcon(smallIcon1);
            builder.setTicker(tickerText);
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setOngoing(true);
            builder.setDefaults(defaults);
            notification = builder.getNotification();
        }

        return notification;
    }
}
