package fr.kirikou.Dashboard.repository;

import fr.kirikou.Dashboard.model.OAuthToken;
import fr.kirikou.Dashboard.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    User getUserByName(String name);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User getUserByEmail(String email);
}
