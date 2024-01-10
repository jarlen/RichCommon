package cn.jarlen.richcommon.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author jarlen
 */
public class DateUtils {

    public static final String DATE_TYPE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_TYPE_YYYY_MM = "yyyy-MM";
    public static final String DATE_TYPE_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_TYPE_YYYYMM = "yyyyMM";
    public static final String DATE_TYPE_CN = "yyyy年MM月";

    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟


    /**
     * date转成 yyyy-MM-dd 格式的string
     *
     * @param date date对象
     * @return yyyy-MM-dd 格式的string
     */
    public static String date2String(Date date) {
        SimpleDateFormat sm = new SimpleDateFormat(DATE_TYPE_YYYY_MM_DD);
        return sm.format(date);
    }

    /**
     * date转成 指定 format 格式的string
     *
     * @param date   date对象
     * @param format 日期格式
     * @return 指定format格式的string
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat sm = new SimpleDateFormat(format);
        return sm.format(date);
    }

    /**
     * string 转成 Date
     *
     * @param date   指定format格式的 string 日期
     * @param format 日期格式
     * @return 日期对象
     */
    public static Date getString2Date(String date, String format) {
        SimpleDateFormat sp = new SimpleDateFormat(format);
        Date spdate = null;
        try {
            spdate = sp.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return spdate;
    }

    /**
     * 返回unix时间戳 (1970年至今的秒数)
     *
     * @return
     */
    public static long getUnixStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 得到昨天的日期
     *
     * @return yyyy-MM-dd格式的昨天string
     */
    public static String getYestoryDate() {
        return getYestoryDate(System.currentTimeMillis(), "yyyy-MM-dd");
    }

    /**
     * 根据给定的日期获得前一天的日期
     *
     * @return 根据给定的日期获得前一天的日期
     */
    public static String getYestoryDate(long currentDate, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentDate);
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 根据给定的日期得到第二天的日期
     *
     * @return 根据给定的日期得到第二天的日期
     */
    public static String getTomorrowDate(long currentDate, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentDate);
        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String yestoday = sdf.format(calendar.getTime());
        return yestoday;
    }

