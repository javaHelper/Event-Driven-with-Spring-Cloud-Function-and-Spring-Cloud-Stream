package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

@FunctionalSpringBootTest
public class CloudFunctionsTests {
    @Autowired
    private FunctionCatalog functionCatalog;

    @Test
    void testUppercase() {
        String input = "Spring Cloud";
        String expectedOutput = "SPRING CLOUD";

        final Function<String, String> uppercaseFunction = functionCatalog.lookup(Functions.class, "uppercase");
        final String actualOutput = uppercaseFunction.apply(input);
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    void testReverse() {
        String input = "Spring Cloud";
        String expectedOutput = "duolC gnirpS";

        Function<String, String> reverseFunction = functionCatalog.lookup(Functions.class, "reverse");
        final String actualOutput = reverseFunction.apply(input);
        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("Function composition with same data type")
    void testUppercaseThenReverse() {
        String input = "Spring Cloud";
        String expectedOutput = "DUOLC GNIRPS";

        Function<String, String> upperThenReverse = functionCatalog.lookup(Function.class, "uppercase|reverse");
        String actualOutput = upperThenReverse.apply(input);

        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("Function composition with different data type")
    void testUppercaseThenReverseWithMessage() {
        String input = "Spring Cloud";
        String expectedOutput = "DUOLC GNIRPS";

        Function<String, String> upperThenReverse = functionCatalog.lookup(Function.class, "uppercase|reverseWithMessage");
        String actualOutput = upperThenReverse.apply(input);

        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("Longer function composition with different data type")
    void testUppercaseThenReverseWithMessageThenReverse() {
        String input = "Spring Cloud";
        String expectedOutput = "SPRING CLOUD";

        Function<String, String> upperThenReverse = functionCatalog.lookup(Function.class, "uppercase|reverseWithMessage|reverse");
        String actualOutput = upperThenReverse.apply(input);

        Assertions.assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("Function composition with both imperative and reactive types")
    void testUppercaseThenReverseReactive() {
        String input = "Spring Cloud";
        String expectedOutput = "DUOLC GNIRPS";

        Function<Flux<String>, Flux<String>> upperThenReverse = functionCatalog.lookup(Function.class, "uppercase|reverseReactive");

        StepVerifier.create(upperThenReverse.apply(Flux.just(input)))
                .expectNextMatches(actualOuput -> actualOuput.equals(expectedOutput))
                .verifyComplete();
    }
}