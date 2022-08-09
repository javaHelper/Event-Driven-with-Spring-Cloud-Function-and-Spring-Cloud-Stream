package com.example.demo;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@Log4j2
public class Functions {

    @Bean
    public Consumer<String> print() {
        return s -> System.out.println(s);
    }

    @Bean
    public Function<String, String> uppercase() {
        return message -> {
            String toUppercase = message.toUpperCase();
            log.info("Converting {} to uppercase: {}", message, toUppercase);
            return toUppercase;
        };
    }

    @Bean
    public Function<Flux<String>, Flux<String>> reverseReactive() {
        return stringFlux -> stringFlux
                .map(message -> new StringBuilder(message).reverse().toString());
    }

    @Bean
    public Function<Message, String> reverseWithMessage() {
        return message -> {
            final String toReversed = new StringBuilder(message.getContent()).reverse().toString();
            log.info("Reversing {}: {}", message, toReversed);
            return toReversed;
        };
    }

    @Bean
    public Function<String,String> reverse() {
        return message -> {
            String toReversed = new StringBuilder(message).reverse().toString();
            log.info("Reversing {}: {}", message, toReversed);
            return toReversed;
        };
    }
}