package com.hqd.calc.engine;

import com.hqd.calc.CalcEngine;
import com.hqd.calc.SyntaxRule;
import com.hqd.calc.exception.CalcException;
import com.hqd.calc.syntax.DefaultSyntaxRule;
import com.hqd.calc.utils.OperatorUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 计算引擎的基础类
 */
@Getter
@Setter
public abstract class CalcEngineBase implements CalcEngine {
    protected SyntaxRule syntaxRule = new DefaultSyntaxRule(true);

    @Override
    public BigDecimal calcResult(String expression) throws CalcException {
        String text = syntaxRule.checkSyntax(expression);
        return doCalcResult(text);
    }

    /**
     * 两个数的运算
     *
     * @param num1
     * @param num2
     * @param operator
     * @return
     */
    protected BigDecimal calculate(BigDecimal num1, BigDecimal num2, String operator) {
        BigDecimal result = new BigDecimal(0);
        switch (operator) {
            case OperatorUtil.OPERATOR_ADD:
                result = num1.add(num2);
                break;
            case OperatorUtil.OPERATOR_SUB:
                result = num2.subtract(num1);
                break;
            case OperatorUtil.OPERATOR_MUL:
                result = num1.multiply(num2);
                break;
            case OperatorUtil.OPERATOR_DIV:
                result = num2.divide(num1, 10, BigDecimal.ROUND_HALF_UP);
                break;
            default:
                break;
        }
        return result;
    }


    protected abstract BigDecimal doCalcResult(String expression) throws CalcException;

    /**
     * 运算等级
     *
     * @param operator
     * @return
     */
    protected int operatorGrade(char operator) {
        return operatorGrade(String.valueOf(operator));
    }

    /**
     * 运算符优先级
     *
     * @param operator
     * @return
     */
    protected int operatorGrade(String operator) {
        if (OperatorUtil.OPERATOR_MUL.equals(operator) || OperatorUtil.OPERATOR_DIV.equals(operator)) {
            return 1;
        } else if (OperatorUtil.OPERATOR_ADD.equals(operator) || OperatorUtil.OPERATOR_SUB.equals(operator)) {
            return 0;
        } else if (OperatorUtil.OPERATOR_LEFT_BRACKET.equals(operator) || OperatorUtil.OPERATOR_RIGHT_BRACKET.equals(operator)) {
            return 2;
        } else {
            return 3;
        }
    }
}
