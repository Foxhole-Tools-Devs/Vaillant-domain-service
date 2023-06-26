package xyz.vaillant.poc.core.model.clan;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.vaillant.poc.core.model.foxhole.FoxholeAllegiance;
import xyz.vaillant.poc.core.model.user.User;


@Getter
@Setter
@Entity(name = "clan")
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "discordId")
    private String discordId;

    @OneToOne
    @JoinColumn(name = "leader")
    private User leader;

    @Column(name = "allegiance")
    @Enumerated(EnumType.STRING)
    private FoxholeAllegiance allegiance;
}
