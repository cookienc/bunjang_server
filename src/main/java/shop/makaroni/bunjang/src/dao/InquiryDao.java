package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.inquiry.Inquiry;
import shop.makaroni.bunjang.src.domain.inquiry.model.GetInquiryRes;
import shop.makaroni.bunjang.src.domain.inquiry.model.PostInqueryReq;
import shop.makaroni.bunjang.src.domain.inquiry.view.InquirySimpleResponse;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class InquiryDao {
	private JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate template;
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Inquiry save(Inquiry inquiry) {
		var sql = "insert into Inquiry (userIdx, targetIdx, parentIdx, post) " +
				"values (:userIdx, :targetIdx, :parentIdx, :post)";
		SqlParameterSource params = getParams(inquiry);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, params, keyHolder);
		inquiry.changeIdx(keyHolder.getKey().longValue());
		return inquiry;
	}

	@NotNull
	private SqlParameterSource getParams(Inquiry inquiry) {
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("userIdx", inquiry.getUserIdx())
				.addValue("targetIdx", inquiry.getStoreIdx())
				.addValue("parentIdx", inquiry.getParentIdx())
				.addValue("post", inquiry.getPost());
		return params;
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
				"where i.targetIdx = :storeIdx " +
				"and i.status = 'Y' " +
				"and i.parentIdx = 0 " +
				"limit 2";
		return template.query(sql, Map.of("storeIdx", storeIdx), BeanPropertyRowMapper.newInstance(InquirySimpleResponse.class));
	}

    public List<GetInquiryRes> getInquiry(Long targetIdx, char type) {
		String typeS =String.valueOf(type);
		String query =
				"select Inquiry.idx,\n" +
						"       userIdx,\n" +
						"       User.storeImage image,\n" +
						"       User.storeName  userName,\n" +
						"       post,\n" +
						"       (case\n" +
						"\n" +
						"            when timestampdiff(minute, Inquiry.updatedAt, now()) < 1\n" +
						"                then concat(timestampdiff(second, Inquiry.updatedAt, now()), '초 전')\n" +
						"            when timestampdiff(hour, Inquiry.updatedAt, now()) < 1\n" +
						"                then concat(timestampdiff(minute, Inquiry.updatedAt, now()), '분 전')\n" +
						"            when timestampdiff(hour, Inquiry.updatedAt, now()) < 24\n" +
						"                then concat(timestampdiff(hour, Inquiry.updatedAt, now()), '시간 전')\n" +
						"            when timestampdiff(day, Inquiry.updatedAt, now()) < 31\n" +
						"                then concat(timestampdiff(day, Inquiry.updatedAt, now()), '일 전')\n" +
						"            when timestampdiff(week, Inquiry.updatedAt, now()) < 4\n" +
						"                then concat(timestampdiff(week, Inquiry.updatedAt, now()), '주 전')\n" +
						"            when timestampdiff(month, Inquiry.updatedAt, now()) < 12\n" +
						"                then concat(timestampdiff(month, Inquiry.updatedAt, now()), '개월 전')\n" +
						"            else concat(timestampdiff(year, Inquiry.updatedAt, now()), '년 전')\n" +
						"           end) as     updatedAt\n" +
						"from Inquiry\n" +
						"         left join User on Inquiry.userIdx = User.idx\n" +
						"where Inquiry.status != 'D'\n" +
						"  and User.status != 'D'\n" +
						"  and type = ?\n" +
						"  and targetIdx = ?\n" +
						"order by Inquiry.updatedAt Desc;";
		Object[] params = new Object[]{typeS, targetIdx};
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetInquiryRes(
						String.valueOf(rs.getInt("idx")),
						String.valueOf(rs.getInt("userIdx")),
						rs.getString("image"),
						rs.getString("userName"),
						rs.getString("post"),
						rs.getString("updatedAt")),
				params);
    }

	public int checkInquiry(Long parentIdx) {
		String query = "select exists(select idx from Inquiry where idx = ?)";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				parentIdx);

	}

	public HashMap<String, String> PostInquiry(Long userIdx, PostInqueryReq postInqueryReq) {
		String query = "insert into Inquiry(targetIdx, parentIdx,  userIdx, post, type) values(?,?,?,?,?)";
		Object[] params = new Object[]{postInqueryReq.getTargetIdx(), postInqueryReq.getParentIdx(),
				userIdx, postInqueryReq.getPost(), postInqueryReq.getType()};
		this.jdbcTemplate.update(query, params);
		String lastInsertIdQuery = "select last_insert_id()";
		HashMap<String,String> res =  new HashMap<String,String>();
		res.put("idx", String.valueOf(this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class)));
		return res;
	}

	public Long getUserIdx(Long inquiryIdx) {
		String query = "select userIdx from Inquiry where idx = ?";
		return this.jdbcTemplate.queryForObject(query,
				Long.class,
				inquiryIdx);
	}

	public HashMap<String, String> DeleteInquiry(Long inquiryIdx) {
		String query = "update Inquiry set status = 'D' where idx = ? or parentIdx=?";
		Object[] params = new Object[]{inquiryIdx, inquiryIdx};
		this.jdbcTemplate.update(query, params);
		HashMap<String,String> res = new HashMap<>();
		res.put("idx", String.valueOf(inquiryIdx));
		return res;
	}
}
