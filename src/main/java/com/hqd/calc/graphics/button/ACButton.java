package com.hqd.calc.graphics.button;

import com.hqd.calc.CalcTextField;
import com.hqd.calc.SyntaxRule;

import java.awt.event.ActionEvent;

public class ACButton extends CalculatorButton {
    protected static final String AC = "AC";

    public ACButton() {
        super(AC);
    }

    @Override
    public String actionPerformed(ActionEvent e, CalcTextField textField, SyntaxRule syntaxRule) {
        return "";
    }
}
