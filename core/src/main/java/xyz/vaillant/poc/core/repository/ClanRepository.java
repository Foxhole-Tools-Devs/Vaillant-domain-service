package xyz.vaillant.poc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.vaillant.poc.core.model.clan.Clan;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Long> {

}
