package xyz.vaillant.poc.back.core.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@EnableTransactionManagement
@EntityScan("xyz.vaillant.poc.core.model")
@EnableJpaRepositories("xyz.vaillant.poc.core.repository")
@ComponentScan("xyz.vaillant.poc")
public class CoreConfig {

}
