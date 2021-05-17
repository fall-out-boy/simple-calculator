package com.hqd.calc.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

public class NumberUtil {
    public static final String NUMBER_0 = "0";
    public static final String NUMBER_1 = "1";
    public static final String NUMBER_2 = "2";
    public static final String NUMBER_3 = "3";
    public static final String NUMBER_4 = "4";
    public static final String NUMBER_5 = "5";
    public static final String NUMBER_6 = "6";
    public static final String NUMBER_7 = "7";
    public static final String NUMBER_8 = "8";
    public static final String NUMBER_9 = "9";
    public static final String SPECIAL_NUMBER_DECIMAL = ".";
    public static final String SPECIAL_NUMBER_PERCENT = "%";
    public static final String SPECIAL_NUMBER_NEGATIVE = "-";
    //左括号，这里做特殊的数字，用于取反用
    public static final String SPECIAL_NUMBER_LEFT_BRACKET = "(";
    //右括号，这里做特殊的数字，用于取反用
    public static final String SPECIAL_NUMBER_RIGHT_BRACKET = ")";

    public static boolean isNonZeroNumber(char ch) {
        if (isDigit(ch)) {
            if (ch > NUMBER_0.charAt(0))
                return true;
        }
        return false;
    }

    public static boolean isNumberStart(String str) {
        if (StringUtils.isNotBlank(str)) {
            char ch = str.charAt(0);
            if (isNegative(ch) || isDecimal(ch) || isDigit(ch) || isLeftBracket(ch))
                return true;
        }
        return false;
    }

    public static boolean isCorrectPercent(String str) {
        int index = str.indexOf(SPECIAL_NUMBER_PERCENT);
        if (index == -1) {
            return false;
        }
        String tmp = str.substring(index);
        for (int i = 0; i < tmp.length(); i++) {
            if (!isPercent(tmp.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isLeftBracket(char ch) {
        return isLeftBracket(String.valueOf(ch));
    }

    public static boolean isLeftBracket(String str) {
        return SPECIAL_NUMBER_LEFT_BRACKET.equals(str);
    }

    public static boolean isRightBracket(char ch) {
        return isRightBracket(String.valueOf(ch));
    }

    public static boolean isRightBracket(String str) {
        return SPECIAL_NUMBER_RIGHT_BRACKET.equals(str);
    }

    public static boolean isNegative(char ch) {
        return SPECIAL_NUMBER_NEGATIVE.equals(String.valueOf(ch));
    }

    public static boolean isPercent(char ch) {
        return SPECIAL_NUMBER_PERCENT.equals(String.valueOf(ch));
    }

    public static boolean isDecimal(char ch) {
        return SPECIAL_NUMBER_DECIMAL.equals(String.valueOf(ch));
    }

    public static boolean isSpecialNumber(String str) {
        if (SPECIAL_NUMBER_DECIMAL.equals(str)
                || SPECIAL_NUMBER_NEGATIVE.equals(str)
                || SPECIAL_NUMBER_PERCENT.equals(str) || SPECIAL_NUMBER_LEFT_BRACKET.equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean isSpecialNumber(char ch) {
        return isSpecialNumber(String.valueOf(ch));
    }

    public static boolean isStartSpecialNumber(char ch) {
        boolean flag = isSpecialNumber(String.valueOf(ch));
        if (flag) {
            flag = isPercent(ch);
            if (flag)
                return false;
            return true;
        }
        return false;
    }

    public static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }

    public static boolean isNumber(String str) {
        return NumberUtils.isNumber(str);
    }

    public static String getNumberStr(int start, String str) {
        if (StringUtils.isBlank(str))
            return str;
        if (start < 0 || start >= str.length()) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        char ch = str.charAt(start);
        if (isLeftBracket(ch)) {
            int index = -1;
            start++;
            for (int i = start; i < str.length(); i++) {
                ch = str.charAt(i);
                if (isRightBracket(ch)) {
                    index = i;
                    break;
                }
            }
            if (index == -1)
                return "";
            ch = str.charAt(start);
            str = str.substring(start, index);
            start = 0;
        }
        if (isNegative(ch)) {
            sb.append(ch);
            start++;
            if (start < str.length()) {
                ch = str.charAt(start);
            } else {
                return "";
            }
        }
        if (isDigit(ch) || isDecimal(ch)) {
            for (int i = start; i < str.length(); i++) {
                ch = str.charAt(i);
                if (isDigit(ch)) {
                    sb.append(ch);
                } else if (isDecimal(ch)) {
                    if (sb.indexOf(SPECIAL_NUMBER_DECIMAL) != -1) {
                        break;
                    } else {
                        sb.append(ch);
                    }
                } else if (isPercent(ch)) {
                    sb.append(ch);
                    break;
                } else {
                    break;
                }
            }
        } else {
            return "";
        }
        String s = sb.toString();
        if (isCorrectPercent(s))
            return s;
        if (isNumber(s))
            return s;
        return "";
    }


    public static boolean isZero(char ch) {
        return NUMBER_0.equals(String.valueOf(ch));
    }

    public static boolean isDecimalNumber(String str) {
        return StringUtils.countMatches(str, SPECIAL_NUMBER_DECIMAL) == 1 ? true : false;
    }

    public static BigDecimal createBigDecimal(String str) {
        return NumberUtils.createBigDecimal(str);
    }
}
