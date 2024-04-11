package org.iot.itty.login.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.iot.itty.login.jwt.JwtUtil;
import org.iot.itty.dto.UserDTO;
import org.iot.itty.login.redis.RedisConfig;
import org.iot.itty.login.redis.RefreshToken;
import org.iot.itty.login.redis.RefreshTokenRepository;
import org.iot.itty.user.aggregate.UserEntity;
import org.iot.itty.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
	private final UserRepository userRepository;
	public final BCryptPasswordEncoder bCryptPasswordEncoder;
	public final RedisConfig redisConfig;
	public final RedisTemplate redisTemplate;
	public final RefreshTokenRepository refreshTokenRepository;

	public LoginServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
		RedisConfig redisConfig, RedisTemplate redisTemplate,
		RefreshTokenRepository refreshTokenRepository) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.redisConfig = redisConfig;
		this.redisTemplate = redisTemplate;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	/* 회원 가입 */
	@Override
	public int registUser(UserDTO userDTO) {

		String userEmail = userDTO.getUserEmail();
		String userPassword = userDTO.getUserPassword();
		String userName = userDTO.getUserName();
		String userPhoneNumber = userDTO.getUserPhoneNumber();
		String userNickname = userDTO.getUserNickname();

		// 아이디 중복 체크
		boolean isExist = userRepository.existsByUserEmail(userEmail);

		if (isExist) {
			throw new IllegalStateException("'" + userEmail + "'는(은) 이미 존재하는 사용자 입니다.");
		}

		UserEntity data = new UserEntity();

		data.setUserEmail(userEmail);
		data.setUserPassword(bCryptPasswordEncoder.encode(userPassword));
		data.setUserName(userName);
		data.setUserPhoneNumber(userPhoneNumber);
		data.setUserNickname(userNickname);
		data.setUserRole("USER");
		data.setUserDeleteStatus(0);
		data.setUserIntroduction("내 소개가 아직 없습니다.");

		userRepository.save(data);
		return data.getUserCodePk();
	}

	/* 로그아웃 */
	@Override
	public void userLogout(String accessToken) {
		// Token에서 로그인된 사용자의 Token을 가져옴

		ModelMapper mapper = new ModelMapper();

		// RefreshToken refreshToken = mapper.map(userEmail, RefreshToken.class);

		// 액세스 토큰의 유효시간
		// Long expiration = jwtUtil.getExpiration(accessToken);

		// Redis Cache 저장
		// redisTemplate.opsForValue().set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

		// refreshToken 삭제
		// refreshTokenRepository.delete(refreshToken);
	}

	/* 회원 탈퇴 */
	@Override
	public boolean userWithdrawal(UserDTO userDTO) {

		boolean isWithdrawalSuccessful = true;
		UserEntity user = userRepository.findByUserEmail(userDTO.getUserEmail());

		if(user != null) {
			if (user.getUserDeleteStatus() != 1) {

				user.setUserDeleteStatus(1);

				userRepository.save(user);

				return isWithdrawalSuccessful;
			}
		} else {
			// return ResponseEntity.badRequest().build().hasBody();
			throw new IllegalAccessError("'" + userDTO.getUserEmail() + "' 해당 유저를 찾을 수 없습니다.");
		}

		return !isWithdrawalSuccessful;
	}

	/* DB에서 유저 정보를 가져와 입력된 정보와 비교할 User 객체 생성 */
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserEmail(userEmail);

		if(userEntity == null) {
			throw new UsernameNotFoundException("'" + userEmail + "' 해당 유저는 존재하지 않습니다.");
		}

		return new User(userEntity.getUserEmail(), userEntity.getUserPassword(),
			true, true, true, true,
			new ArrayList<>());
	}

	/* 유저 이메일 조회하여 해당 유저 정보 반환(토큰 발급용) */
	@Override
	public UserDTO getUserDetailsByUserEmail(String userEmail) {
		UserEntity userEntity = userRepository.findByUserEmail(userEmail);

		if(userEntity == null)
			throw new UsernameNotFoundException(userEmail);

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDTO userDTO = mapper.map(userEntity, UserDTO.class);

		return userDTO;
	}

	@Override
	public UserDTO searchUserEmail(String userEmail) {
		UserEntity userEntity = userRepository.findByUserEmail(userEmail);

		ModelMapper mapper = new ModelMapper();
		UserDTO userDTO = mapper.map(userEntity, UserDTO.class);

		return userDTO;
	}
}