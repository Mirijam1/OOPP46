package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.user.User;
import nl.tudelft.gogreen.server.models.user.VerificationToken;
import nl.tudelft.gogreen.server.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VerificationTokenService implements IVerificationTokenService {
    private final VerificationTokenRepository tokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public VerificationToken createTokenForUser(User user, Integer token) {
        int timeOffsetInMillis = 1000 * 60 * 60; // 1 hour

        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .id(UUID.randomUUID())
                .user(user)
                .expiresAt(System.currentTimeMillis() + timeOffsetInMillis)
                .build();

        return tokenRepository.save(verificationToken);
    }

    @Override
    public HttpStatus verifyToken(Integer token, User user) {
        VerificationToken verificationToken = tokenRepository.findByTokenAndUserExternalId(token, user.getExternalId());

        if (verificationToken == null || !verificationToken.getUser().getId().equals(user.getId())) {
            return HttpStatus.NOT_FOUND;
        }

        if (System.currentTimeMillis() > verificationToken.getExpiresAt()) {
            tokenRepository.delete(verificationToken);
            return HttpStatus.FORBIDDEN;
        }

        tokenRepository.delete(verificationToken);
        return HttpStatus.OK;
    }
}
