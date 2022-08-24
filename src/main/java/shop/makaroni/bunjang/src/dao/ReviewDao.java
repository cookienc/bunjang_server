package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.review.dto.ReviewAllResponse;

import java.util.List;
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

	public List<ReviewAllResponse> getReviewInfo(Long storeIdx) {
		var sql = "select u.storeName reviewerName, " +
				"       u.storeImage reviewerImage, " +
				"       r.post post, " +
				"       r.rating rating, " +
				"       (case " +
				"            when timestampdiff(minute , r.createdAt, now()) < 1 then concat(timestampdiff(second, r.createdAt, now()), '초 전')   " +
				"            when timestampdiff(hour, r.createdAt, now()) < 1 then concat(timestampdiff(minute, r.createdAt, now()), '분 전')   " +
				"            when timestampdiff(hour, r.createdAt, now()) < 24 then concat(timestampdiff(hour, r.createdAt, now()), '시간 전')   " +
				"            when timestampdiff(day, r.createdAt, now()) < 31 then concat(timestampdiff(day, r.createdAt, now()), '일 전') " +
				"            when timestampdiff(week, r.createdAt, now()) < 4 then concat(timestampdiff(week, r.createdAt, now()), '주 전') " +
				"            when timestampdiff(month,r.createdAt, now()) < 12 then concat(timestampdiff(month, r.createdAt, now()), '개월 전')      " +
				"            else concat(timestampdiff(year, r.createdAt, now()), '년 전')   " +
				"        end) as date " +
				"from Review r " +
				"inner join User u on u.idx = r.userIdx " +
				"inner join Item i on i.idx = r.itemIdx " +
				"where i.sellerIdx = :storeIdx";
		return template.query(sql, Map.of("storeIdx", storeIdx), BeanPropertyRowMapper.newInstance(ReviewAllResponse.class));
	}
}
