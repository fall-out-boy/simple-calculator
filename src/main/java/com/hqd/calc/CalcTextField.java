package com.hqd.calc;

import java.util.Stack;

/**
 * 输入框接口
 */
public interface CalcTextField {
    /**
     * 清除输入
     *
     * @return
     */
    String clear();

    /**
     * 删除输入
     *
     * @return
     */
    String del();

    /**
     * 设置内容
     *
     * @param t
     */
    void setText(String t);

    /**
     * 获取文本
     *
     * @return
     */
    String getText();

    /**
     * 获取历史输入
     *
     * @return
     */
    Stack<String> getHistoryInput();

    /**
     * 添加历史输入
     *
     * @param input
     */
    void addHistoryInput(String input);

    /**
     * 清除历史输入
     */
    void clearHisHistory();

    /**
     * 设置默认显示
     *
     * @param defaultText
     */
    void setDefaultShow(String defaultText);

    /**
     * 获取默认显示
     *
     * @return
     */
    String getDefaultShow();
}
