package com.hqd.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Stack;

public class JFrameTest {
    public static void main(String[] args) {
        test();
        //System.out.println(NumberUtils.isParsable("123.0"));
    }

    public static void test() {
        CalculatorFrame frame = CalculatorFrameFactory.create("简单的计算器");
        frame.showFrame();
    }
}

class CalculatorPanel extends JPanel {
    protected static final LayoutManager DEFAULT_LAYOUT = new GridLayout(1, 4, 3, 3);
    //创建pan1面板容器，指定为表格布局，1*4，水平垂直间距为3
    private LayoutManager layoutManager;

    public CalculatorPanel(LayoutManager layoutManager) {
        super(layoutManager);
        this.layoutManager = layoutManager;
    }

    public CalculatorPanel() {
        this(DEFAULT_LAYOUT);
    }


    public void addButtons(ButtonInfo[] buttonInfos, InputTextField textField) {
        for (ButtonInfo info : buttonInfos) {
            this.add(ButtonFactory.createButton(info, textField)); //把数组中的按钮添加到pan4容器中
        }
    }
}

enum ButtonType {
    NUMBER, OTHER, OPERATION
}

class ButtonFactory {
    public static AbstractButton createButton(ButtonInfo info, InputTextField textField) {
        if (info != null) {
            switch (info.getType()) {
                case NUMBER:
                    return new NumberButton(info, textField);
                case OPERATION:
                    return new OperationButton(info, textField);
                case OTHER:
                    return new OtherButton(info, textField);
                default:
                    return null;
            }
        }
        return null;
    }
}

class CalcEngine {
    public static BigDecimal calcResult(BigDecimal num1, BigDecimal num2, char operator) {
        BigDecimal result = new BigDecimal(0);
        switch (operator) {
            case '+':
                result = num1.add(num2);
                break;
            case '-':
                result = num2.subtract(num1);
                break;
            case '×':
                result = num1.multiply(num2);
                break;
            case '÷':
                result = num2.divide(num1, 10, BigDecimal.ROUND_HALF_UP);
                break;
            default:
                break;
        }
        return result;
    }

    public static BigDecimal suffixCalc(String calcStr) {
        Stack<BigDecimal> numberStack = new Stack<>();
        String[] strs = calcStr.split(" ");
        for (int i = 0; i < strs.length; i++) {
            if (isNumber(strs[i])) {
                numberStack.push(new BigDecimal(strs[i]));
            } else {
                if (numberStack.size() >= 2) {
                    BigDecimal num1 = numberStack.pop();
                    BigDecimal num2 = numberStack.pop();
                    numberStack.push(calcResult(num1, num2, strs[i].charAt(0)));
                }

            }
        }
        return numberStack.peek();
    }

