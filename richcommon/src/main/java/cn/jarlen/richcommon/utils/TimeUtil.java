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
package cn.jarlen.richcommon.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * DESCRIBE: Time Util
 * Created by hjl on 2016/6/28.
 */
public class TimeUtil {

    /**
     * format: xxxx-xx-xx
     */
    public final static SimpleDateFormat Y_M_D = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

    /**
     * format: xxxx-xx-xx xx:xx:xx
     */
    public final static SimpleDateFormat Y_M_D_H_M_S = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);

    public final static SimpleDateFormat MDHM = new SimpleDateFormat(
            "MM-dd HH:mm",Locale.ENGLISH);

    public final static SimpleDateFormat YMD = new SimpleDateFormat(
            "yyyy/MM/dd", Locale.ENGLISH);

    /**
     * milliseconds in one day
     */
    public final static long MS_ONE_DAY = 1000*24*60*60;

    /**
     * milliseconds in an hour
     */
    public final static long MS_AN_HOUR = 1000*60*60;

    /**
     * milliseconds in one minute
     */
    public final static long MS_ONE_MINUTE = 1000*60;

    /**
     * get time of today
     * <br>
     * @return
     */
    public static int getToday() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumIntegerDigits(2);
        nf.setMinimumIntegerDigits(2);

        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = nf.format(calendar.get(Calendar.MONTH) + 1);
        String day = nf.format(calendar.get(Calendar.DAY_OF_MONTH));
        StringBuffer today = new StringBuffer(year);
        today.append(month).append(day);

        return Integer.valueOf(today.toString());
    }

    /**
     * get time of today
     * @return
     * <br>format: xxxx/xx/xx/weekdays
     */
    public static String getToday2() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        String weekdayStr = "";
        switch (weekday) {
            case 1:
                weekdayStr = "Sun";
                break;
            case 2:
                weekdayStr = "Mon";
                break;
            case 3:
                weekdayStr = "Tues";
                break;
            case 4:
                weekdayStr = "Wed";
                break;
            case 5:
                weekdayStr = "Thurs";
                break;
            case 6:
                weekdayStr = "Fri";
                break;
            case 7:
                weekdayStr = "Sat";
                break;
        }
        StringBuffer today = new StringBuffer(year + "");
        today.append("/" + month).append("/" + day).append("  " + weekdayStr);
        return today.toString();
    }

}
