package xyz.vaillant.poc.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.vaillant.poc.core.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
