package shop.makaroni.bunjang.src.user.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.user.model.dto.PatchUserRequest;

@Repository
@RequiredArgsConstructor
public class UserDao {

	private final NamedParameterJdbcTemplate template;
	private final UserMapper userMapper;

	public void update(Long userId, PatchUserRequest request) {
		userMapper.update(userId, request);
	}
}
