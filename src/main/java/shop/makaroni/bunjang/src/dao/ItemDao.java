package shop.makaroni.bunjang.src.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.item.model.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
public class ItemDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public GetItemRes getItem(int itemIdx){
		String query = "select concat(FORMAT(price,0),'원') as price, name,\n" +
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
				"stock,\n"+
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
				"where idx = ? and (status != 'D' and status != 'S');";
		return this.jdbcTemplate.queryForObject(query,
				(rs, rowNum) -> new GetItemRes(
						String.valueOf(rs.getInt("idx")),
						rs.getString("price"),
						rs.getString("name"),
						rs.getString("location"),
						rs.getString("time"),
						String.valueOf(rs.getInt("hit")),
						String.valueOf(rs.getInt("stock")),
						String.valueOf(0),String.valueOf(0),
						rs.getBoolean("isNew"),
						rs.getBoolean("delivery"),
						rs.getBoolean("exchange"),
						rs.getString("content"),
						rs.getString("category"),
						String.valueOf(rs.getInt("brand")),
						String.valueOf(rs.getInt("seller")),
						rs.getString("status").charAt(0),
						null, null),
				itemIdx);

	}
	public List<GetItemRes> getItems(){
		String query = "select idx, concat(FORMAT(price,0),'원') as price, name,\n" +
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
				"stock,\n"+
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
				"from Item\n where (status != 'D' and status != 'S')";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetItemRes(
						String.valueOf(rs.getInt("idx")),
						rs.getString("price"),
						rs.getString("name"),
						rs.getString("location"),
						rs.getString("time"),
						String.valueOf(rs.getInt("hit")),
						String.valueOf(rs.getInt("stock")),
						String.valueOf(0),String.valueOf(0),
						rs.getBoolean("isNew"),
						rs.getBoolean("delivery"),
						rs.getBoolean("exchange"),
						rs.getString("content"),
						rs.getString("category"),
						String.valueOf(rs.getInt("brand")),
						String.valueOf(rs.getInt("seller")),
						rs.getString("status").charAt(0),
						null, null)
				);

	}

	public int checkItemIdx(int itemIdx){
		String query = "select exists(select idx from Item where idx = ? and status != 'D')";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx);

	}

	public int getItemWishCnt(int itemIdx){
		String query = "select count(idx) as wish from WishList where itemIdx = ? and status != 'D'";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx);
	}

	public int getItemChatCnt(int itemIdx){
		String query = "select count(idx) as chat from ChatRoom where itemIdx=? ";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx
		);
	}
	public List<String> getItemTags(int itemIdx){
		String query = "select name from Tag where itemIdx = ? and status != 'D'";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new String(
						rs.getString("name")),
				itemIdx);

	}


	public List<String> getItemImages(int itemIdx) {
		String query = "select path from ItemImage where itemIdx = ? and status != 'D';";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new  String(
						rs.getString("path")),
				itemIdx);
	}

	public List<GetSearchRes> getSearchRes(String name, char sort, int count){
		String query;
		Object[] reqParams;
		String[] param={name, name+"%", "%"+name+"%", "%"+name, "%"+name+"%"};
		if (sort == 'C') {
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd\n" +
					"from Item left join (select itemIdx, min(path) path from ItemImage group by itemIdx)img on Item.idx = img.itemIdx\n"+
					"where name like ?\n" +
					"order by\n" +
					"    (case \n" +
					"        when name = ? then 0\n" +
					"        when name like ? then 1\n" +
					"        when name like ? then 2\n" +
					"        when name like ? then 3\n" +
					"        else 4\n" +
					"    end) limit ?;";
			reqParams = new Object[]{param[4], param[0], param[1], param[2], param[3], count};

			return this.jdbcTemplate.query(query,
					(rs, rowNum) -> new GetSearchRes(
							String.valueOf(rs.getInt("itemIdx")),
							rs.getString("price"),
							rs.getString("name"),
							rs.getBoolean("safePay"),
							rs.getBoolean("isAd"),
							Collections.singletonList(rs.getString("path"))
					),
					reqParams
			);

		}
		else if(sort == 'R'){
			reqParams = new Object[]{param[4], count};
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd\n" +
					"from Item left join (select itemIdx, min(path) path from ItemImage group by itemIdx)img on Item.idx = img.itemIdx\n"+
					"where name like ? order by updatedAt desc limit ?;";
			return this.jdbcTemplate.query(query,
					(rs, rowNum) -> new GetSearchRes(
							String.valueOf(rs.getInt("itemIdx")),
							rs.getString("price"),
							rs.getString("name"),
							rs.getBoolean("safePay"),
							rs.getBoolean("isAd"),
							Collections.singletonList(rs.getString("path"))
					),
					reqParams
			);
		}
		else if(sort == 'L'){
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd\n" +
					"from Item left join (select itemIdx, min(path) path from ItemImage group by itemIdx)img on Item.idx = img.itemIdx\n"+
					"where name like ? order by price asc limit ?;";
			reqParams = new Object[]{param[4], count};
			return this.jdbcTemplate.query(query,
					(rs, rowNum) -> new GetSearchRes(
							String.valueOf(rs.getInt("itemIdx")),
							rs.getString("price"),
							rs.getString("name"),
							rs.getBoolean("safePay"),
							rs.getBoolean("isAd"),
							Collections.singletonList(rs.getString("path"))
					),
					reqParams
			);
		}
		else{
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd \n" +
					"from Item left join (select itemIdx, min(path) path from ItemImage group by itemIdx)img on Item.idx = img.itemIdx\n"+
					"where name like ? order by price desc limit ?;";
			reqParams = new Object[]{param[4], count};
			return this.jdbcTemplate.query(query,
					(rs, rowNum) -> new GetSearchRes(
							String.valueOf(rs.getInt("itemIdx")),
							rs.getString("price"),
							rs.getString("name"),
							rs.getBoolean("safePay"),
							rs.getBoolean("isAd"),
							Collections.singletonList(rs.getString("path"))
					),
					reqParams
			);
		}

	}

	public List<GetLogRes> getItemLastN(int userIdx, int count) {
		String query =
				"select distinct Log.itemIdx itemIdx, name, price, safePay, isAd, max(Log.createdAt) createdAt, path\n" +
				"from (Log join Item I on Log.itemIdx = I.idx) left join  (select itemIdx, min(path) path from ItemImage group by itemIdx) img\n" +
				"on I.idx = img.itemIdx where userIdx = ?\n" +
				"group by itemIdx, name, price, safePay, isAd\n" +
				"order by createdAt desc\n" +
				"limit ?;\n";
		Object[] params = new Object[]{userIdx, count};
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetLogRes(
						String.valueOf(rs.getInt("itemIdx")),
						rs.getString("price"),
						rs.getString("name"),
						rs.getBoolean("safePay"),
						rs.getBoolean("isAd"),
						rs.getTimestamp("createdAt"),
						Collections.singletonList(rs.getString("path"))
				),
				params
		);
	}
}
