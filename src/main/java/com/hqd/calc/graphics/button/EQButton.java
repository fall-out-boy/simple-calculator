package com.hqd.calc.graphics.button;

import com.hqd.calc.CalcEngine;
import com.hqd.calc.CalcTextField;
import com.hqd.calc.SyntaxRule;
import com.hqd.calc.engine.RPNCalcEngine;
import com.hqd.calc.utils.NumberUtil;
import com.hqd.calc.utils.OperatorUtil;
import org.apache.commons.lang3.StringUtils;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

public class EQButton extends CalculatorButton {
    private CalcEngine calcEngine = new RPNCalcEngine();

    public EQButton() {
        super(OperatorUtil.OPERATOR_EQ);
    }

    @Override
    public String actionPerformed(ActionEvent e, CalcTextField calcTextField, SyntaxRule syntaxRule) {
        String expression = calcTextField.getText();
        if (StringUtils.isBlank(expression)) {
            return expression;
        }
        BigDecimal bd = calcEngine.calcResult(expression);
        String result = bd.toPlainString();
        //结果小于0，补上()
        if (bd.compareTo(new BigDecimal(NumberUtil.NUMBER_0)) == -1) {
            result = NumberUtil.SPECIAL_NUMBER_LEFT_BRACKET + result + NumberUtil.SPECIAL_NUMBER_RIGHT_BRACKET;
        }
        return result;
    }
}
