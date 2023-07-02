package xyz.vaillant.poc.back.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("xyz.vaillant.poc.core.model")
@ComponentScan("xyz.vaillant.poc.back")
public class SpringApplicationWS {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationWS.class, args);
    }

}