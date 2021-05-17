package com.hqd.calc.graphics.button;

import com.hqd.calc.CalcTextField;
import com.hqd.calc.SyntaxRule;
import com.hqd.calc.utils.NumberUtil;

import java.awt.event.ActionEvent;

public class DELButton extends CalculatorButton {
    protected static final String DEL = "DEL";

    public DELButton() {
        super(DEL);
    }

    @Override
    public String actionPerformed(ActionEvent e, CalcTextField textField, SyntaxRule syntaxRule) {
        String expression = textField.getText();
        if (!textField.getHistoryInput().isEmpty()) {
            String lastStr = expression.substring(expression.length() - 1);
            if (lastStr.equals(NumberUtil.SPECIAL_NUMBER_RIGHT_BRACKET)) {
                for (int i = expression.length() - 2; i >= 0; i--) {
                    char ch = expression.charAt(i);
                    lastStr = ch + lastStr;
                    if (NumberUtil.isLeftBracket(ch)) {
                        break;
                    }
                }
            }
            return expression.substring(0, expression.length() - lastStr.length());
        }

        return expression;
    }
}
