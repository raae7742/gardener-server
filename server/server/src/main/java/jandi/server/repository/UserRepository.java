package jandi.server.repository;

import jandi.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByGithub(String github);

    boolean existsByUsername(String name);

    Optional<User> findByUsername(String name);
}
