package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ReviewDao {

	private final NamedParameterJdbcTemplate template;

	public Integer countStoreReview(Long userId) {
		var sql = "select count(*) from ReviewUser ru " +
				"where ru.storeIdx=:userId " +
				"and status='Y'";
		return template.queryForObject(sql, Map.of("userId", userId), Integer.class);
	}
}
