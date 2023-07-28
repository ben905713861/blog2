package com.wuxb;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class AssumptionTest {
    @Test
    public void test0() {
        System.out.println("test0");
    }

    @Test
    public void assumeTrue() {
        Assumptions.assumeTrue(false, "假设不正确");
//        Assertions.assertTrue(false, "断言不正确");
        System.out.println("ok");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }
}
