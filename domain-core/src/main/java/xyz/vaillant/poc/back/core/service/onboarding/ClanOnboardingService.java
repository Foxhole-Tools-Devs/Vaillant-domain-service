package xyz.vaillant.poc.back.core.service.onboarding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import xyz.vaillant.poc.back.core.exception.parameter.ParameterException;
import xyz.vaillant.poc.back.core.exception.user.InsufficientOnboardingLevelException;
import xyz.vaillant.poc.back.core.exception.user.UserNotFoundException;
import xyz.vaillant.poc.back.core.model.clan.Clan;
import xyz.vaillant.poc.back.core.model.foxhole.FoxholeAllegiance;
import xyz.vaillant.poc.back.core.model.user.OnboardingState;
import xyz.vaillant.poc.back.core.model.user.User;
import xyz.vaillant.poc.back.core.repository.ClanRepository;
import xyz.vaillant.poc.back.core.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClanOnboardingService {
    private final UserRepository userRepository;

    private final ClanRepository clanRepository;

    public Clan register(final Clan clan, final Long leaderUserId) {
        if (Objects.isNull(clan.getDiscordId())) {
            log.error("clan sould have a discord Id to register");
            throw ParameterException.ParameterExceptionBuilder.createParameterException()
                    .addMissingParameter(String.class, "clan.discordId")
                    .build();
        }

        if (Objects.isNull(clan.getName())) {
            log.error("clan sould have a name to register");
            throw ParameterException.ParameterExceptionBuilder.createParameterException()
                    .addMissingParameter(String.class, "clan.name")
                    .build();
        }

        if (Strings.isBlank(clan.getName())) {
            log.error("clan sould have a valid name to register");
            throw ParameterException.ParameterExceptionBuilder.createParameterException()
                    .addBadValueParameter(String.class, "clan.name", "clan name should have a name")
                    .build();
        }

        if (Objects.isNull(clan.getAllegiance())) {
            log.error("clan sould have a allegiance to register");
            throw ParameterException.ParameterExceptionBuilder.createParameterException()
                    .addMissingParameter(FoxholeAllegiance.class, "clan.allegiance")
                    .build();
        }

        Optional<User> user = userRepository.findById(leaderUserId);
        if (user.isEmpty()) {
            log.error("user with id : " + leaderUserId + " is not registered");
            throw new UserNotFoundException("User not found !", leaderUserId);
        }

        var leader = user.get();
        if (leader.getOnboardingState().equals(OnboardingState.NOT_REGISTER) || leader.getOnboardingState().equals(OnboardingState.REGISTERED)) {
            log.error("User had not a valid user id: " + leader.getId() + " onboarding state : " + leader.getOnboardingState());
            throw new InsufficientOnboardingLevelException("User had bad user onboarding : " + leader.getOnboardingState());
        }

        clan.setLeader(leader);
        var savedClan = clanRepository.save(clan);

        leader.setOnboardingState(OnboardingState.USER_ONBOARDING_COMPLETED);
        var savedLeader = userRepository.save(leader);

        log.info("User : " + savedLeader.getId() + " create a new clan : " + clan.getId() + ", " + clan.getName());
        return savedClan;
    }
}
