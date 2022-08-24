package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.login.LoginRequest;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.response.exception.CannotDecodeEx;
import shop.makaroni.bunjang.utils.AES128;
import shop.makaroni.bunjang.utils.JwtService;

import java.security.GeneralSecurityException;
import java.util.NoSuchElementException;

import static shop.makaroni.bunjang.src.response.ErrorCode.CANNOT_ENCODE_PASSWORD;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

	private final UserDao userDao;
	private final JwtService jwtService;

	public String login(LoginRequest loginRequest) {

		User user = userDao.findByLoginId(loginRequest.getLoginId()).orElseThrow(NoSuchElementException::new);

		try {
			AES128.matchPassword(user.getPassword(), loginRequest.getPassword());
		} catch (GeneralSecurityException e) {
			throw new CannotDecodeEx(CANNOT_ENCODE_PASSWORD.getMessages());
		}

		return jwtService.createJwt(user.getIdx());
	}
}