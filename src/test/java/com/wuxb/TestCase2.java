package com.wuxb;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BlogApplication.class)
public class TestCase2 {
    @LocalServerPort
    private Integer port;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("beforeAll");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("beforeEach");
    }

    @Test
    @Tag("dev")
    public void devTest() {
        System.out.println("devTest");
//        Assertions.fail("不再执行后面的了");
    }

    @Test
    @Tag("prodTest")
    public void prodTest() {
        System.out.println("prodTest");
    }


    @Disabled // 运行当前类时，不自动执行
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)  // 超时1秒报错
    @RepeatedTest(2) // 重复两次
    @DisplayName("方法名")
    public void repeatedTest() throws InterruptedException {
        Thread.sleep(900);
        System.out.println("test2");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void parameterizedTest(int input) {
        System.out.println(input);
    }

    @Nested
    class Inner01 {
        @Test
        public void test() {
            System.out.println("Inner01");
        }

        @Nested
        class Inner02 {
            @Test
            public void test() {
                System.out.println("Inner02");
            }
        }
    }

    @Disabled
    @ParameterizedTest
//    @MethodSource("initEntityList")
    @MethodSource("com.wuxb.IntergationTest#initEntityList")
    public void parameterizedTest(int index, Entity input) {
        System.out.println(index + "-" + input.toString());
        Assertions.assertEquals(index, 100, "index == 100");
//        assert false : "assert错误";
    }

    @Test
    public void assertThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("my exception");
        });
        System.out.println("ok");
    }

    private static Stream<Arguments> initEntityList() {
        return Stream.of(
            Arguments.of(100, new Entity("name", "ben")),
            Arguments.of(101, new Entity("age", "18"))
        );
    }

    @Data
    @AllArgsConstructor
    static class Entity {
        private String key;
        private String value;
    }
}
