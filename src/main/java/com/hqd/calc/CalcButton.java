package com.hqd.calc;

import com.hqd.calc.graphics.ButtonManager;

import java.awt.event.ActionEvent;

/**
 * 按钮接口
 */
public interface CalcButton {
    //获取按钮内容
    String getText();

    //点击按钮触发事件
    String actionPerformed(ActionEvent e, CalcTextField textField, SyntaxRule syntaxRule);

    //设置按钮管理器
    void setButtonManager(ButtonManager bm);
}
