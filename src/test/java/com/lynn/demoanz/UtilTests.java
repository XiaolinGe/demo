package com.lynn.demoanz;

import com.lynn.demoanz.utils.BigDecimalUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class UtilTests {

    @Test
    public void testBigDecimalMultiply() {
        //given
        var b1 = new BigDecimal("1.11");
        var b2 = new BigDecimal("2.22");

        //when
        var result = BigDecimalUtil.multiply(b1, b2);

        //then
        Assertions.assertThat(result).isEqualTo(new BigDecimal("2.46")); // 1.11 * 2.22 = 2.4642

    }

}
