package com.petsinmind;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PetsInMindApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetsInMindApplication.class, args);
    }
    @Bean
public CommandLineRunner debugContext(ApplicationContext ctx) {
    return args -> {
        System.out.println("Loaded Beans:");
        for (String bean : ctx.getBeanDefinitionNames()) {
            if (bean.toLowerCase().contains("joboffer")) {
                System.out.println("✅ FOUND: " + bean);
            }
        }
    };
}

}
