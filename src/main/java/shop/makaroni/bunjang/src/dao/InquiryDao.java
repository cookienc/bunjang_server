package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.inquiry.Inquiry;
import shop.makaroni.bunjang.src.domain.inquiry.view.InquirySimpleResponse;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class InquiryDao {

	private final NamedParameterJdbcTemplate template;

	public Inquiry save(Inquiry inquiry) {
		var sql = "insert into Inquiry (userIdx, storeIdx, parentIdx, post) " +
				"values (:userIdx, :storeIdx, :parentIdx, :post)";
		SqlParameterSource params = getParams(inquiry);
		insertInquiry(inquiry, sql, params);
		return inquiry;
	}

	@NotNull
	private SqlParameterSource getParams(Inquiry inquiry) {
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", inquiry.getUserIdx())
				.addValue("storeIdx", inquiry.getStoreIdx())
				.addValue("parentIdx", inquiry.getParentIdx())
				.addValue("post", inquiry.getPost());
		return params;
	}

	private void insertInquiry(Inquiry inquiry, String sql, SqlParameterSource params) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, params, keyHolder);
		inquiry.changeIdx(keyHolder.getKey().longValue());
	}

	public List<InquirySimpleResponse> findAllByStoreId(Long storeIdx) {
		var sql = "select u.storeName name, " +
				"       ifnull(u.storeImage, '이미지가 없습니다.') image, " +
				"       i.post post, " +
				"       (case " +
				"            when timestampdiff(minute , i.createdAt, now()) < 1 then concat(timestampdiff(second, i.createdAt, now()), '초 전') " +
				"            when timestampdiff(hour, i.createdAt, now()) < 1 then concat(timestampdiff(minute, i.createdAt, now()), '분 전') " +
				"            when timestampdiff(hour, i.createdAt, now()) < 24 then concat(timestampdiff(hour, i.createdAt, now()), '시간 전') " +
				"            when timestampdiff(day, i.createdAt, now()) < 31 then concat(timestampdiff(day, i.createdAt, now()), '일 전') " +
				"            when timestampdiff(week, i.createdAt, now()) < 4 then concat(timestampdiff(week, i.createdAt, now()), '주 전') " +
				"            when timestampdiff(month,i.createdAt, now()) < 12 then concat(timestampdiff(month, i.createdAt, now()), '개월 전') " +
				"            else concat(timestampdiff(year, i.createdAt, now()), '년 전') " +
				"        end) as date " +
				"from Inquiry i " +
				"inner join User u on u.idx = i.userIdx " +
				"where i.storeIdx = :storeIdx " +
				"and i.status = 'Y' " +
				"and i.parentIdx = 0 " +
				"limit 2";
		return template.query(sql, Map.of("storeIdx", storeIdx), BeanPropertyRowMapper.newInstance(InquirySimpleResponse.class));
	}
}
