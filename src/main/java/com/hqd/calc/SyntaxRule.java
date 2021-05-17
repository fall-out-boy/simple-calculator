package com.hqd.calc;

import com.hqd.calc.exception.SyntaxException;

import java.util.Collection;

/**
 * 语法检查接口
 */
public interface SyntaxRule {
    /**
     * 检查语法输入
     *
     * @param expression
     * @return
     * @throws SyntaxException
     */
    String checkSyntax(String expression) throws SyntaxException;

    /**
     * 获取输入列表
     *
     * @return
     */
    Collection<String> getExpressions();

    /**
     * 获取数字正则
     *
     * @return
     */
    String getNumberPattern();

    /**
     * 设置数字正则
     *
     * @param numberPattern
     */
    void setNumberPattern(String numberPattern);

    /**
     * 获取运算正则
     *
     * @return
     */
    String getOperatorPattern();

    /**
     * 设置运算正则
     *
     * @param operatorPattern
     */
    void setOperatorPattern(String operatorPattern);

}
