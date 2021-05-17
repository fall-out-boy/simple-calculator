package com.hqd.calc.utils;

public class OperatorUtil {
    public static final String OPERATOR_ADD = "+";
    public static final String OPERATOR_SUB = "-";
    public static final String OPERATOR_MUL = "×";
    public static final String OPERATOR_DIV = "÷";
    public static final String OPERATOR_LEFT_BRACKET = "(";
    public static final String OPERATOR_RIGHT_BRACKET = ")";
    public static final String PM = "±";
    public static final String OPERATOR_EQ = "=";

    public static boolean isOperator(String prev) {
        if (OPERATOR_ADD.equals(prev))
            return true;
        else if (OPERATOR_SUB.equals(prev))
            return true;
        else if (OPERATOR_MUL.equals(prev))
            return true;
        else if (OPERATOR_DIV.equals(prev))
            return true;
        return false;
    }

    ;
}
