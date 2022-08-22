package shop.makaroni.bunjang.src.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;

import java.util.NoSuchElementException;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserDao userDao;

	public void update(Long userId, PatchUserRequest request) {
		User user = findById(userId);
		userDao.update(userId, request);
	}

	public void delete(Long userId) {
		User user = findById(userId);
		user.validate();
		userDao.delete(userId);
	}

	public User findById(Long userId) {
		User user = userDao.findById(userId).orElseThrow(NoSuchElementException::new);
		return user;
	}
}
