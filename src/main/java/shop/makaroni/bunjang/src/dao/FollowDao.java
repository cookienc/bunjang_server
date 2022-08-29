package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.follow.dto.FollowersDto;
import shop.makaroni.bunjang.src.domain.follow.dto.FollowingsDto;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class FollowDao {

	private final NamedParameterJdbcTemplate template;

	public Integer countMyFollowers(Long userId) {
		var sql = "select count(*) from Follow f " +
				"where f.storeIdx=:userId " +
				"and f.status='Y'";
		return template.queryForObject(sql, Map.of("userId", userId), Integer.class);
	}

	public Integer countMyFollowings(Long userId) {
		var sql = "select count(*) from Follow f " +
				"where f.userIdx=:userId " +
				"and f.status='Y'";
		return template.queryForObject(sql, Map.of("userId", userId), Integer.class);
	}

	public List<FollowersDto> getFollowers(Long userIdx) {
		var sql = "select u.idx        storeIdx, " +
				"       u.storeName  storeName, " +
				"       ifnull(u.storeImage, '이미지가 없습니다.') storeImage, " +
				"       count(case when i.status in ('Y', 'R', 'S', 'F') then 1 end) items, " +
				"       (select count(idx) from Follow where storeIdx = u.idx) followers " +
				"from Follow f " +
				"         left join User u on u.idx = f.userIdx " +
				"         left join Item i on i.sellerIdx = u.idx " +
				"where f.storeIdx = :userIdx " +
				"  and f.status = 'Y' " +
				"group by u.idx, u.storeName, u.storeImage";
		return template.query(sql, Map.of("userIdx", userIdx), BeanPropertyRowMapper.newInstance(FollowersDto.class));
	}


	public List<FollowingsDto> getFollowings(Long userIdx) {
		var sql = "select u.idx                                                        storeIdx, " +
				"       u.storeName                                                  storeName, " +
				"       ifnull(u.storeImage, '이미지가 없습니다.')                           storeImage, " +
				"       count(case when i.status in ('Y', 'R', 'S', 'F') then 1 end) items, " +
				"       (select count(idx) from Follow where userIdx = u.idx)       followers " +
				"from Follow f " +
				"         left join User u on u.idx = f.storeIdx " +
				"         left join Item i on i.sellerIdx = u.idx " +
				"where f.userIdx = :userIdx " +
				"  and f.status = 'Y' " +
				"group by u.idx, u.storeName, u.storeImage";
		return template.query(sql, Map.of("userIdx", userIdx), BeanPropertyRowMapper.newInstance(FollowingsDto.class));
	}

	public Long doFollow(Long userIdx, Long storeIdx) {
		var sql = "insert into Follow (userIdx, storeIdx) " +
				"values (:userIdx, :storeIdx)";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", userIdx)
				.addValue("storeIdx", storeIdx);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, params, keyHolder);

		return keyHolder.getKey().longValue();
	}

	public Boolean alreadyExistFollow(Long userIdx, Long storeIdx) {
		var sql = "select exists(select idx from Follow " +
				"where userIdx = :userIdx " +
				"and storeIdx = :storeIdx " +
				"and status = 'Y')";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", userIdx)
				.addValue("storeIdx", storeIdx);

		return template.queryForObject(sql, params, Boolean.class);
	}

	public boolean alreadyNotification(Long userIdx, Long storeIdx) {
		var sql = "select notification from Follow " +
				"where userIdx = :userIdx " +
				"and storeIdx = :storeIdx " +
				"and status = 'Y'";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", userIdx)
				.addValue("storeIdx", storeIdx);

		return template.queryForObject(sql, params, Boolean.class);
	}

	public void delete(Long userIdx, Long storeIdx) {
		var sql = "update Follow " +
				"set status = 'D', " +
				"notification = 0 " +
				"where userIdx = :userIdx " +
				"and storeIdx = :storeIdx " +
				"and status = 'Y'";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", userIdx)
				.addValue("storeIdx", storeIdx);
		template.update(sql, params);
	}

	public void saveNotification(Long userIdx, Long storeIdx) {
		var sql = "update Follow " +
				"set notification = 1 " +
				"where userIdx = :userIdx " +
				"and storeIdx = :storeIdx " +
				"and status = 'Y'";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", userIdx)
				.addValue("storeIdx", storeIdx);
		template.update(sql, params);
	}

	public Long findIdByUserIdAndStoreId(Long userIdx, Long storeIdx) {
		var sql = "select idx from Follow " +
				"where userIdx = :userIdx " +
				"and storeIdx = :storeIdx " +
				"and status = 'Y'";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", userIdx)
				.addValue("storeIdx", storeIdx);
		return template.queryForObject(sql, params, Long.class);
	}

	public void deleteNotification(Long userIdx, Long storeIdx) {
		var sql = "update Follow " +
				"set notification = 0 " +
				"where userIdx = :userIdx " +
				"and storeIdx = :storeIdx " +
				"and status = 'Y'";
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", userIdx)
				.addValue("storeIdx", storeIdx);

		template.update(sql, params);
	}
}
