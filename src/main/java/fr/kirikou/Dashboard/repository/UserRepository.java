package fr.kirikou.Dashboard.repository;

import fr.kirikou.Dashboard.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Optional<User> getUserByName(String name);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> getUserByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.googleMail = ?1")
    Optional<User> getUserByGoogleMail(String email);

    @Query("SELECT u FROM User u WHERE u.redditName = ?1")
    Optional<User> getUserByRedditName(String name);
}
