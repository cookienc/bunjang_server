package shop.makaroni.bunjang.src.service;


import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;
import shop.makaroni.bunjang.src.domain.user.dto.SaveUserRequest;
import shop.makaroni.bunjang.src.provider.UserProvider;
import shop.makaroni.bunjang.src.response.exception.CannotEncodeEx;
import shop.makaroni.bunjang.utils.AES128;
import shop.makaroni.bunjang.utils.JwtService;

import java.security.GeneralSecurityException;

import static shop.makaroni.bunjang.src.response.ErrorCode.CANNOT_ENCODE_PASSWORD;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserProvider userProvider;
	private final JwtService jwtService;

	private final UserDao userDao;

	public Long save(SaveUserRequest request) {
		userProvider.findByLoginId(request.getLoginId());

		String encodePassword;
		try {
			encodePassword = AES128.encode(request.getPassword());
		} catch (GeneralSecurityException e) {
			throw new CannotEncodeEx(CANNOT_ENCODE_PASSWORD.getMessages());
		}

		User user = userDao.save(request.getLoginId(), encodePassword);

		String storeName = validStoreName(request, user.getIdx());

		userDao.changeStoreName(user.getIdx(), storeName);

		return user.getIdx();
	}

	public void update(Long userId, PatchUserRequest request) {
		User user = userProvider.findById(userId);
		userDao.update(userId, request);
	}

	public void delete(Long userId) {
		User user = userProvider.findById(userId);
		user.validate();
		userDao.delete(userId);
	}

	private String validStoreName(SaveUserRequest request, Long idx) {
		String storeName = request.getStoreName();
		if (storeName == null || storeName.isBlank() || storeName.isEmpty()) {
			storeName = manipulateStoreName(idx);
		}
		return storeName;
	}

	@NotNull
	private String manipulateStoreName(Long idx) {
		String storeName;
		storeName = String.valueOf(idx);

		if (storeName.length() > 10) {
			return storeName.substring(0, 10);
		}

		return storeName;
	}
}
