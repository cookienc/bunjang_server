package shop.makaroni.bunjang.src.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.item.model.*;

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
		String query = "select idx, price, name,\n" +
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
						String.valueOf(0),
						String.valueOf(0),
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
		String query = "select idx, price, name,\n" +
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
		String query = "select count(idx) as chat from ChatRoom where itemIdx=? and status != 'D' ";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx
		);
	}
	public List<String> getItemTags(int itemIdx){
		String query = "select name from Tag where itemIdx = ? and status != 'D'";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> rs.getString("name"),
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
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select itemIdx, min(path) path " +
					"					from ItemImage where status !='D' group by itemIdx ) img on Item.idx = img.itemIdx\n" +
					"where name like ? and status != 'D'\n" +
					"order by (case\n" +
					"              when name = ? then 0\n" +
					"              when name like ? then 1\n" +
					"              when name like ? then 2\n" +
					"              when name like ? then 3\n" +
					"              else 4\n" +
					"    end)\n" +
					"limit ?;";
			reqParams = new Object[]{param[4], param[0], param[1], param[2], param[3], count};

			return this.jdbcTemplate.query(query,
					(rs, rowNum) -> new GetSearchRes(
							String.valueOf(rs.getInt("itemIdx")),
							rs.getString("price"),
							rs.getString("name"),
							rs.getBoolean("safePay"),
							rs.getBoolean("isAd"),
							rs.getString("path"),
							rs.getString("status").charAt(0)
					),
					reqParams
			);

		}
		else if(sort == 'R'){
			reqParams = new Object[]{param[4], count};
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select itemIdx, min(path) path from ItemImage where status = 'Y' group by itemIdx) img\n" +
					"                   on Item.idx = img.itemIdx\n" +
					"where name like ?\n" +
					"  and status != 'D'\n" +
					"order by Item.updatedAt desc\n" +
					"limit ?;\n";
			return this.jdbcTemplate.query(query,
					(rs, rowNum) -> new GetSearchRes(
							String.valueOf(rs.getInt("itemIdx")),
							rs.getString("price"),
							rs.getString("name"),
							rs.getBoolean("safePay"),
							rs.getBoolean("isAd"),
							rs.getString("path"),
							rs.getString("status").charAt(0)
					),
					reqParams
			);
		}
		else if(sort == 'L'){
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select itemIdx, min(path) path from ItemImage where status = 'Y' group by itemIdx) img\n" +
					"                   on Item.idx = img.itemIdx\n" +
					"where name like ?\n" +
					"  and status != 'D'\n" +
					"order by price asc\n" +
					"limit ?;\n;";
			reqParams = new Object[]{param[4], count};
			return this.jdbcTemplate.query(query,
					(rs, rowNum) -> new GetSearchRes(
							String.valueOf(rs.getInt("itemIdx")),
							rs.getString("price"),
							rs.getString("name"),
							rs.getBoolean("safePay"),
							rs.getBoolean("isAd"),
							rs.getString("path"),
							rs.getString("status").charAt(0)
					),
					reqParams
			);
		}
		else{
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select ItemImage.status, itemIdx, min(path) path\n" +
					"                    from ItemImage\n" +
					"                    where status != 'D'\n" +
					"                    group by ItemImage.status, itemIdx) img\n" +
					"                   on Item.idx = img.itemIdx\n" +
					"where name like ?\n" +
					"  and Item.status != 'D' order by price desc limit ?;";
			reqParams = new Object[]{param[4], count};
			return this.jdbcTemplate.query(query,
					(rs, rowNum) -> new GetSearchRes(
							String.valueOf(rs.getInt("itemIdx")),
							rs.getString("price"),
							rs.getString("name"),
							rs.getBoolean("safePay"),
							rs.getBoolean("isAd"),
							rs.getString("path"),
							rs.getString("status").charAt(0)
					),
					reqParams
			);
		}

	}

	public List<GetLogRes> getItemLastN(int userIdx, int count) {
		String query =
				"select distinct Log.itemIdx itemIdx, name, price, safePay, isAd, max(Log.createdAt) createdAt, path\n" +
				"from (Log join Item I on Log.itemIdx = I.idx)\n" +
				"         left join\n" +
				"     (select itemIdx, min(path) path from ItemImage where status != 'D' group by itemIdx) img\n" +
				"     on I.idx = img.itemIdx\n" +
				"where userIdx = ?\n" +
				"  and status != 'D'\n" +
				"group by itemIdx, name, price, safePay, isAd\n" +
				"order by createdAt desc\n" +
				"limit ?;";
		Object[] params = new Object[]{userIdx, count};
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetLogRes(
						String.valueOf(rs.getInt("itemIdx")),
						rs.getString("price"),
						rs.getString("name"),
						rs.getBoolean("safePay"),
						rs.getBoolean("isAd"),
						rs.getTimestamp("createdAt"),
						rs.getString("path")
				),
				params
		);
	}


	public int checkBrandIdx(int brandIdx) {
		String query = "select exists(select idx from Brand where idx = ? and status != 'D');";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				brandIdx);
	}

	public int getItemCnt(int brandIdx) {
		String query = "select count(idx) from Item where brandIdx = ? and status !='D';";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				brandIdx);
	}

	public List<GetBrandRes> getBrand(int userIdx, char sort) {
		String query;
		if(sort == 'K'){
			query =	"select distinct logo, idx brandIdx, name brandName, englishName,\n" +
					"idx in (select brandIdx from Brand join (select * from BrandFollow where userIdx = ? and status!='D') follow\n" +
					"on Brand.idx = brandIdx where Brand.status != 'D') follow\n" +
					"from Brand\n" +
					"order by brandName asc;";
		}
		else{
			query = "select distinct logo, idx brandIdx, name brandName, englishName,\n" +
					"idx in (select brandIdx from Brand join (select * from BrandFollow where userIdx = ? and status!='D') follow\n" +
					"on Brand.idx = brandIdx where Brand.status != 'D') follow\n" +
					"from Brand\n" +
					"order by englishName asc;";
		}
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetBrandRes(
						rs.getString("logo"),
						String.valueOf(rs.getInt("brandIdx")),
						rs.getString("brandName"),
						rs.getString("englishName"),
						null,
						rs.getBoolean("follow")
				),
				userIdx
		);

	}

	public List<GetBrandRes> getBrandSearch(int userIdx, String name) {
		String query = "select distinct logo, idx brandIdx, name brandName, englishName,\n" +
				"idx in (select brandIdx from Brand join (select * from BrandFollow where userIdx = ? and status!='D') follow\n" +
				"on Brand.idx = brandIdx where Brand.status != 'D') follow\n" +
				"from Brand\n" +
				"where (Brand.name like ? or englishName like ?)\n" +
				"order by brandName asc;";
		Object[] params = new Object[]{userIdx, "%"+name+"%", "%"+name+"%"};
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetBrandRes(
						rs.getString("logo"),
						String.valueOf(rs.getInt("brandIdx")),
						rs.getString("brandName"),
						rs.getString("englishName"),
						null,
						rs.getBoolean("follow")
				),
				params
		);
	}

	public List<GetUserBrandRes> getUserBrand(int userIdx, char sort) {
		String query;
		if(sort == 'K'){
			query =	"select distinct logo, Brand.idx brandIdx, name brandName, englishName from Brand join\n" +
					"(select * from BrandFollow where status != 'D') BrandFollow\n" +
					"on Brand.idx = BrandFollow.brandIdx\n" +
					"where userIdx = ? and Brand.status !='D'\n" +
					"order by brandName asc;";
		}
		else{
			query = "select distinct logo, Brand.idx brandIdx, name brandName, englishName from Brand join\n" +
					"(select * from BrandFollow where status != 'D') BrandFollow\n" +
					"on Brand.idx = BrandFollow.brandIdx\n" +
					"where userIdx = ? and Brand.status !='D'\n" +
					"order by englishName asc;";
		}
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetUserBrandRes(
						rs.getString("logo"),
						String.valueOf(rs.getInt("brandIdx")),
						rs.getString("brandName"),
						rs.getString("englishName"),
						null
				),
				userIdx
		);
	}


	public List<GetSubcategoryRes> getSubcategory(String code) {
		String query = "select concat(parentCode,code) code, name from Category where parentCode=?";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetSubcategoryRes(
						rs.getString("code"),
						rs.getString("name")
				),
				code
		);
	}

	public List<GetSearchRes> getCategoryItems(String code, char sort, int count) {
		Object[] reqParams = new Object[]{code + "%", count};
		String query;
		if(sort=='R') {
			query = "select Item.idx itemIdx, path, price, Item.name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select ItemImage.status, itemIdx, min(path) path\n" +
					"                    from ItemImage\n" +
					"                    where status != 'D'\n" +
					"                    group by ItemImage.status, itemIdx) img\n" +
					"                   on Item.idx = img.itemIdx\n" +
					"where category like ?\n" +
					"  and Item.status != 'D'\n" +
					"order by Item.updatedAt desc\n" +
					"limit ?;\n";
		}
		else if(sort=='F'){
			query = "select Item.idx itemIdx, path, price, Item.name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select ItemImage.status, itemIdx, min(path) path\n" +
					"                    from ItemImage\n" +
					"                    where status != 'D'\n" +
					"                    group by ItemImage.status, itemIdx) img\n" +
					"                   on Item.idx = img.itemIdx\n" +
					"where category like ?\n" +
					"  and Item.status != 'D'\n" +
					"order by Item.hit desc\n" +
					"limit ?;\n";
		}
		else if(sort=='L'){
			query = "select Item.idx itemIdx, path, price, Item.name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select ItemImage.status, itemIdx, min(path) path\n" +
					"                    from ItemImage\n" +
					"                    where status != 'D'\n" +
					"                    group by ItemImage.status, itemIdx) img\n" +
					"                   on Item.idx = img.itemIdx\n" +
					"where category like ?\n" +
					"  and Item.status != 'D'\n" +
					"order by Item.price asc\n" +
					"limit ?;\n";
		}
		else{
			query = "select Item.idx itemIdx, path, price, Item.name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select ItemImage.status, itemIdx, min(path) path\n" +
					"                    from ItemImage\n" +
					"                    where status != 'D'\n" +
					"                    group by ItemImage.status, itemIdx) img\n" +
					"                   on Item.idx = img.itemIdx\n" +
					"where category like ?\n" +
					"  and Item.status != 'D'\n" +
					"order by Item.price desc\n" +
					"limit ?;\n";
		}
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetSearchRes(
						String.valueOf(rs.getInt("itemIdx")),
						rs.getString("price"),
						rs.getString("name"),
						rs.getBoolean("safePay"),
						rs.getBoolean("isAd"),
						rs.getString("path"),
						rs.getString("status").charAt(0)
				),
				reqParams
		);
	}
	public int checkCategory(String parentCode, String code) {
		String query = "select exists(select idx from Category where parentCode = ? and code = ? and status != 'D');";
		Object[] params = new Object[]{parentCode, code};
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				params);
	}

	public int createItem(PostItemReq postItemReq) {
		String query =
				"Insert into Item(sellerIdx, name, category, brandIdx, price, delivery, content, stock, isNew, exchange, safePay,\n" +
						"                 inspection, location, isAd)\n" +
						"                 values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);";
		Object[] params = postItemReq.getPostItemReq();
		this.jdbcTemplate.update(query, params);
		String lastInsertIdQuery = "select last_insert_id()";
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
	}

	public int findBrand(String tag) {
		String query = "select IF((select exists(select idx from Brand where name = ? or englishName = ?)),\n" +
				"               (select idx from Brand where name = ? or englishName = ?),\n" +
				"               0);";
		Object[] params = new Object[]{tag, tag, tag, tag};
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				params);
	}

	public void setBrand(int itemIdx, int brandIdx){
		String query = "update Item set brandIdx = ? where idx=?;";
		Object[] params = new Object[]{brandIdx,itemIdx};
		this.jdbcTemplate.update(query, params);
	}
}
