package com.hqd.calc.graphics;

import com.hqd.calc.CalcFrame;
import com.hqd.calc.CalcPanel;
import com.hqd.calc.CalcTextField;

import javax.swing.*;
import java.awt.*;

public class CalculatorFrame extends JFrame implements CalcFrame {
    public static final int DEFAULT_WIDTH = 340;
    public static final int DEFAULT_HEIGHT = 300;
    public static final int DEFAULT_HGAP = 3;
    public static final int DEFAULT_VGAP = 3;
    protected static final LayoutManager DEFAULT_LAYOUT = new GridLayout(6, 3, DEFAULT_HGAP, DEFAULT_VGAP);
    private String title;
    private int width;
    private int height;
    private Component position;
    private LayoutManager layout;
    private CalcTextField calcTextField;

    public CalculatorFrame(String title) {
        this(title, DEFAULT_WIDTH, DEFAULT_HEIGHT, null, DEFAULT_LAYOUT);
    }

    public CalculatorFrame(String title, int row, int cols) {
        this(title, DEFAULT_WIDTH, DEFAULT_HEIGHT, null, new GridLayout(row, cols, DEFAULT_HGAP, DEFAULT_VGAP));
    }

    public CalculatorFrame(String title, int width, int height, Component position, LayoutManager layout) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.position = position;
        this.layout = layout;
        this.init();
    }

    private void init() {
        setTitle(title);
        setPreferredSize(new Dimension(width, height));
        pack();                                    //设置窗体大小以内容大小决定
        setLocationRelativeTo(position);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(layout);
        setResizable(false);
    }

    public void showFrame() {
        setVisible(true);
    }

    @Override
    public void addPanel(CalcPanel cp) {
        if (cp instanceof Component)
            this.add((Component) cp);
    }

    @Override
    public void setTextField(CalcTextField textField) {
        if (textField instanceof Component) {
            CalcTextField old = this.calcTextField;
            if (old != null) {
                this.remove((Component) old);
            }
            this.calcTextField = textField;
            this.add((Component) this.calcTextField);
        }
    }

    @Override
    public CalcTextField getTextField() {
        return calcTextField;
    }
}