    private static String revise(String calcStr) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < calcStr.length(); i++) {
            char c = calcStr.charAt(i);
            if (c == '.') {
                boolean isAppend = false;
                int index = i - 1;
                if (index < 0 || !isNumber(calcStr.charAt(index))) {
                    sb.append('0');
                    sb.append(c);
                    isAppend = true;
                }
                index = i + 1;
                if (index >= calcStr.length() || !isNumber(calcStr.charAt(index))) {
                    sb.append(c);
                    sb.append('0');
                    isAppend = true;
                }
                if (!isAppend) {
                    sb.append(c);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String infix2Suffix(String calcStr) {
        StringBuilder sb = new StringBuilder("");
        calcStr = revise(calcStr);
        Stack<Character> operatorStack = new Stack<>();
        Stack<String> numberStack = new Stack<>();
        for (int i = 0; i < calcStr.length(); i++) {
            char ch = calcStr.charAt(i);
            if (isNumber(ch)) {
                String numberStr = getNumber(calcStr, i).toString();
                numberStack.push(numberStr);
                i = numberStr.length() - 1 + i;
            } else {
                if (isNegative(i, calcStr)) {
                    String numberStr = getNumber(calcStr, i).toString();
                    numberStack.push(numberStr);
                    i = numberStr.length() - 1 + i;
                } else if (ch == '%') {
                    BigDecimal bigDecimal = new BigDecimal(numberStack.pop());
                    numberStack.push(bigDecimal.divide(BigDecimal.valueOf(100)).toString());
                } else {
                    if (ch == '(' || operatorStack.isEmpty()) {
                        operatorStack.push(ch);
                    } else if (ch == ')') {
                        char operator = operatorStack.pop();
                        while (operator != '(' && !operatorStack.isEmpty()) {
                            numberStack.push(String.valueOf(operator));
                            operator = operatorStack.pop();
                        }
                    } else {
                        while (!operatorStack.isEmpty() && operatorStack.peek() != '('
                                && operatorGrade(ch) <= operatorGrade(operatorStack.peek())) {
                            numberStack.push(String.valueOf(operatorStack.pop()));
                        }
                        operatorStack.push(ch);
                    }
                }
            }
        }
        while (!operatorStack.isEmpty()) {
            numberStack.push(String.valueOf(operatorStack.pop()));
        }
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

    public static boolean isNegative(int start, String str) {
        int index = start - 1;
        char ch = str.charAt(start);
        if (ch != '-') {
            return false;
        }
        if (index < 0) {
            return true;
        }
        ch = str.charAt(index);
        if (isNumber(ch) || ch == ')' || ch == '%')
            return false;
        return true;
    }

    /**
     * 运算符优先级
     *
     * @param operator
     * @return
     */
    private static int operatorGrade(char operator) {
        if (operator == '×' || operator == '÷') {
            return 1;
        } else if (operator == '-' || operator == '+') {
            return 0;
        } else if (operator == '(' || operator == ')') {
            return 2;
        } else {
            return 3;
        }
    }

    private static boolean isNumber(String str) {
        try {
            BigDecimal bd = new BigDecimal(str);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    private static boolean isNumber(char c) {
        return (c >= '0' && c <= '9') ? true : false;
    }

    private static BigDecimal getNumber(String str, int start) {
        if (isOutIndex(start, str) || StringUtils.isBlank(str)) {
            return new BigDecimal(0);
        }
        StringBuilder sb = new StringBuilder();
        if (str.charAt(start) == '-') {
            sb.append('-');
            start++;
        }
        for (int i = start; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isNumber(c) || c == '.') {
                sb.append(str.charAt(i));
            } else {
                break;
            }
        }
        return new BigDecimal(sb.toString());
    }

    private static boolean isOutIndex(int index, String str) {
        if (index < 0 || index >= str.length())
            return true;
        return false;
    }
}

@Data
@AllArgsConstructor
class ButtonInfo {
    private String text;
    private ButtonType type;
}

abstract class AbstractButton extends JButton implements ActionListener {
    protected InputTextField textField;
    protected ButtonInfo buttonInfo;

    public AbstractButton(ButtonInfo buttonInfo, InputTextField textField) {
        super(buttonInfo.getText());
        this.buttonInfo = buttonInfo;
        this.textField = textField;
        super.addActionListener(this);
    }

    public ButtonInfo getButtonInfo() {
        return buttonInfo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        doAction(e);
    }

    protected void doAction(ActionEvent e) {
        if (this.textField != null) {
            this.textField.setText(this);
        }
    }
}

class NumberButton extends AbstractButton {
    public static final String BUTTON_0 = "0";
    public static final String BUTTON_1 = "1";
    public static final String BUTTON_2 = "2";
    public static final String BUTTON_3 = "3";
    public static final String BUTTON_4 = "4";
    public static final String BUTTON_5 = "5";
    public static final String BUTTON_6 = "6";
    public static final String BUTTON_7 = "7";
    public static final String BUTTON_8 = "8";
    public static final String BUTTON_9 = "9";
    public static final String BUTTON_DECIMAL = ".";
    //负号，这里做特殊的数字，用于取反用
    public static final String BUTTON_NEGATIVE = "-";
    //左括号，这里做特殊的数字，用于取反用
    public static final String BUTTON_LEFT_BRACKET = "(";
    //右括号，这里做特殊的数字，用于取反用
    public static final String BUTTON_RIGHT_BRACKET = ")";
    //百分号，这里做特殊的数字，用于百分数
    public static final String BUTTON_PERCENT = "%";

    public NumberButton(ButtonInfo buttonInfo, InputTextField textField) {
        super(buttonInfo, textField);
    }

    @Override
    protected void doAction(ActionEvent e) {
        String com = e.getActionCommand();
        String oldText = textField.getText();
        String newText = oldText;
        switch (com) {
            case BUTTON_DECIMAL: {
                if (hasDecimal()) {
                    return;
                } else {
                    newText += com;
                    textField.getHistoryInput().push(this.buttonInfo);
                    break;
                }
            }
            case BUTTON_PERCENT: {
                newText += BUTTON_PERCENT;
                textField.getHistoryInput().push(this.buttonInfo);
                break;
            }
            default: {
                Stack<ButtonInfo> stack = textField.getHistoryInput();
                if (!stack.isEmpty()) {
                    ButtonInfo top = stack.peek();
                    if (top.getText().equals(BUTTON_PERCENT)) {
                        return;
                    }
                }
                if (oldText.startsWith(InputTextField.DEFAULT_SHOW) && stack.isEmpty()) {
                    newText = com;
                    stack.push(this.buttonInfo);
                } else {
                    StringBuilder numberSb = new StringBuilder();
                    while (!stack.isEmpty()) {
                        ButtonInfo bi = stack.pop();
                        if (bi.getType() == ButtonType.OPERATION) {
                            stack.push(bi);
                            break;
                        }
                        numberSb.append(bi.getText());
                    }
                    newText = oldText.substring(0, (oldText.length() - numberSb.length()));
                    numberSb = numberSb.reverse();
                    numberSb.append(com);
                    numberSb = new StringBuilder(new BigDecimal(numberSb.toString()).toString());
                    for (int i = 0; i < numberSb.length(); i++) {
                        stack.push(new ButtonInfo(String.valueOf(numberSb.charAt(i)), ButtonType.NUMBER));
                    }
                    newText += numberSb.toString();
                }
                break;
            }
        }

        textField.setText(this, newText);
    }

    private boolean hasDecimal() {
        Stack<ButtonInfo> historyInput = textField.getHistoryInput();
        boolean hasDecimal = false;
        for (int i = historyInput.size() - 1; i >= 0; i--) {
            ButtonInfo bi = historyInput.elementAt(i);
            if (bi.getType() == ButtonType.OPERATION)
                break;
            if (bi.getText().equals(NumberButton.BUTTON_DECIMAL)) {
                hasDecimal = true;
                break;
            }
        }
        return hasDecimal;
    }
}

class OperationButton extends AbstractButton {
    public static final String BUTTON_PM = "±";
    public static final String BUTTON_DIV = "÷";
    public static final String BUTTON_MUL = "×";
    public static final String BUTTON_PAH = "%";
    public static final String BUTTON_ADD = "+";
    public static final String BUTTON_SUB = "-";
    public static final String BUTTON_EQ = "=";

    public OperationButton(ButtonInfo buttonInfo, InputTextField textField) {
        super(buttonInfo, textField);
    }

    @Override
    protected void doAction(ActionEvent e) {
        String com = e.getActionCommand();
        String oldText = textField.getText();
        String newText = oldText;
        switch (com) {
            case BUTTON_EQ: {
                String str = CalcEngine.infix2Suffix(textField.getText());
                BigDecimal result = CalcEngine.suffixCalc(str);
                System.out.println("中缀表达式:" + textField.getText() + " 逆波兰表达式:" + str);
                textField.clear();
                result.setScale(10, BigDecimal.ROUND_HALF_UP);
                newText = result.toString();
                textField.getHistoryInput().push(this.getButtonInfo());
                boolean isNegate = false;
                if (result.compareTo(new BigDecimal(0)) == -1) {
                    isNegate = true;
                    textField.getHistoryInput().push(new ButtonInfo(NumberButton.BUTTON_LEFT_BRACKET, ButtonType.NUMBER));
                }
                for (int i = 0; i < newText.length(); i++) {
                    textField.getHistoryInput().push(new ButtonInfo(String.valueOf(newText.charAt(i)), ButtonType.NUMBER));
                }
                if (isNegate) {
                    textField.getHistoryInput().push(new ButtonInfo(NumberButton.BUTTON_RIGHT_BRACKET, ButtonType.NUMBER));
                }
                break;
            }
            case BUTTON_PM: {
                Stack<ButtonInfo> history = textField.getHistoryInput();
                StringBuilder numberSb = new StringBuilder();
                while (!history.isEmpty()) {
                    ButtonInfo bi = history.pop();
                    if (bi.getType() == ButtonType.OPERATION) {
                        history.push(bi);
                        break;
                    }
                    numberSb.append(bi.getText());
                }
                if (StringUtils.isNotBlank(numberSb)) {
                    newText = newText.substring(0, newText.length() - numberSb.length());
                    numberSb = numberSb.reverse();
                    int index = numberSb.indexOf(NumberButton.BUTTON_LEFT_BRACKET);
                    if (index != -1)
                        numberSb.deleteCharAt(index);
                    index = numberSb.indexOf(NumberButton.BUTTON_RIGHT_BRACKET);
                    if (index != -1)
                        numberSb.deleteCharAt(index);
                    BigDecimal bd = new BigDecimal(numberSb.toString());
                    bd = bd.negate();
                    numberSb = new StringBuilder(bd.toString());
                    boolean isNegative = false;
                    if (bd.compareTo(new BigDecimal(0)) == -1) {
                        isNegative = true;
                        history.push(new ButtonInfo(NumberButton.BUTTON_LEFT_BRACKET, ButtonType.OTHER));
                        newText += NumberButton.BUTTON_LEFT_BRACKET;
                    }
                    for (int i = 0; i < numberSb.length(); i++) {
                        history.push(new ButtonInfo(String.valueOf(numberSb.charAt(i)), ButtonType.NUMBER));
                    }
                    newText += numberSb.toString();
                    if (isNegative) {
                        history.push(new ButtonInfo(NumberButton.BUTTON_RIGHT_BRACKET, ButtonType.OTHER));
                        newText += NumberButton.BUTTON_RIGHT_BRACKET;
                    }
                    break;
                } else {
                    return;
                }
            }
            default: {
                ButtonInfo bi = textField.getHistoryInput().peek();
                if (bi.getType() == ButtonType.OPERATION) {
                    newText = oldText.substring(0, oldText.length() - 1) + com;
                } else {
                    newText += com;
                }
                textField.getHistoryInput().push(this.getButtonInfo());
                break;
            }
        }
        textField.setText(this, newText);
    }
}

class OtherButton extends AbstractButton {
    public static final String BUTTON_AC = "AC";
    public static final String BUTTON_DEL = "DEL";

    public OtherButton(ButtonInfo buttonInfo, InputTextField textField) {
        super(buttonInfo, textField);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        switch (com) {
            case BUTTON_AC: {
                textField.clear();
                break;
            }
            case BUTTON_DEL: {
                textField.del();
                break;
            }
        }
    }
}

class InputTextField extends JTextField {
    public static final String DEFAULT_SHOW = "0";
    protected Stack<ButtonInfo> historyInput = new Stack<>();

    public InputTextField() {
        super.setText(DEFAULT_SHOW);
        setHorizontalAlignment(JTextField.RIGHT);
    }

    public Stack<ButtonInfo> getHistoryInput() {
        return historyInput;
    }

    public String clear() {
        String val = getText();
        setText(DEFAULT_SHOW);
        historyInput.clear();
        return val;
    }

    public String del() {
        String val = getText();
        String delVal = "";
        if (StringUtils.isNotBlank(val)) {
            if (val.length() == 1) {
                clear();
                delVal = val;
            } else {
                delVal = val.substring(val.length() - 1);
                val = val.substring(0, val.length() - 1);
                super.setText(val);
            }
            if (!historyInput.isEmpty())
                historyInput.pop();
        }
        return delVal;
    }

    public void setText(AbstractButton button, String newText) {
        setText(newText);
    }

    public void setText(AbstractButton button) {
        this.setText(button, "");
    }
}

class CalculatorFrameFactory {
    public static CalculatorFrame create() {
        return create("");
    }

    public static CalculatorFrame create(String title) {
        CalculatorFrame frame = new CalculatorFrame(title);                      //创建一个窗体标题
        CalculatorPanel pan0 = new CalculatorPanel();          //创建pan1面板容器，指定为表格布局，1*4，水平垂直间距为3
        CalculatorPanel pan1 = new CalculatorPanel();          //创建pan1面板容器，指定为表格布局，1*4，水平垂直间距为3
        CalculatorPanel pan2 = new CalculatorPanel();          //创建pan2面板容器，指定为表格布局，1*4，水平垂直间距为3
        CalculatorPanel pan3 = new CalculatorPanel();          //创建pan3面板容器，指定为表格布局，1*4，水平垂直间距为3
        CalculatorPanel pan4 = new CalculatorPanel();          //创建pan4面板容器，指定为表格布局，1*4，水平垂直间距为3
        InputTextField h1 = new InputTextField();                          //创建一个单行文本框h1
        frame.add(h1);                                            //添加单行文本框到窗体

        ButtonInfo[] b0 = {
                new ButtonInfo(OtherButton.BUTTON_AC, ButtonType.OTHER),
                new ButtonInfo(OtherButton.BUTTON_DEL, ButtonType.OTHER),
                new ButtonInfo(OperationButton.BUTTON_PM, ButtonType.OPERATION),
                new ButtonInfo(OperationButton.BUTTON_DIV, ButtonType.OPERATION)
        };
        pan0.addButtons(b0, h1);

        ButtonInfo[] b1 = {
                new ButtonInfo(NumberButton.BUTTON_7, ButtonType.NUMBER),
                new ButtonInfo(NumberButton.BUTTON_8, ButtonType.NUMBER),
                new ButtonInfo(NumberButton.BUTTON_9, ButtonType.NUMBER),
                new ButtonInfo(OperationButton.BUTTON_MUL, ButtonType.OPERATION)};
        pan1.addButtons(b1, h1);

        ButtonInfo[] b2 = {
                new ButtonInfo(NumberButton.BUTTON_4, ButtonType.NUMBER),
                new ButtonInfo(NumberButton.BUTTON_5, ButtonType.NUMBER),
                new ButtonInfo(NumberButton.BUTTON_6, ButtonType.NUMBER),
                new ButtonInfo(OperationButton.BUTTON_SUB, ButtonType.OPERATION)};
        pan2.addButtons(b2, h1);


        ButtonInfo[] b3 = {
                new ButtonInfo(NumberButton.BUTTON_1, ButtonType.NUMBER),
                new ButtonInfo(NumberButton.BUTTON_2, ButtonType.NUMBER),
                new ButtonInfo(NumberButton.BUTTON_3, ButtonType.NUMBER),
                new ButtonInfo(OperationButton.BUTTON_ADD, ButtonType.OPERATION)};
        pan3.addButtons(b3, h1);

        ButtonInfo[] b4 = {
                new ButtonInfo(OperationButton.BUTTON_PAH, ButtonType.NUMBER),
                new ButtonInfo(NumberButton.BUTTON_0, ButtonType.NUMBER),
                new ButtonInfo(NumberButton.BUTTON_DECIMAL, ButtonType.NUMBER),
                new ButtonInfo(OperationButton.BUTTON_EQ, ButtonType.OPERATION)};
        pan4.addButtons(b4, h1);

        frame.add(pan0);                                //添加pan0容器到窗体中
        frame.add(pan1);                                //添加pan1容器到窗体中
        frame.add(pan2);                               //添加pan2容器到窗体中
        frame.add(pan3);                              //添加pan3容器到窗体中
        frame.add(pan4);                             //添加pan4容器到窗体中
        return frame;
    }

}

/**
 * 计算器的frame
 */
class CalculatorFrame extends JFrame {
    protected static final int DEFAULT_WIDTH = 340;
    protected static final int DEFAULT_HEIGHT = 300;
    protected static final LayoutManager DEFAULT_LAYOUT = new GridLayout(6, 3, 3, 3);
    private String title;
    private int width;
    private int height;
    private Component position;
    private LayoutManager layout;

    public CalculatorFrame(String title) {
        this(title, DEFAULT_WIDTH, DEFAULT_HEIGHT, null, DEFAULT_LAYOUT);
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
    }

    public void showFrame() {
        setVisible(true);
    }
}