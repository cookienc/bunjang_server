package shop.makaroni.bunjang.src.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.item.Item;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;
import shop.makaroni.bunjang.utils.resolver.PagingCond;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {

	private final NamedParameterJdbcTemplate template;
	private final UserMapper userMapper;
	private final ItemMapper itemMapper;

	public void update(Long userIdx, PatchUserRequest request) {
		userMapper.update(userIdx, request);
	}

	public void delete(Long userIdx) {
		var sql = "update User " +
				"set status='D' " +
				"where idx=:userIdx";

		template.update(sql, Map.of("userIdx", userIdx));
	}

	public Optional<User> findById(Long userIdx) {
		var sql = "select * from User where idx=:userIdx";
		try {
			User user = template.queryForObject(sql, Map.of("userIdx", userIdx), BeanPropertyRowMapper.newInstance(User.class));
			return Optional.of(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Item> getMyStoreItem(Long userIdx, String condition, PagingCond pagingCond) {
		return itemMapper.getMyStoreItem(userIdx, condition, pagingCond);
	}

	public List<Item> searchStoreItemByName(Long userIdx, String itemName, String condition, PagingCond pagingCond) {
		return itemMapper.searchStoreItemByName(userIdx, itemName, condition, pagingCond);
	}
}
