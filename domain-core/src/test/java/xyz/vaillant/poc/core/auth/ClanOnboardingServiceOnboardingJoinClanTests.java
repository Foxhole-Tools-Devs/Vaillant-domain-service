package xyz.vaillant.poc.core.auth;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import xyz.vaillant.poc.back.core.config.CoreConfig;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = CoreConfig.class)
public class ClanOnboardingServiceOnboardingJoinClanTests {
}
