package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.review.dto.FollowersDto;

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
				"         inner join User u on u.idx = f.userIdx " +
				"         inner join Item i on i.sellerIdx = u.idx " +
				"where f.storeIdx = 1 " +
				"  and f.status = 'Y' " +
				"group by u.idx, u.storeName, u.storeImage";
		return template.query(sql, Map.of("userIdx", userIdx), BeanPropertyRowMapper.newInstance(FollowersDto.class));
	}
}
