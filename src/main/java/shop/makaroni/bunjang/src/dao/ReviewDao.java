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
		var sql = "select count(*) from Review r " +
				"inner join Item i on i.idx = r.itemIdx " +
				"where i.sellerIdx = :userId " +
				"and r.status = 'Y'";
		return template.queryForObject(sql, Map.of("userId", userId), Integer.class);
	}
}
