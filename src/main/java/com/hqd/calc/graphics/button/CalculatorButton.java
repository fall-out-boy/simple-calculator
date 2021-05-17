package com.hqd.calc.graphics.button;

import com.hqd.calc.CalcButton;
import com.hqd.calc.CalcTextField;
import com.hqd.calc.SyntaxRule;
import com.hqd.calc.graphics.ButtonManager;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 计算器按钮
 */
public class CalculatorButton extends JButton implements CalcButton {
    protected String text;
    protected ButtonManager bm;

    public CalculatorButton(String text) {
        super(text);
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String actionPerformed(ActionEvent e, CalcTextField textField, SyntaxRule syntaxRule) {
        if (textField != null)
            return textField.getText() + text;
        return text;
    }

    @Override
    public void setButtonManager(ButtonManager bm) {
        this.bm = bm;
        this.addActionListener(bm);
    }
}
