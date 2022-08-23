package shop.makaroni.bunjang.src.dao;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.item.Item;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;
import shop.makaroni.bunjang.utils.resolver.PagingCond;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {
	private final NamedParameterJdbcTemplate template;

	private final UserMapper userMapper;
	private final ItemMapper itemMapper;

	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public User save(String loginId, String password) {
		var sql = "insert into User (loginId, password) " +
				"values (:loginId, :password)";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("loginId", loginId)
				.addValue("password", password);
		long key = update(sql, params);

		return User.builder()
				.idx(key)
				.loginId(loginId)
				.password(password)
				.build();
	}

	public void update(Long userId, PatchUserRequest request) {
		userMapper.update(userId, request);
	}

	public void delete(Long userId) {
		var sql = "update User " +
				"set status='D' " +
				"where idx=:userId";

		template.update(sql, Map.of("userId", userId));
	}

	public Optional<User> findById(Long userId) {
		var sql = "select * from User where idx=:userId";
		try {
			User user = template.queryForObject(sql, Map.of("userId", userId), BeanPropertyRowMapper.newInstance(User.class));
			return Optional.of(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void changeStoreName(Long idx, String storeName) {
		var sql = "update User u " +
				"set u.storeName = :storeName " +
				"where u.idx = :idx";
		template.update(sql, Map.of("storeName", storeName, "idx", idx));
	}

	public List<Item> getMyStoreItem(Long userId, String condition, PagingCond pagingCond) {
		return itemMapper.getMyStoreItem(userId, condition, pagingCond);
	}

	public List<Item> searchStoreItemByName(Long userId, String itemName, String condition, PagingCond pagingCond) {
		return itemMapper.searchStoreItemByName(userId, itemName, condition, pagingCond);
	}

	public int checkUserIdx(int userIdx){
		String query = "select exists(select idx from User where idx = ?)";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				userIdx);

	}

	private long update(String sql, SqlParameterSource params) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, params, keyHolder);

		long key = keyHolder.getKey().longValue();
		return key;
	}
}
