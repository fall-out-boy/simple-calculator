package com.hqd.calc.graphics.panel;

import com.hqd.calc.CalcButton;
import com.hqd.calc.CalcPanel;
import com.hqd.calc.CalcTextField;
import com.hqd.calc.graphics.ButtonManager;

import javax.swing.*;
import java.awt.*;

public class CalculatorPanel extends JPanel implements CalcPanel {
    //创建面板容器，指定为表格布局，1*4，水平垂直间距为3
    protected static final LayoutManager DEFAULT_LAYOUT = new GridLayout(1, 4, 3, 3);
    private CalcTextField textField;
    private LayoutManager layoutManager;
    private ButtonManager bm;

    public CalculatorPanel(CalcTextField textField) {
        this(textField, DEFAULT_LAYOUT);
    }

    public CalculatorPanel(CalcTextField textField, LayoutManager layoutManager) {
        this.textField = textField;
        this.layoutManager = layoutManager;
        this.init();
    }


    protected void init() {
        this.bm = new ButtonManager(textField);
        setLayout(this.layoutManager);
    }

    @Override
    public void addButtons(CalcButton[] cbs) {
        for (CalcButton cb : cbs) {
            bm.addButton(cb);
            cb.setButtonManager(bm);
            if (cb instanceof Component)
                this.add((Component) cb);
        }
    }

    @Override
    public ButtonManager getButtonManager() {
        return bm;
    }
}
