package com.hqd.calc.graphics.button;

import com.hqd.calc.CalcTextField;
import com.hqd.calc.SyntaxRule;

import java.awt.event.ActionEvent;

public class DELButton extends CalculatorButton {
    protected static final String DEL = "DEL";

    public DELButton() {
        super(DEL);
    }

    @Override
    public String actionPerformed(ActionEvent e, CalcTextField textField, SyntaxRule syntaxRule) {
        String expression = textField.getText();
        if (!textField.getHistoryInput().isEmpty())
            return expression.substring(0, expression.length() - 1);
        return expression;
    }
}
