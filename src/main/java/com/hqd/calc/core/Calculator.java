package com.hqd.calc.core;

import com.hqd.calc.CalcButton;
import com.hqd.calc.CalcFrame;
import com.hqd.calc.CalcPanel;
import com.hqd.calc.graphics.CalculatorFrame;
import com.hqd.calc.graphics.CalculatorTextField;
import com.hqd.calc.graphics.button.*;
import com.hqd.calc.graphics.panel.CalculatorPanel;

public class Calculator {
    public static void main(String[] args) {
        CalcFrame cf = new CalculatorFrame("简单计算器");
        cf.setTextField(new CalculatorTextField());
        CalcPanel cp = new CalculatorPanel(cf.getTextField());
        cp.addButtons(new CalcButton[]{new ACButton(), new DELButton(), new PMButton(), new DivButton()});
        CalcPanel cp1 = new CalculatorPanel(cf.getTextField());
        cp1.addButtons(new CalcButton[]{new Number7Button(), new Number8Button(), new Number9Button(), new MulButton()});
        CalcPanel cp2 = new CalculatorPanel(cf.getTextField());
        cp2.addButtons(new CalcButton[]{new Number4Button(), new Number5Button(), new Number6Button(), new SubButton()});
        CalcPanel cp3 = new CalculatorPanel(cf.getTextField());
        cp3.addButtons(new CalcButton[]{new Number1Button(), new Number2Button(), new Number3Button(), new AddButton()});

        CalcPanel cp4 = new CalculatorPanel(cf.getTextField());
        cp4.addButtons(new CalcButton[]{new PercentButton(), new Number0Button(), new DecimalButton(), new EQButton()});

        cf.addPanel(cp);
        cf.addPanel(cp1);
        cf.addPanel(cp2);
        cf.addPanel(cp3);
        cf.addPanel(cp4);

        cf.showFrame();
    }
}
