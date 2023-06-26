package xyz.vaillant.poc.core.service.onboarding;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import xyz.vaillant.poc.core.exception.parameter.ParameterException;
import xyz.vaillant.poc.core.model.user.OnboardingState;
import xyz.vaillant.poc.core.model.user.User;
import xyz.vaillant.poc.core.repository.UserRepository;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOnboardingService {
    private final UserRepository userRepository;

    public User registerUser(final User user) throws ParameterException {
        if (Objects.isNull(user.getDiscordId())) {
            log.error("User without a discord Id try to register");
            throw ParameterException.ParameterExceptionBuilder.createParameterException()
                    .addMissingParameter(String.class, "discordId", "a valid user need a discordId")
                    .build();
        }

        var optUser = userRepository.findByDiscordId(user.getDiscordId());

        if (optUser.isPresent()) {
            log.error("User is alreadyRegister");
            throw ParameterException.ParameterExceptionBuilder.createParameterException()
                    .addBadValueParameter(String.class, "discordId", "user discordId already register")
                    .build();
        }

        user.setOnboardingState(OnboardingState.REGISTERED);
        return userRepository.save(user);
    }

    public User onboardingUserInformation(final User user) throws IllegalStateException, ParameterException {
        if (Objects.isNull(user.getOnboardingState()) || user.getOnboardingState() != OnboardingState.REGISTERED) {
            log.error("User is in a bad state : " + user.getOnboardingState());
            throw new IllegalStateException();
        }

        if (Objects.isNull(user.getUsername())) {
            log.error("User don't have a username");
            throw ParameterException.ParameterExceptionBuilder.createParameterException()
                    .addMissingParameter(String.class, "username", "username is necessary")
                    .build();
        }

        if (Strings.isBlank(user.getUsername())) {
            log.error("User have an invalid username : " + user.getUsername());
            throw ParameterException.ParameterExceptionBuilder.createParameterException()
                    .addBadValueParameter(String.class, "username", "username should not be empty")
                    .build();
        }

        user.setOnboardingState(OnboardingState.USER_INFORMATION_DONE);
        var savedUser = userRepository.save(user);
        log.info("New user registered " + user.getId());
        return savedUser;
    }
}
