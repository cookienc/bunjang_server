package shop.makaroni.bunjang.src.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.UserDao;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;
import shop.makaroni.bunjang.src.provider.UserProvider;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserDao userDao;
	private final UserProvider userProvider;

	public void update(Long userId, PatchUserRequest request) {
		User user = userProvider.findById(userId);
		userDao.update(userId, request);
	}

	public void delete(Long userId) {
		User user = userProvider.findById(userId);
		user.validate();
		userDao.delete(userId);
	}
}
