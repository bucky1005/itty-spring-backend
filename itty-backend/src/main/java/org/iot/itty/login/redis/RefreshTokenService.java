package org.iot.itty.login.redis;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public void saveTokenInfo(String userEmail, String refreshToken, String accessToken) {
		refreshTokenRepository.save(new RefreshToken(userEmail, refreshToken, accessToken));
	}

	@Transactional
	public void removeRefreshToken(String accessToken) {
		refreshTokenRepository.findByAccessToken(accessToken)
			.ifPresent(refreshToken -> refreshTokenRepository.delete(null));
	}
}
