package com.hqd.calc.graphics;

import com.hqd.calc.CalcTextField;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class CalculatorTextField extends JTextField implements CalcTextField {
    protected static final String DEFAULT_SHOW = "";
    protected Stack<String> historyInput = new Stack<>();
    private String defaultShowText = DEFAULT_SHOW;

    public CalculatorTextField() {
        this.init();
    }

    private void init() {
        setHorizontalAlignment(JTextField.RIGHT);
        super.setText(defaultShowText);
        setEnabled(false);
        setFont(new Font("宋体", Font.BOLD, 18));
        setDisabledTextColor(Color.BLACK);
    }

    @Override
    public String clear() {
        String text = getText();
        historyInput.clear();
        setText();
        return text;
    }

    @Override
    public String del() {
        String delVal = null;
        if (!historyInput.isEmpty()) {
            delVal = historyInput.pop();
        }
        setText();
        return delVal;
    }

    @Override
    public String getText() {
        if (historyInput.isEmpty())
            return "";
        return super.getText();
    }

    @Override
    public void setText(String t) {
        clearHisHistory();
        for (int i = 0; i < t.length(); i++) {
            historyInput.push(String.valueOf(t.charAt(i)));
        }
        if (historyInput.isEmpty()) {
            t = defaultShowText;
        }
        super.setText(t);
    }

    private void setText() {
        if (historyInput.isEmpty()) {
            setText(defaultShowText);
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyInput.size(); i++) {
                sb.append(historyInput.elementAt(i));
            }
            setText(sb.toString());
        }
    }

    @Override
    public Stack<String> getHistoryInput() {
        return historyInput;
    }

    @Override
    public void addHistoryInput(String input) {
        historyInput.add(input);
    }

    @Override
    public void clearHisHistory() {
        historyInput.clear();
    }

    @Override
    public String getDefaultShow() {
        return defaultShowText;
    }

    @Override
    public void setDefaultShow(String defaultText) {
        this.defaultShowText = defaultText;
    }
}
