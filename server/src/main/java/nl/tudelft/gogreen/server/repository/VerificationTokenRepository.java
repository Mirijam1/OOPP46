package nl.tudelft.gogreen.server.repository;

import nl.tudelft.gogreen.server.models.user.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    VerificationToken findByTokenAndUserExternalId(Integer token, UUID externalId);
}
