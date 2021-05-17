package com.hqd.calc.syntax;

import com.hqd.calc.SyntaxRule;
import com.hqd.calc.exception.SyntaxException;
import com.hqd.calc.utils.NumberUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 语法检查默认实现
 */
public class DefaultSyntaxRule implements SyntaxRule {
    //数字正则
    protected String numberPattern = "((0?\\.\\d*%*)|([1-9]\\d*\\.?\\d*%*)|(\\(-([1-9]\\d*)*\\.?\\d*%*\\))|\\d)";
    //操作符正则
    protected String operatorPattern = "([\\+,\\-,\\×,\\÷])";
    protected Pattern np = Pattern.compile(numberPattern);
    protected Pattern op = Pattern.compile(operatorPattern);

    protected List<String> expressionList = new LinkedList<>();
    //是否开启自动修正
    protected boolean autoRevise;

    public DefaultSyntaxRule(boolean autoRevise) {
        this.autoRevise = autoRevise;
    }

    public DefaultSyntaxRule() {
        this(false);
    }

    @Override
    public String checkSyntax(String expression) throws SyntaxException {
        expressionList.clear();

        String reviseStr = expression;
        if (StringUtils.isBlank(reviseStr))
            return reviseStr;

        if (!check(reviseStr)) {
            throw new SyntaxException("表达式有误:" + reviseStr);
        }
        if (autoRevise)
            reviseStr = revise(expression);
        return reviseStr;
    }

    private boolean check(String str) {
        Matcher nm = np.matcher(str);
        Matcher om = op.matcher(str);
        int len = 0;
        int strLength = str.length();
        int index = 0;
        String matchStr = null;
        while (index < strLength && nm.find(index)) {
            //匹配数字
            matchStr = nm.group();
            len += matchStr.length();
            index += matchStr.length();
            //在匹配操作符
            if (index < strLength && om.find(index)) {
                matchStr = om.group();
                index = str.indexOf(matchStr, index);
                len += matchStr.length();
            } else
                break;
        }
        if (len != strLength) {
            return false;
        }
        return true;
    }

    /**
     * 修正小数点，前置缺失则补0，后置缺失则加0
     * eg: .3 -> 0.3，4. -> 4.0
     *
     * @param str
     * @return
     */
    protected String reviseDecimalNumber(String str) {
        if (NumberUtil.isDecimalNumber(str)) {
            int index = str.indexOf(NumberUtil.SPECIAL_NUMBER_DECIMAL);
            int prev = index - 1;
            int next = index + 1;
            if (prev < 0) {
                str = NumberUtil.NUMBER_0 + str;
            } else if (next >= str.length()) {
                str = str + NumberUtil.NUMBER_0;
            }
        }
        return str;
    }

    /**
     * 修正%,将%转成小数
     * eg: 2% -> 0.02 ,2%% -> 0.0002 以此类推
     *
     * @param str
     * @return
     */
    protected String revisePercentNumber(String str) {
        int index = str.indexOf(NumberUtil.SPECIAL_NUMBER_PERCENT);
        if (index != -1) {
            String tmp = str.substring(0, index);
            String percentStr = str.substring(index);
            BigDecimal bd = NumberUtil.createBigDecimal(tmp);
            bd = bd.divide(new BigDecimal(100).pow(percentStr.length()));
            return bd.toString();
        }
        return str;
    }

    /**
     * 修正负数
     * eg: (-9) -> -9
     *
     * @param str
     * @return
     */
    protected String reviseMinusNumber(String str) {
        if (str.startsWith(NumberUtil.SPECIAL_NUMBER_LEFT_BRACKET)) {
            str = NumberUtil.getNumberStr(0, str);
        }
        return str;
    }

    /**
     * 修正输入的数学表达式
     *
     * @param expression
     * @return
     */
    protected String revise(String expression) {

        Matcher nm = np.matcher(expression);
        Matcher om = op.matcher(expression);
        int start = 0;
        StringBuilder sb = new StringBuilder("");
        System.out.println("====================分割开始========================");
        while (nm.find()) {
            String str = nm.group();
            start += str.length();
            str = reviseMinusNumber(str);
            str = reviseDecimalNumber(str);
            str = revisePercentNumber(str);
            sb.append(str);
            expressionList.add(str);
            if (om.find(start)) {
                String operatorStr = om.group();
                expressionList.add(operatorStr);
                sb.append(operatorStr);
                start += operatorStr.length();
            }
        }
        System.out.println(sb.toString());
        System.out.println("====================分割结束========================");
        return sb.toString();
    }

    @Override
    public Collection<String> getExpressions() {
        return expressionList;
    }

    public String getNumberPattern() {
        return numberPattern;
    }

    public void setNumberPattern(String numberPattern) {
        this.numberPattern = numberPattern;
    }

    public String getOperatorPattern() {
        return operatorPattern;
    }

    public void setOperatorPattern(String operatorPattern) {
        this.operatorPattern = operatorPattern;
    }
}
