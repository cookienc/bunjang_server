package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.review.ReviewCommentDto;
import shop.makaroni.bunjang.src.domain.review.ReviewSpecificDto;
import shop.makaroni.bunjang.src.domain.review.dto.ReviewSimpleView;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	public Optional<String> getRating(Long storeIdx) {
		var sql = "select round(sum(r.rating) / count(r.idx), 1) from Review r " +
				"inner join (select i.idx itemIdx from Item i " +
				"where i.sellerIdx = :storeIdx) i on i.itemIdx= r.itemIdx " +
				"where r.status = 'Y'";

		return getRating(storeIdx, sql);
	}

	@NotNull
	private Optional<String> getRating(Long storeIdx, String sql) {
		try {
			String rating = template.queryForObject(sql, Map.of("storeIdx", storeIdx), String.class);
			rating = isItNull(rating);
			return Optional.of(rating);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@NotNull
	private String isItNull(String rating) {
		if (rating == null) {
			rating = "0";
		}
		return rating;
	}

	public List<ReviewSimpleView> getReviewInfo(Long storeIdx) {
		var sql = "select u.storeName reviewerName, " +
				"       ifnull(u.storeImage, '이미지가 없습니다.') reviewerImage, " +
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
				"where i.sellerIdx = :storeIdx " +
				"limit 2";
		return template.query(sql, Map.of("storeIdx", storeIdx), BeanPropertyRowMapper.newInstance(ReviewSimpleView.class));
	}

	public List<ReviewSpecificDto> findAllByStoreIdx(Long storeIdx, Integer start, Integer offset) {
		var sql = "select r.idx idx, " +
				"	 i.idx itemIdx, " +
				"    u.idx reviewerIdx, " +
				"    u.storeName  reviewerName, " +
				"       ifnull(u.storeImage, '이미지가 없습니다.') reviewerImage, " +
				"       r.post       reviewPost, " +
				"       r.rating     rating, " +
				"       i.name       purchasedProduct, " +
				"       (case " +
				"            when timestampdiff(minute, r.createdAt, now()) < 1 " +
				"                then concat(timestampdiff(second, r.createdAt, now()), '초 전') " +
				"            when timestampdiff(hour, r.createdAt, now()) < 1 " +
				"                then concat(timestampdiff(minute, r.createdAt, now()), '분 전') " +
				"            when timestampdiff(hour, r.createdAt, now()) < 24 " +
				"                then concat(timestampdiff(hour, r.createdAt, now()), '시간 전') " +
				"            when timestampdiff(day, r.createdAt, now()) < 31 then concat(timestampdiff(day, r.createdAt, now()), '일 전') " +
				"            when timestampdiff(week, r.createdAt, now()) < 4 then concat(timestampdiff(week, r.createdAt, now()), '주 전') " +
				"            when timestampdiff(month, r.createdAt, now()) < 12 " +
				"                then concat(timestampdiff(month, r.createdAt, now()), '개월 전') " +
				"            else concat(timestampdiff(year, r.createdAt, now()), '년 전') " +
				"           end) as  reviewDate " +
				"from Review r " +
				"         inner join User u on u.idx = r.userIdx " +
				"         inner join Item i on i.idx = r.itemIdx and i.status = 'S'" +
				"where i.sellerIdx = :storeIdx " +
				"and r.status = 'Y' " +
				"order by r.createdAt ASC " +
				"limit :start, :offset";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("storeIdx", storeIdx)
				.addValue("start", start)
				.addValue("offset", offset);

		return template.query(sql, params, BeanPropertyRowMapper.newInstance(ReviewSpecificDto.class));
	}

	public Optional<Object> findReviewCommentById(Long reviewIdx) {
		var sql = "select u.storeName sellerName, " +
				"    c.post sellerPost, " +
				"(case " +
				"            when timestampdiff(minute, c.createdAt, now()) < 1 " +
				"                then concat(timestampdiff(second, c.createdAt, now()), '초 전') " +
				"            when timestampdiff(hour, c.createdAt, now()) < 1 " +
				"                then concat(timestampdiff(minute, c.createdAt, now()), '분 전') " +
				"            when timestampdiff(hour, c.createdAt, now()) < 24 " +
				"                then concat(timestampdiff(hour, c.createdAt, now()), '시간 전') " +
				"            when timestampdiff(day, c.createdAt, now()) < 31 then concat(timestampdiff(day, c.createdAt, now()), '일 전') " +
				"            when timestampdiff(week, c.createdAt, now()) < 4 then concat(timestampdiff(week, c.createdAt, now()), '주 전') " +
				"            when timestampdiff(month, c.createdAt, now()) < 12 " +
				"                then concat(timestampdiff(month, c.createdAt, now()), '개월 전') " +
				"            else concat(timestampdiff(year, c.createdAt, now()), '년 전') " +
				"           end) as  sellerDate " +
				"from Comment c " +
				"inner join Review r on r.idx = c.reviewIdx " +
				"    inner join Item i on i.idx = r.itemIdx " +
				"inner join User u on u.idx = i.sellerIdx " +
				"where c.reviewIdx = :reviewIdx " +
				"and c.status = 'Y'";

		try {
			ReviewCommentDto commentDto = template.queryForObject(sql, Map.of("reviewIdx", reviewIdx), BeanPropertyRowMapper.newInstance(ReviewCommentDto.class));
			return Optional.of(commentDto);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
