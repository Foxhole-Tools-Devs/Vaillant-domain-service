package xyz.vaillant.poc.back.ws.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan("xyz.vaillant.poc.back.core.model")
@ComponentScan("xyz.vaillant.poc.back")
public class DomainConfig {
}
