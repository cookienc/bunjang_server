package shop.makaroni.bunjang.src.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.item.model.GetItemRes;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ItemDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public GetItemRes getItem(int itemIdx){
		String query = "select price, name,\n" +
				"       IF(isnull(location),'지역정보 없음', location) location,\n" +
				"       (case\n" +
				"            when timestampdiff(minute , updatedAt, now()) < 1 then concat(timestampdiff(second, updatedAt, now()), '초 전')\n" +
				"           when timestampdiff(hour, updatedAt, now()) < 1 then concat(timestampdiff(minute, updatedAt, now()), '분 전')\n" +
				"           when timestampdiff(hour, updatedAt, now()) < 24 then concat(timestampdiff(hour, updatedAt, now()), '시간 전')\n" +
				"           when timestampdiff(day, updatedAt, now()) < 31 then concat(timestampdiff(day, updatedAt, now()), '일 전')\n" +
				"           when timestampdiff(week, updatedAt, now()) < 4 then concat(timestampdiff(week, updatedAt, now()), '주 전')\n" +
				"           when timestampdiff(month,updatedAt, now()) < 12 then concat(timestampdiff(month, updatedAt, now()), '개월 전')\n" +
				"           else concat(timestampdiff(year, updatedAt, now()), '년 전')\n" +
				"       end) as time,\n" +
				"        hit,\n" +
				"        0 as wish,\n" +
				"        0 as chat,\n" +
				"        isNew,\n" +
				"        delivery,\n" +
				"        exchange,\n" +
				"        content,\n" +
				"        category,\n" +
				"        brandIdx as brand,\n" +
				"        sellerIdx as seller,\n" +
				"        status \n"+
				"from Item\n" +
				"where idx = ?;";
		return this.jdbcTemplate.queryForObject(query,
				(rs, rowNum) -> new GetItemRes(
						rs.getString("price"),
						rs.getString("name"),
						rs.getString("location"),
						rs.getString("time"),
						rs.getInt("hit"),0,0,
						rs.getBoolean("isNew"),
						rs.getBoolean("delivery"),
						rs.getBoolean("exchange"),
						rs.getString("content"),
						rs.getString("category"),
						rs.getInt("brand"),
						rs.getInt("seller"),
						rs.getString("status").charAt(0),
						null, null),
				itemIdx);

	}

	public int checkItemIdx(int itemIdx){
		String query = "select exists(select idx from Item where idx = ?)";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx);

	}

	public int getItemWishCnt(int itemIdx){
		String query = "select count(idx) as wish from WishList where itemIdx = ?";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx);
	}

	public int getItemChatCnt(int itemIdx){
		String query = "select count(idx) as chat from ChatRoom where itemIdx=?";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx
		);
	}
	public List<String> getItemTags(int itemIdx){
		String query = "select name from Tag where itemIdx = ?";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new String(
						rs.getString("name")),
				itemIdx);

	}


	public List<String> getItemImages(int itemIdx) {
		String query = "select path from ItemImage where itemIdx = ? and status = 'Y';";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new  String(
						rs.getString("path")),
				itemIdx);
	}

}
