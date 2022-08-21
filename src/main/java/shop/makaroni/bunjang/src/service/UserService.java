package shop.makaroni.bunjang.src.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserDao userDao;

	public void update(Long userId, PatchUserRequest request) {
		userDao.update(userId, request);
	}

	public void delete(Long userId) {
		userDao.delete(userId);
	}
}
