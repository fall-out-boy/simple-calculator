package com.hqd.calc;

/**
 * 计算器frame接口
 */
public interface CalcFrame {
    /**
     * 添加面板
     *
     * @param cp
     */
    void addPanel(CalcPanel cp);

    /**
     * 显示
     */
    void showFrame();

    /**
     * 添加输入框
     *
     * @param textField
     */
    void setTextField(CalcTextField textField);

    /**
     * 获取输入框
     *
     * @return
     */
    CalcTextField getTextField();
}
