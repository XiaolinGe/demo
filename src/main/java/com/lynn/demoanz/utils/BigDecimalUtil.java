package com.lynn.demoanz.utils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

public class BigDecimalUtil {
    public static final int SCALE = 2;

    /** 加 */
    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        return b1.add(b2);
    }

    /** 减 */
    public static BigDecimal subtract(BigDecimal b1, BigDecimal b2) {
        return b1.subtract(b2);
    }

    /** 乘 */
    public static BigDecimal multiply(BigDecimal multiplier, BigDecimal multiplicand) {
        return multiplier.multiply(multiplicand).setScale(SCALE, HALF_UP);
    }

    /** 除 */
    public static BigDecimal divide(BigDecimal b1, BigDecimal divisor) {
        return b1.divide(divisor, SCALE, HALF_UP);
    }

    /** 四舍五入， 保留scale位小数 */
    public static BigDecimal roundHalfUp(BigDecimal b, int scale) {
        return  b.setScale(scale, HALF_UP);
    }

    /** 百分位数 */
    public static BigDecimal percentile(List<BigDecimal> latencies, int percentile) {
        Collections.sort(latencies);
        int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
        return latencies.get(index-1);
    }
}