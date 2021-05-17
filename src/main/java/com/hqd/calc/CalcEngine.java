package com.hqd.calc;

import com.hqd.calc.exception.CalcException;

import java.math.BigDecimal;

/**
 * 算法接口，用来实现数学表达式计算
 */
@FunctionalInterface
public interface CalcEngine {
    /**
     * 计算结果
     *
     * @param expression
     * @return
     * @throws CalcException
     */
    BigDecimal calcResult(String expression) throws CalcException;
}
