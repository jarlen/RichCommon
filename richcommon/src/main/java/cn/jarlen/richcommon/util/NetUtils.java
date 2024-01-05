package cn.jarlen.richcommon.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * tools about network
 * Created by jarlen on 2017/4/4.
 */
public class NetUtils {


    /**
     * NetType : error
     **/
    public static final int NETWORK_TYPE_ERROR = -2;

    /**
     * NetType : no net
     **/
    public static final int NETWORK_TYPE_NONE = -1;

    /**
     * NetType : mobile
     **/
    public static final int NETWORN_MOBILE = 0;

    /**
     * NetType : wifi
     **/
    public static final int NETWORK_TYPE_WIFI = 1;

    /**
     * NetType : 2g
     **/
    public static final int NETWORK_TYPE_2G = 2;

    /**
     * NetType : 3g
     **/
    public static final int NETWORK_TYPE_3G = 3;

    /**
     * NetType : 4g
     **/
    public static final int NETWORK_TYPE_4G = 4;

    /**
     * Get network type
     *
     * @param context context
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager cm
                = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return NETWORK_TYPE_ERROR;
        }

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (null == networkInfo || !networkInfo.isAvailable()) {
            return NETWORK_TYPE_NONE;
        }

        if (networkInfo.isConnectedOrConnecting()) {

            int networkType = networkInfo.getType();
            if (networkType == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_TYPE_WIFI;
            } else if (networkType == ConnectivityManager.TYPE_MOBILE) {
                return getNetworkTypeByType(context);
            }
        }
        return NETWORK_TYPE_NONE;
    }


    /**
     * Whether is fast mobile network
     *
     * @param context context
     * @return
     */
    private static int getNetworkTypeByType(Context context) {
        TelephonyManager telManager
                = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (null == telManager) {
            return NETWORK_TYPE_ERROR;
        }

        switch (telManager.getNetworkType()) {

            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return NETWORK_TYPE_NONE;
                /*network: 2G*/
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_TYPE_2G;
                /*network: 3G*/
            case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_TYPE_3G;
                /*network: 4G*/
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_TYPE_4G;
            default:
                /*unknown network type*/
                return NETWORN_MOBILE;
        }
    }

    /**
     * Get ip Address
     *
     * @return null：NO NetWork
     */
    public static String getIpAddress() {
        try {
            NetworkInterface nerworkInterface;
            InetAddress inetAddress;
            for (Enumeration<NetworkInterface> en
                 = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                nerworkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr
                     = nerworkInterface.getInetAddresses();
                     enumIpAddr.hasMoreElements(); ) {
                    inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
            return null;
        } catch (SocketException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * whether network is available or not
     * @param context
     * @return
     */
    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null == cm){
            return false;
        }

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(null != networkInfo && networkInfo.isConnected()){
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }

        return false;
    }
}