package fr.kirikou.Dashboard.repository;

import fr.kirikou.Dashboard.model.OAuthToken;
import org.springframework.data.repository.CrudRepository;

public interface OAuthTokenRepository extends CrudRepository<OAuthToken, Long> {
}
