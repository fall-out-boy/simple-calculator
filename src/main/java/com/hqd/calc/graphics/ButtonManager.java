package com.hqd.calc.graphics;

import com.hqd.calc.CalcButton;
import com.hqd.calc.CalcTextField;
import com.hqd.calc.SyntaxRule;
import com.hqd.calc.exception.SyntaxException;
import com.hqd.calc.syntax.DefaultSyntaxRule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 按键管理器
 */
public class ButtonManager implements ActionListener {
    private Map<String, CalcButton> buttonMap = new HashMap<>();
    protected SyntaxRule syntaxRule = new DefaultSyntaxRule();
    //显示框
    private CalcTextField textField;

    public ButtonManager(CalcTextField textField) {
        this.textField = textField;
    }

    /**
     * 按钮事件回调
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        CalcButton cb = buttonMap.get(e.getActionCommand());
        if (cb != null) {
            String text = cb.actionPerformed(e, textField, syntaxRule);
            try {
                text = syntaxRule.checkSyntax(text);
                textField.setText(text);
            } catch (SyntaxException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void addButton(CalcButton cb) {
        if (cb != null) {
            if (!buttonMap.containsKey(cb.getText())) {
                buttonMap.put(cb.getText(), cb);
            }
        }
    }

    public void removeButton(String text) {
        buttonMap.remove(text);
    }

    public void removeButton(CalcButton cb) {
        if (cb != null)
            removeButton(cb.getText());
    }

    public Collection<CalcButton> getButtons() {
        return buttonMap.values();
    }

    public CalcTextField getTextField() {
        return textField;
    }

    public void setTextField(CalcTextField textField) {
        this.textField = textField;
    }

    public SyntaxRule getSyntaxRule() {
        return syntaxRule;
    }

    public void setSyntaxRule(SyntaxRule syntaxRule) {
        this.syntaxRule = syntaxRule;
    }
}
