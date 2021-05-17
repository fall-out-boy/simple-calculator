package com.hqd.calc.graphics.button;

import com.hqd.calc.CalcTextField;
import com.hqd.calc.SyntaxRule;
import com.hqd.calc.utils.OperatorUtil;

import java.awt.event.ActionEvent;
import java.util.Stack;

public class CalculatorOperatorButton extends CalculatorButton {

    public CalculatorOperatorButton(String text) {
        super(text);
    }

    protected boolean prevIsOperator(CalcTextField textField) {
        Stack<String> stack = textField.getHistoryInput();
        if (!stack.isEmpty()) {
            String prev = stack.peek();
            if (OperatorUtil.isOperator(prev)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String actionPerformed(ActionEvent e, CalcTextField textField, SyntaxRule syntaxRule) {
        if (prevIsOperator(textField)) {
            textField.del();
        }
        return textField.getText() + text;
    }
}
