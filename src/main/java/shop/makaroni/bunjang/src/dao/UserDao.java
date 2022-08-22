package shop.makaroni.bunjang.src.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {

	private final NamedParameterJdbcTemplate template;
	private final UserMapper userMapper;

	public void update(Long userId, PatchUserRequest request) {
		userMapper.update(userId, request);
	}

	public void delete(Long userId) {
		var sql = "update User " +
				"set status='D' " +
				"where idx=:userId";

		template.update(sql, Map.of("userId", userId));
	}
//	public Optional<User> getMyStore(Long userId) {
//		var sql = "select idx, storeName, isCertificated, storeImage from User u " +
//				"where u.idx=:userId " +
//				"and u.status='Y'";
//		try {
//			User user = template.queryForObject(sql, Map.of("userId", userId), BeanPropertyRowMapper.newInstance(User.class));
//			return Optional.of(user);
//		} catch (EmptyResultDataAccessException e) {
//			return Optional.empty();
//		}
//	}

	public Optional<User> findById(Long userId) {
		var sql = "select * from User where idx=:userId";
		try {
			User user = template.queryForObject(sql, Map.of("userId", userId), BeanPropertyRowMapper.newInstance(User.class));
			return Optional.of(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
