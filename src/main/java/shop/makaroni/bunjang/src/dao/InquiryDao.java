package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.inquiry.Inquiry;

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
}
