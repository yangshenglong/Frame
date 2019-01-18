package cs.frame.util;

import java.util.regex.Pattern;

/**
 * 手机号码及验证码的格式
 */
public final class RegexUtils {


    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }


    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }


    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
        String regex = "^[1]([3][0-9]{1}|47|50|52|57|58|87|88|55|59||51|54||56|57|58|85|86|53|80|81|89|73|77|45|76|85|82|83|84|78)[0-9]{8}$";

        return Pattern.matches(regex, number);
    }

    //判断六位数字的验证码
    public static boolean checkVerificationCode(String code) {
        String regex = "^\\d{6}$";
        return Pattern.matches(regex, code);
    }

    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }


    public static boolean checkDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex, digit);
    }


    public static boolean checkDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex, decimals);
    }


    public static boolean checkBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Pattern.matches(regex, blankSpace);
    }


    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex, chinese);
    }


    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex, birthday);
    }


    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }


    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }

    // 6-12位数字或字母
    public static boolean checkPassword(String password) {
        String regex = "^[0-9a-zA-Z]{6,12}$"; //   ^(?=.*\d)(?=.*\D).{6,20}$
        return Pattern.matches(regex, password);
    }


    public static boolean checkStringLength(String arg, int min, int max) {
        boolean result = false;
        try {
            if(arg.length()>=min&&arg.length()<=max){
                result = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            return result;
        }

    }

    public static boolean checkBankNumber(String number) {
        String regex = "^(\\d{16}|\\d{19})$";
        return Pattern.matches(regex, number);
    }

    public static boolean checkMoneyTwoDecimals(String number) {
        String regex = "^[0-9]+(.[0-9]{1,2})?$";
        return Pattern.matches(regex, number);
    }

}
