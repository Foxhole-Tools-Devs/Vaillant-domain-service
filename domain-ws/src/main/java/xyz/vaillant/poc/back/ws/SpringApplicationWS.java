package xyz.vaillant.poc.back.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("xyz.vaillant.poc")
@SpringBootApplication
public class SpringApplicationWS {

    public static void main(String[] args) {
        SpringApplication.run(SpringApplicationWS.class, args);
    }

}