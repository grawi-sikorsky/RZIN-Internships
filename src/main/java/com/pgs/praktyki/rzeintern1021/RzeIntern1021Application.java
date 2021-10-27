package com.pgs.praktyki.rzeintern1021;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class})
public class RzeIntern1021Application {

    public static void main(String[] args) {
        SpringApplication.run(RzeIntern1021Application.class, args);
    }

}
