package com.hqd.calc.graphics.button;

import com.hqd.calc.CalcTextField;
import com.hqd.calc.SyntaxRule;
import com.hqd.calc.utils.NumberUtil;
import com.hqd.calc.utils.OperatorUtil;

import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 取反运算符
 */
public class PMButton extends CalculatorOperatorButton {

    public PMButton() {
        super(OperatorUtil.PM);
    }

    @Override
    public String actionPerformed(ActionEvent e, CalcTextField textField, SyntaxRule syntaxRule) {
        String expression = textField.getText();
        if (prevIsOperator(textField))
            return expression;
        String regex = "(" + syntaxRule.getNumberPattern() + ")$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(expression);
        String result = "";
        //匹配最后一个数字
        if (m.find()) {
            result = m.group();
        }
        String tmp = expression.substring(0, expression.length() - result.length());
        //如果是负数，去除()和-
        if (result.startsWith(NumberUtil.SPECIAL_NUMBER_LEFT_BRACKET) && result.endsWith(NumberUtil.SPECIAL_NUMBER_RIGHT_BRACKET)) {
            result = result.substring(1, result.length() - 1);
            result = result.replace(NumberUtil.SPECIAL_NUMBER_NEGATIVE, "");
        } else {//正数添加()和-
            result = NumberUtil.SPECIAL_NUMBER_LEFT_BRACKET + NumberUtil.SPECIAL_NUMBER_NEGATIVE + result + NumberUtil.SPECIAL_NUMBER_RIGHT_BRACKET;
        }

        return tmp + result;
    }
}
