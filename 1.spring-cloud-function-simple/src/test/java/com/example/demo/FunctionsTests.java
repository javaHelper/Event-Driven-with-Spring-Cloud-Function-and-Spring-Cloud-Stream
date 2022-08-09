package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionsTests {
    private Functions functions = new Functions();

    @Test
    void testUppercase(){
        String input = "Spring Cloud";
        String expectedOutput = "SPRING CLOUD";

        final String actualOutput = functions.uppercase().apply(input);
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    void testReverse() {
        String input = "Spring Cloud";
        String expectedOutput = "duolC gnirpS";

        String actualOutput = functions.reverse().apply(input);

        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    // call two functions
    @Test
    void testUppercaseThenReversed(){
        String input = "Spring Cloud";
        String expectedOutput = "DUOLC GNIRPS";

        final String actualOutput = functions.uppercase()
                .andThen(functions.reverse())
                .apply(input);
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}