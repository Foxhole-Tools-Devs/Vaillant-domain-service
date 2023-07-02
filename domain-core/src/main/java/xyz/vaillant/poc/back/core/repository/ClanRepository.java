package xyz.vaillant.poc.back.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.vaillant.poc.back.core.model.clan.Clan;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Long> {

}
