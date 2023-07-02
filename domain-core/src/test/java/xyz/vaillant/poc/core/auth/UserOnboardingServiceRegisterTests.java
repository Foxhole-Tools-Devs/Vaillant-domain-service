package xyz.vaillant.poc.core.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import xyz.vaillant.poc.back.core.config.CoreConfig;
import xyz.vaillant.poc.back.core.exception.parameter.ParameterErrorType;
import xyz.vaillant.poc.back.core.exception.parameter.ParameterException;
import xyz.vaillant.poc.back.core.model.user.User;
import xyz.vaillant.poc.back.core.model.user.OnboardingState;
import xyz.vaillant.poc.back.core.repository.UserRepository;
import xyz.vaillant.poc.back.core.service.onboarding.UserOnboardingService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = CoreConfig.class)
 public class UserOnboardingServiceRegisterTests {
    private User unregisteredUser;
    private final String UNREGISTER_USER_DISCORD_ID = "42";
    private final String UNREGISTER_USER_MAIL = "mock.unregistered@mail.net";

    private User registeredUser;
    private final String REGISTERED_USER_DISCORD_ID = "13";

    private User savedUser;
    private final Long SAVED_USER_ID = 22L;
    private final String SAVED_USER_EMAIL = "mock.saved@mail.net";

    private User createdUser;
    private final Long CREATED_USER_ID = 56L;


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
    void contextLoad() {}

    @Test
    void register_a_user_without_discord_id_should_throw_an_missing_parameter_exception() {
        User user = new User();
        user.setDiscordId(null);
        assertThrows( ParameterException.class , () -> sut.registerUser(user));
    }

    @Test
    void register_a_user_with_a_registered_discord_id_should_throw_an_bad_parameter_exception() {
        try {
            sut.registerUser(registeredUser);
        } catch (ParameterException e) {
            assertEquals(e.getParameterErrors().size(), 1);
            assertEquals(e.getParameterErrors().get(0).getParameterErrorType(), ParameterErrorType.BAD_VALUE);
        }
    }

    @Test
    void register_a_user_without_a_discord_id_shouldn_t_throw_exception() {
        assertDoesNotThrow(() -> sut.registerUser(unregisteredUser));
    }

    @Test
    void a_user_not_register_has_an_unregister_state() {
        User user = new User();
        assertEquals(user.getOnboardingState(), OnboardingState.NOT_REGISTER);
    }

    @Test
    void a_registered_user_has_a_registered_state() {
        assertEquals(savedUser.getOnboardingState(), OnboardingState.REGISTERED);
    }

    @Test
    void a_user_not_registered_after_registration_had_id_and_registered_status() {
        User user = sut.registerUser(unregisteredUser);
        assertEquals(user.getOnboardingState(), OnboardingState.REGISTERED);
        assertNotNull(user.getId());
    }

    private void initMockedComponents() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        Mockito.when(userRepositoryMock.findByDiscordId(UNREGISTER_USER_DISCORD_ID)).thenReturn(Optional.empty());
        Mockito.when(userRepositoryMock.findByDiscordId(REGISTERED_USER_DISCORD_ID)).thenReturn(Optional.of(savedUser));
        Mockito.when(userRepositoryMock.save(unregisteredUser)).then(invocationOnMock -> {
            unregisteredUser.setId(CREATED_USER_ID);
            return unregisteredUser;
        });
    }

    private void initMockedData() {
        unregisteredUser = new User();
        unregisteredUser.setDiscordId(UNREGISTER_USER_DISCORD_ID);
        unregisteredUser.setEmail(UNREGISTER_USER_MAIL);

        registeredUser = new User();
        registeredUser.setDiscordId(REGISTERED_USER_DISCORD_ID);

        savedUser = new User();
        savedUser.setId(SAVED_USER_ID);
        savedUser.setDiscordId(REGISTERED_USER_DISCORD_ID);
        savedUser.setEmail(SAVED_USER_EMAIL);
        savedUser.setOnboardingState(OnboardingState.REGISTERED);

        createdUser = new User();
        createdUser.setId(CREATED_USER_ID);
        createdUser.setDiscordId(UNREGISTER_USER_DISCORD_ID);
        createdUser.setEmail(UNREGISTER_USER_MAIL);
        createdUser.setOnboardingState(OnboardingState.REGISTERED);
    }


}
