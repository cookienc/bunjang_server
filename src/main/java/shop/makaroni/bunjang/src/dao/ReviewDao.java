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

	public String getRating(Long storeIdx) {
		var sql = "select round(sum(r.rating) / count(r.idx), 1) from Review r " +
				"inner join (select i.idx itemIdx from Item i " +
				"where i.sellerIdx = :storeIdx) i on i.itemIdx= r.itemIdx";
		return template.queryForObject(sql, Map.of("storeIdx", storeIdx), String.class);
	}
}