    /**
     * 得到今天的日期,格式 yyyy-MM-dd
     *
     * @return 得到今天的日期, 格式 yyyy-MM-dd
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * 时间戳转化为时间格式 yyyy-MM-dd HH:mm:ss
     *
     * @param timeStamp 时间戳
     * @return yyyy-MM-dd HH:mm:ss时间格式 的字符串
     */
    public static String timeStampToStr(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 得到日期   yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     * @return 得到日期   yyyy-MM-dd
     */
    public static String formatDate(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(timeStamp * 1000);
        return date;
    }

    /**
     * 将一种格式的时间字符串 转换为另一种格式的时间字符串
     *
     * @param dateStr     时间字符串
     * @param newdPattern 新的时间格式
     * @param oldPattern  旧时间格式
     * @return 新的时间格式字符串
     */
    public static String format(String dateStr, String newdPattern, String oldPattern) {
        SimpleDateFormat newsdf = new SimpleDateFormat(newdPattern);
        SimpleDateFormat oldsdf = new SimpleDateFormat(oldPattern);
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        try {
            Date date = oldsdf.parse(dateStr);
            return newsdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Date对象转化为 指定格式的字符串
     *
     * @param date    Date对象
     * @param pattern 时间格式
     * @return 指定格式的时间字符串
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 得到时间  yyyy-MM-dd HH:mm:ss
     *
     * @param timeStamp 时间戳,注意 单位是秒，内部会乘1000
     * @return 得到时间  yyyy-MM-dd HH:mm:ss
     */
    public static String getTime(long timeStamp) {
        String time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(timeStamp * 1000);
        String[] split = date.split("\\s");
        if (split.length > 1) {
            time = split[1];
        }
        return time;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp 时间戳,注意 单位是毫秒，内部会除1000
     * @return 提示性时间字符串，如刚刚，1秒前
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

    /**
     * @param timeStamp 时间戳,注意 单位是毫秒，内部会除1000
     * @return 提示性时间字符串，如刚刚，1秒前
     * @Deprecated 该方法已过时，建议使用convertTimeToFormat(long timeStamp)
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前 精确到7天内
     */
    public static String convertTimeToFormatWeek(long timeStamp, String format) {
        long curTime = System.currentTimeMillis() / 1000;
        timeStamp = timeStamp / 1000;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 7) {
            return time / 3600 / 24 + "天前";
        } else {
            return format(new Date(timeStamp * 1000), format);
        }
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp 时间戳,注意 单位是毫秒，内部会除1000
     * @return 提示性时间字符串，(多少分钟)
     */
    public static String timeStampToFormat(long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp;
        return time / 60 + "";
    }

    /**
     * 判断两个时间戳是否足够近，60秒为分界线
     *
     * @param timeStamp1
     * @param timeStamp2
     * @return true 两个时间戳在60s内，false表示在60s之外。
     */
    public static boolean isCloseEnough(long timeStamp1, long timeStamp2) {
        return (timeStamp1 - timeStamp2) / 1000 < 3 * 60;
    }

    /**
     * 根据时间转化今天，昨天，星期
     * 今天的时间显示时分，昨天及以前的，不需要这么精确
     *
     * @param date 待转化的日期
     * @return 今天的时间显示时分/昨天/星期几
     * @author renjw
     */
    public static String getTimestampForList(Date date) {
        String todySDF = "HH:mm";
        String yesterDaySDF = "昨天";
        String otherSDF = "M月d日";
        String weekSDF = "E";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);


        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.after(targetCalendar)) {//今天
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;
        } else {
            targetCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(targetCalendar)) {//昨天
                sfd = new SimpleDateFormat(yesterDaySDF);
                time = sfd.format(date);
                return time;
            } else {
                Calendar mondayCalendar = Calendar.getInstance();
                mondayCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                if (dateCalendar.compareTo(mondayCalendar) >= 0
                        && dateCalendar.compareTo(targetCalendar) < 0) {
                    sfd = new SimpleDateFormat(weekSDF);
                    time = sfd.format(date);
                    return time;
                }
            }

        }

        if (date.getTime() == 0) {

            return "正在加载……";
        }
        sfd = new SimpleDateFormat(otherSDF);
        time = sfd.format(date);
        return time;
    }

    /**
     * 根据时间转化今天，昨天，星期
     * 今天的时间显示时分，昨天及以前的，不需要这么精确
     *
     * @param date       待转换的日期对象
     * @param hasHourMin 是否包含时分
     * @return
     */
    public static String getTimestampString(Date date, boolean hasHourMin) {
        String todySDF = "HH:mm";
        String yesterDaySDF = "昨天 HH:mm";
        String otherSDF = "M月d日";
        String weekSDF = "E HH:mm";
        if (hasHourMin) {
            otherSDF = "M月d日 HH:mm";
        }
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);


        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.after(targetCalendar)) {
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;


        } else {
            targetCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(targetCalendar)) {
                sfd = new SimpleDateFormat(yesterDaySDF);
                time = sfd.format(date);
                return time;


            } else {
                Calendar mondayCalendar = Calendar.getInstance();
                mondayCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                if (dateCalendar.compareTo(mondayCalendar) >= 0
                        && dateCalendar.compareTo(targetCalendar) < 0) {
                    sfd = new SimpleDateFormat(weekSDF);
                    time = sfd.format(date);
                    return time;
                }
            }

        }

        if (date.getTime() == 0) {

            return "正在加载……";
        }
        sfd = new SimpleDateFormat(otherSDF);
        time = sfd.format(date);
        return time;
    }

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getTimeStringFromNow(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        String timeStr = null;
        if (timeGap > DAY) {// 1天以上
            timeStr = getSimpleDate(new Date(timestamp));
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式的时间字符串 转换为long
     *
     * @param sTime yyyy-MM-dd HH:mm:ss 格式的时间字符串
     * @return 转换为long
     * @throws Exception
     */
    public static long getTimeLong(String sTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(sTime);
        return date.getTime();
    }

    /**
     * Date转换为 yyyy-MM-dd格式的时间字符串
     *
     * @param date 日期对象
     * @return yyyy-MM-dd格式的时间字符串
     * @throws Exception
     */
    public static String getSimpleDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        return time;
    }

    /**
     * yyyy-MM-dd HH:mm:ss 格式的时间字符串 转换为long
     *
     * @param time yyyy-MM-dd HH:mm:ss 格式的时间字符串
     * @return 转换为long
     * @throws Exception
     */
    public static String getLongToDateString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(time));
    }

    /**
     * 是否为同一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameDay(long time1, long time2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return 多多少天
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            return day2 - day1;
        }
    }
}
