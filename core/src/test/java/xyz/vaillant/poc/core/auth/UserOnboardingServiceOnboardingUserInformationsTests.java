package xyz.vaillant.poc.core.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import xyz.vaillant.poc.core.config.CoreConfig;
import xyz.vaillant.poc.core.exception.parameter.ParameterException;
import xyz.vaillant.poc.core.model.user.OnboardingState;
import xyz.vaillant.poc.core.model.user.User;
import xyz.vaillant.poc.core.repository.UserRepository;
import xyz.vaillant.poc.core.service.onboarding.UserOnboardingService;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = CoreConfig.class)
public class UserOnboardingServiceOnboardingUserInformationsTests {

    private User unregisteredUser;
    private final String UNREGISTER_USER_DISCORD_ID = "42";
    private final String UNREGISTER_USER_MAIL = "mock.unregistered@mail.net";

    private User registeredUser;
    private final String REGISTERED_USER_DISCORD_ID = "13";
    private final String REGISTERED_USERNAME = "john";


    private UserOnboardingService sut;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        initMockedData();
        initMockedComponents();

        sut = new UserOnboardingService(userRepositoryMock);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void a_user_not_registered_can_t_start_an_onboarding_and_throw_an_exception() {
        assertThrows(IllegalStateException.class, () -> sut.onboardingUserInformation(unregisteredUser));
    }

    @Test
    void a_register_user_after_user_onboarding_change_state_to_onboarding_user() {
        var user = sut.onboardingUserInformation(registeredUser);
        assertEquals(user.getOnboardingState(), registeredUser.getOnboardingState());
    }

    @Test
    void a_register_user_need_for_onboarding_an_username() {
        assertDoesNotThrow(() -> {
            sut.onboardingUserInformation(registeredUser);
            Mockito.verify(userRepositoryMock).save(registeredUser);
        });
    }

    @Test
    void a_register_user_without_an_username_throw_an_invalid_param_exception() {
        registeredUser.setUsername(null);
        assertThrows(ParameterException.class, () -> sut.onboardingUserInformation(registeredUser));
    }

    private void initMockedComponents() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        registeredUser.setOnboardingState(OnboardingState.REGISTERED);
        Mockito.when(userRepositoryMock.save(registeredUser)).then(invocationOnMock -> {
            var user = new User();
            user.setOnboardingState(OnboardingState.USER_INFORMATION_DONE);
            user.setId(42L);
            return user;
        });
    }

    private void initMockedData() {
        unregisteredUser = new User();
        unregisteredUser.setDiscordId(UNREGISTER_USER_DISCORD_ID);
        unregisteredUser.setEmail(UNREGISTER_USER_MAIL);
        unregisteredUser.setOnboardingState(OnboardingState.NOT_REGISTER);

        registeredUser = new User();
        registeredUser.setDiscordId(REGISTERED_USER_DISCORD_ID);
        registeredUser.setOnboardingState(OnboardingState.REGISTERED);
        registeredUser.setUsername(REGISTERED_USERNAME);
    }

}
