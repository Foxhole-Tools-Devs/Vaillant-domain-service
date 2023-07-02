package xyz.vaillant.poc.core.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import xyz.vaillant.poc.back.core.config.CoreConfig;
import xyz.vaillant.poc.back.core.exception.parameter.ParameterException;
import xyz.vaillant.poc.back.core.exception.user.UserNotFoundException;
import xyz.vaillant.poc.back.core.model.clan.Clan;
import xyz.vaillant.poc.back.core.model.foxhole.FoxholeAllegiance;
import xyz.vaillant.poc.back.core.model.user.OnboardingState;
import xyz.vaillant.poc.back.core.model.user.User;
import xyz.vaillant.poc.back.core.repository.ClanRepository;
import xyz.vaillant.poc.back.core.repository.UserRepository;
import xyz.vaillant.poc.back.core.service.onboarding.ClanOnboardingService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = CoreConfig.class)
public class ClanOnboardingServiceOnboardingRegisterClanTests {

    private User leaderUser;
    private User savedLeaderUser;
    private ClanOnboardingService sut;

    private final Long REGISTERED_USER_ID = 42L;
    private final String REGISTERED_USER_DISCORD_ID = "13";
    private final String REGISTERED_USERNAME = "john";
    private final String REGISTER_USER_MAIL = "mock.unregistered@mail.net";

    private Clan onboardClan;
    private Clan onboardSavedClan;
    private final String ONBOARD_CLAN_NAME = "noob";
    private final String ONBOARD_CLAN_DISCORD_ID = "214581534894";
    private final Long ONBOARD_SAVED_CLAN_ID = 72L;



    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private ClanRepository clanRepositoryMock;


    @BeforeEach()
    void beforeEach() {
        initMockedData();
        initMockedComponents();
        initSut();
    }

    @Test
    void contextLoad() {

    }

    @Test
    void registering_an_clan_with_a_name_discord_id_an_allegiance_and_a_registered_chief_shouldn_t_throw_an_exception() {
        assertDoesNotThrow(() -> sut.register(onboardClan, leaderUser.getId()));
        Mockito.verify(userRepositoryMock, Mockito.atLeastOnce()).findById(REGISTERED_USER_ID);
        Mockito.verify(clanRepositoryMock, Mockito.atLeastOnce()).save(onboardClan);
    }

    @Test
    void registering_an_clan_without_name_throw_an_exception() {
        onboardClan.setName(null);
        assertThrows(ParameterException.class, () -> sut.register(onboardClan, leaderUser.getId()));
    }

    @Test
    void registering_an_clan_without_discord_id_throw_an_exception() {
        onboardClan.setDiscordId(null);
        assertThrows(ParameterException.class, () -> sut.register(onboardClan, leaderUser.getId()));
    }

    @Test
    void registering_an_clan_without_leader_throw_an_exception() {
        Long unregisterUser = 57L;
        Mockito.when(userRepositoryMock.findById(unregisterUser)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> sut.register(onboardClan, unregisterUser));
    }

    @Test
    void registering_an_clan_without_allegiance_throw_an_exception() {
        onboardClan.setAllegiance(null);
        assertThrows(ParameterException.class, () -> sut.register(onboardClan, leaderUser.getId()));
    }

    private void initMockedComponents() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        clanRepositoryMock = Mockito.mock(ClanRepository.class);

        Mockito.when(userRepositoryMock.findById(REGISTERED_USER_ID)).thenReturn(Optional.of(leaderUser));
        Mockito.when(userRepositoryMock.save(leaderUser)).thenReturn(savedLeaderUser);
        Mockito.when(clanRepositoryMock.save(onboardClan)).thenReturn(onboardSavedClan);
        Mockito.when(clanRepositoryMock.findById(ONBOARD_SAVED_CLAN_ID)).thenReturn(Optional.of(onboardSavedClan));
    }

    private void initMockedData() {
        leaderUser = new User();
        leaderUser.setId(REGISTERED_USER_ID);
        leaderUser.setUsername(REGISTERED_USERNAME);
        leaderUser.setDiscordId(REGISTERED_USER_DISCORD_ID);
        leaderUser.setOnboardingState(OnboardingState.USER_INFORMATION_DONE);
        leaderUser.setEmail(REGISTER_USER_MAIL);

        savedLeaderUser = new User();
        savedLeaderUser.setId(REGISTERED_USER_ID);
        savedLeaderUser.setUsername(REGISTERED_USERNAME);
        savedLeaderUser.setDiscordId(REGISTERED_USER_DISCORD_ID);
        savedLeaderUser.setOnboardingState(OnboardingState.USER_ONBOARDING_COMPLETED);
        savedLeaderUser.setEmail(REGISTER_USER_MAIL);

        onboardClan = new Clan();
        onboardClan.setName(ONBOARD_CLAN_NAME);
        onboardClan.setDiscordId(ONBOARD_CLAN_DISCORD_ID);
        onboardClan.setAllegiance(FoxholeAllegiance.WARDEN);

        onboardSavedClan = new Clan();
        onboardSavedClan.setId(ONBOARD_SAVED_CLAN_ID);
        onboardSavedClan.setName(ONBOARD_CLAN_NAME);
        onboardSavedClan.setDiscordId(ONBOARD_CLAN_DISCORD_ID);
        onboardSavedClan.setLeader(leaderUser);
        onboardSavedClan.setAllegiance(FoxholeAllegiance.WARDEN);
    }

    private void initSut() {
        sut = new ClanOnboardingService(userRepositoryMock, clanRepositoryMock);
    }
}
