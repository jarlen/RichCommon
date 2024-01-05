package cn.jarlen.richcommon.util;

import android.text.TextUtils;
import java.util.regex.Pattern;

/**
 * Created by jarlen on 2017/4/12.
 */
public class RegexUtils {

    /**
     * the regex of mobile
     * <p>CMCC：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
     * <p>China Unicom：130、131、132、145、155、156、175、176、185、186</p>
     * <p>China Telecom：133、153、173、177、180、181、189</p>
     * <p>Global star：1349</p>
     * <p>VNO：170</p>
     */
    public static final String REGEX_MOBILE_PHONE = "^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[0|3|6|7|8]))\\d{8}$";

    /**
     * the regex of email
     */
    public static final String REGEX_EMAIL = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    /**
     * the regex of idCard(15-digit)
     */
    public static final String REGEX_ID_CARD15 = "^[1-9]\\\\d{7}((0\\\\d)|(1[0-2]))(([0|1|2]\\\\d)|3[0-1])\\\\d{3}$";

    /**
     * the regex of idCard(18-digit)
     */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\\\d{5}[1-9]\\\\d{3}((0\\\\d)|(1[0-2]))(([0|1|2]\\\\d)|3[0-1])\\\\d{3}([0-9Xx])$";

    /**
     * the regex of ip address
     */
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    /**
     * Check text is ip address
     * @param ip
     * input
     * @return {@code true}: match
     * <br>{@code false}: doesn't match
     */
    public static boolean isIP(CharSequence ip) {
        if (TextUtils.isEmpty(ip)) {
            return false;
        }
        return isMatch(REGEX_IP, ip);
    }


    /**
     * Check text is ID Card (15-digit)
     * @param idCard
     * ID Card Number(15-digit)
     * @return {@code true}: match
     * <br>{@code false}: doesn't match
     */
    public static boolean isIDCard15(CharSequence idCard){
        if (TextUtils.isEmpty(idCard)) {
            return false;
        }
        return isMatch(REGEX_ID_CARD15,idCard);
    }

    /**
     * Check text is ID Card(18-digit)
     * @param idCard
     * ID Card Number
     * @return {@code true}: match
     * <br>{@code false}: doesn't match
     */
    public static boolean isIDCard18(CharSequence idCard){
        if (TextUtils.isEmpty(idCard)) {
            return false;
        }
        return isMatch(REGEX_ID_CARD18,idCard);
    }

    /**
     * Check text is email address
     *
     * @param email email address
     * @return {@code true}: match
     * <br>{@code false}: doesn't match
     */
    public static boolean isEmail(CharSequence email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return isMatch(REGEX_EMAIL, email);
    }

    /**
     * Check text is mobile number
     *
     * @param phoneNumber
     * @return {@code true}: match
     * <br>{@code false}: doesn't match
     */
    public static boolean isMobile(CharSequence phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        return isMatch(REGEX_MOBILE_PHONE, phoneNumber);
    }

    /**
     * check whether to match the regex
     *
     * @param regex the regex
     * @param input text
     * @return {@code true}: match
     * <br>{@code false}: doesn't match
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }
}
