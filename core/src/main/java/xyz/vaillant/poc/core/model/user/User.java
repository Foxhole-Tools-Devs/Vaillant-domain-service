package xyz.vaillant.poc.core.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "vaillant_user")
public class User {

    public User() {
        onboardingState = OnboardingState.NOT_REGISTER;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "discord_id")
    private String discordId;

    @Column(name = "onboarding_state")
    @Enumerated(EnumType.STRING)
    private OnboardingState onboardingState;
}
