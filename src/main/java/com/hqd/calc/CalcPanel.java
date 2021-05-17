package com.hqd.calc;

import com.hqd.calc.graphics.ButtonManager;

/**
 * 面板接口
 */
public interface CalcPanel {
    /**
     * 添加按钮
     *
     * @param cbs
     */
    void addButtons(CalcButton[] cbs);

    /**
     * 获取按钮管理器
     *
     * @return
     */
    ButtonManager getButtonManager();
}
