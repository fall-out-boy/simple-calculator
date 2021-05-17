package com.hqd.calc.engine;

import com.hqd.calc.exception.CalcException;
import com.hqd.calc.utils.NumberUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Stack;

/**
 * 逆波兰算法
 */
public class RPNCalcEngine extends CalcEngineBase {

    @Override
    protected BigDecimal doCalcResult(String expression) throws CalcException {
        String suffixExpression = infix2Suffix();
        System.out.println(suffixExpression);
        return suffixCalc(suffixExpression);
    }

    /**
     * 后缀表达式计算
     *
     * @param suffixCalcStr
     * @return
     * @throws CalcException
     */
    private BigDecimal suffixCalc(String suffixCalcStr) throws CalcException {
        Stack<BigDecimal> numberStack = new Stack<>();
        String[] strs = suffixCalcStr.split(" ");
        for (int i = 0; i < strs.length; i++) {
            //数字，入数栈
            if (NumberUtil.isNumber(strs[i])) {
                numberStack.push(new BigDecimal(strs[i]));
            } else {
                //数栈有超过两个数，计算
                if (numberStack.size() >= 2) {
                    BigDecimal num1 = numberStack.pop();
                    BigDecimal num2 = numberStack.pop();
                    numberStack.push(calculate(num1, num2, strs[i]));
                }

            }
        }
        if (numberStack.size() != 1)
            throw new CalcException("运算异常");
        BigDecimal bg = numberStack.pop();
        return bg.stripTrailingZeros();
    }

    /**
     * 中缀表达式转后缀表达式
     *
     * @return
     */
    private String infix2Suffix() {
        Collection<String> els = syntaxRule.getExpressions();
        StringBuilder sb = new StringBuilder("");
        Stack<String> operatorStack = new Stack<>();
        Stack<String> numberStack = new Stack<>();
        for (String str : els) {
            //数字入数栈
            if (NumberUtil.isNumber(str)) {
                numberStack.push(str);
            } else {
                //操作栈为空或者为(，直接入操作栈，
                if (NumberUtil.isLeftBracket(str) || operatorStack.isEmpty()) {
                    operatorStack.push(str);
                } else if (NumberUtil.isRightBracket(str)) {//为),操作栈出栈并压入数栈，直到遇见(
                    String operator = operatorStack.pop();
                    //遇到(，或者操作栈为空
                    while (!NumberUtil.isLeftBracket(operator) && !operatorStack.isEmpty()) {
                        numberStack.push(operator);
                        operator = operatorStack.pop();
                    }
                } else {
                    //其他符号，比较操作栈顶运算符优先级
                    while (!operatorStack.isEmpty() && !NumberUtil.isLeftBracket(operatorStack.peek())
                            && operatorGrade(str) <= operatorGrade(operatorStack.peek())) {
                        numberStack.push(operatorStack.pop());
                    }
                    operatorStack.push(str);
                }
            }
        }
        //将剩余的操作符弹出
        while (!operatorStack.isEmpty()) {
            numberStack.push(operatorStack.pop());
        }
        //拼接结果
        while (!numberStack.isEmpty()) {
            sb.append(numberStack.pop() + " ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(" "));
        }
        String[] tmps = sb.toString().split(" ");
        ArrayUtils.reverse(tmps);
        return StringUtils.join(tmps, " ");
    }
}
