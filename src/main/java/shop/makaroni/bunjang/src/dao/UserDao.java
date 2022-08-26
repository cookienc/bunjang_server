package shop.makaroni.bunjang.src.dao;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import shop.makaroni.bunjang.src.domain.user.StoreSearchDto;
import shop.makaroni.bunjang.src.domain.user.User;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;
import shop.makaroni.bunjang.utils.resolver.PagingCond;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
		var sql = "select idx, " +
				"loginId, " +
				"password, " +
				"storeName, " +
				"contactStart, " +
				"contactEnd, " +
				"isCertificated, " +
				"storeUrl, " +
				"ifnull(storeImage, '이미지가 없습니다.') storeImage, " +
				"IF(isnull(description),'상점소개가 없습니다.', description) description, " +
				"IF(isnull(policy),'물품의 상태가 기재된 것과 상이할 경우 배송완료일 기준 7일 이내에 환불 및 반품이 가능합니다.', policy) policy, " +
				"IF(isnull(precaution),'지역정보 없음.', precaution) precaution, " +
				"hit, " +
				"concat('+', timestampdiff(day, createdAt, now())) openDate, " +
				"createdAt, " +
				"updatedAt, " +
				"status " +
				"from User " +
				"where idx=:userId " +
				"and status = 'Y'";
		try {
			User user = template.queryForObject(sql, Map.of("userId", userId), BeanPropertyRowMapper.newInstance(User.class));
			return Optional.of(user);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByLoginId(String loginId) {
		var sql = "select * from User " +
				"where loginId=:loginId " +
				"and status = 'Y'";
		try {
			User user = template.queryForObject(sql, Map.of("loginId", loginId), BeanPropertyRowMapper.newInstance(User.class));
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

	public String getSoldCount(Long storeIdx) {
		var sql = "select count(*) " +
				"from Item i " +
				"where i.status = 'S' " +
				"and i.sellerIdx = :storeIdx";

		return template.queryForObject(sql, Map.of("storeIdx", storeIdx), String.class);
	}

	public String getSellerNameByItemIdx(Long itemIdx) {
		var sql = "select u.storeName from User u " +
				"where u.idx = (select i.sellerIdx from Item i where i.idx = :itemIdx)";
		return template.queryForObject(sql, Map.of("itemIdx", itemIdx), String.class);
	}

	public List<StoreSearchDto> searchStoreByName(String name) {
		var sql = "select u.idx storeIdx, " +
				"u.storeName storeName, " +
				"ifnull(u.storeImage, '이미지가 없습니다.') storeImage, " +
				"u.createdAt createdAt " +
				"from User u " +
				"where u.status = 'Y' " +
				"and u.storeName like :name";
		String wrappedName = "%" + name + "%";
		return template.query(sql, Map.of("name", wrappedName), BeanPropertyRowMapper.newInstance(StoreSearchDto.class));
	}

	public Integer countAllItems(Long storeIdx) {
		var sql = "select count(*) from Item where sellerIdx = :storeIdx and status in ('Y', 'R')";
		return template.queryForObject(sql, Map.of("storeIdx", storeIdx), Integer.class);
	}

	public Optional<Item> findItemWithUserIdxItemIdx(Long storeIdx, Long userIdx, Long itemIdx) {
		log.info("userIdx = {}", userIdx);
		log.info("itemIdx = {}", itemIdx);
		var sql = "select * from Item i " +
				"where i.status = 'S' " +
				"and i.sellerIdx = :storeIdx " +
				"and i.buyerIdx = :userIdx " +
				"and i.idx = :itemIdx";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("storeIdx", storeIdx)
				.addValue("userIdx", userIdx)
				.addValue("itemIdx", itemIdx);
		try {
			Item item = template.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Item.class));
			return Optional.of(item);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
