package shop.makaroni.bunjang.src.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.item.model.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Repository
public class ItemDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public GetItemRes getItem(Long itemIdx){
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

	public int checkItemIdx(Long itemIdx){
		String query = "select exists(select idx from Item where idx = ? and status != 'D')";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx);

	}

	public int getItemWishCnt(Long itemIdx){
		String query = "select count(idx) as wish from WishList where itemIdx = ? and status != 'D'";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx);
	}

	public int getItemChatCnt(Long itemIdx){
		String query = "select count(idx) as chat from ChatRoom where itemIdx=? and status != 'D' ";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx
		);
	}
	public List<String> getItemTags(Long itemIdx){
		String query = "select name from Tag where itemIdx = ? and status != 'D'";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> rs.getString("name"),
				itemIdx);

	}


	public List<String> getItemImages(Long itemIdx) {
		String query = "select path from ItemImage where itemIdx = ? and status != 'D';";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new  String(
						rs.getString("path")),
				itemIdx);
	}

	public List<GetSearchRes> getSearchRes(String name, char sort, int page){
		String query;
		Object[] reqParams;
		String[] param={name, name+"%", "%"+name+"%", "%"+name, "%"+name+"%"};
		page = 6 * (page-1);
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
					"limit 6 offset ?;";
			reqParams = new Object[]{param[4], param[0], param[1], param[2], param[3], page};

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
			reqParams = new Object[]{param[4], page};
			query = "select Item.idx itemIdx, path, price, name, safePay, isAd, Item.status status\n" +
					"from Item\n" +
					"         left join (select itemIdx, min(path) path from ItemImage where status = 'Y' group by itemIdx) img\n" +
					"                   on Item.idx = img.itemIdx\n" +
					"where name like ?\n" +
					"  and status != 'D'\n" +
					"order by Item.updatedAt desc\n" +
					"limit 6 offset ?;\n";
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
					"limit 6 offset ?;";
			reqParams = new Object[]{param[4], page};
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
					"  and Item.status != 'D' order by price desc limit 6 offset ?;;";
			reqParams = new Object[]{param[4], page};
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

	public List<GetLogRes> getItemLastN(long userIdx, int page) {
		page = 6 * (page-1);
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
				"limit 6 offset ?;";
		Object[] params = new Object[]{userIdx, page};
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetLogRes(
						String.valueOf(rs.getInt("itemIdx")),
						rs.getString("price"),
						rs.getString("name"),
						rs.getBoolean("safePay"),
						rs.getBoolean("isAd"),
						rs.getString("path")
				),
				params
		);
	}


	public int checkBrandIdx(Long brandIdx) {
		String query = "select exists(select idx from Brand where idx = ? and status != 'D');";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				brandIdx);
	}

	public int getItemCnt(Long brandIdx) {
		String query = "select count(idx) from Item where brandIdx = ? and status !='D';";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				brandIdx);
	}

	public List<GetBrandRes> getBrand(Long userIdx, int page, char sort) {
		String query;
		page = 6 * (page-1);
		Object[] params = new Object[]{userIdx, page};
		if(sort == 'K'){
			query =	"select distinct logo, idx brandIdx, name brandName, englishName,\n" +
					"idx in (select brandIdx from Brand join (select * from BrandFollow where userIdx = ? and status!='D') follow\n" +
					"on Brand.idx = brandIdx where Brand.status != 'D') follow\n" +
					"from Brand\n" +
					"order by brandName asc\n" +
					"limit 6 offset ?;";
		}
		else{
			query = "select distinct logo, idx brandIdx, name brandName, englishName,\n" +
					"idx in (select brandIdx from Brand join (select * from BrandFollow where userIdx = ? and status!='D') follow\n" +
					"on Brand.idx = brandIdx where Brand.status != 'D') follow\n" +
					"from Brand\n" +
					"order by englishName asc\n" +
					"limit 6 offset ?;";
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
				params
		);

	}

	public List<GetBrandRes> getBrandSearch(Long userIdx, String name, int page) {
		page = 6 * (page-1);
		String query = "select distinct logo, idx brandIdx, name brandName, englishName,\n" +
				"idx in (select brandIdx from Brand join (select * from BrandFollow where userIdx = ? and status!='D') follow\n" +
				"on Brand.idx = brandIdx where Brand.status != 'D') follow\n" +
				"from Brand\n" +
				"where (Brand.name like ? or englishName like ?)\n" +
				"order by brandName asc\n" +
				"limit 6 offset ?;";
		Object[] params = new Object[]{userIdx, "%"+name+"%", "%"+name+"%", page};
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

	public List<GetUserBrandRes> getUserBrand(Long userIdx, int page, char sort) {
		String query;
		page = 5 * (page-1);
		if(sort == 'K'){
			query =	"select distinct logo, Brand.idx brandIdx, name brandName, englishName from Brand join\n" +
					"(select * from BrandFollow where status != 'D') BrandFollow\n" +
					"on Brand.idx = BrandFollow.brandIdx\n" +
					"where userIdx = ? and Brand.status !='D'\n" +
					"order by brandName asc\n" +
					"limit 6 offset ?;";
		}
		else{
			query = "select distinct logo, Brand.idx brandIdx, name brandName, englishName from Brand join\n" +
					"(select * from BrandFollow where status != 'D') BrandFollow\n" +
					"on Brand.idx = BrandFollow.brandIdx\n" +
					"where userIdx = ? and Brand.status !='D'\n" +
					"order by englishName asc\n" +
					"limit 6 offset ?;";
		}
		Object[] params = new Object[]{userIdx, page};
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetUserBrandRes(
						rs.getString("logo"),
						String.valueOf(rs.getInt("brandIdx")),
						rs.getString("brandName"),
						rs.getString("englishName"),
						null
				),
				params
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

	public List<GetSearchRes> getCategoryItems(String code, char sort, int page) {
		page = 5 * (page-1);
		Object[] reqParams = new Object[]{code + "%", page};
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
					"limit 6 offset ?;\n";
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
					"limit 6 offset ?;\n";
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
					"limit 6 offset ?;\n";
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
					"limit 6 offset ?;\n";
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

	public Long createItem(Long sellerIdx, ItemReq itemReq) {
		String query =
				"Insert into Item(sellerIdx, name, category, brandIdx, price, delivery, content, stock, isNew, exchange, safePay,\n" +
						"                 inspection, location, isAd, buyerIdx)\n" +
						"                 values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?);";
		Object[] params = itemReq.getPostItemReq(sellerIdx);
		this.jdbcTemplate.update(query, params);
		String lastInsertIdQuery = "select last_insert_id()";
		return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
	}

	public Long findBrand(String tag) {
		String query = "select IF((select exists(select idx from Brand where (name = ? or englishName = ?) and status!='D')),\n" +
				"               (select idx from Brand where (name = ? or englishName = ?) and status!='D'),\n" +
				"               0);";
		Object[] params = new Object[]{tag, tag, tag, tag};
		return this.jdbcTemplate.queryForObject(query,
				Long.class,
				params);
	}

	public void setBrand(Long itemIdx, Long brandIdx){
		String query = "update Item set brandIdx = ? where idx=?;";
		Object[] params = new Object[]{brandIdx,itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public void setTags(Long itemIdx, String tag) {
		String query = "insert into Tag(itemIdx, name) values(?, ?);";
		Object[] params = new Object[]{itemIdx, tag};
		this.jdbcTemplate.update(query, params);
	}

	public void setImage(Long itemIdx, String image) {
		String query = "insert into ItemImage(itemIdx, path) values(?, ?);";
		Object[] params = new Object[]{itemIdx, image};
		this.jdbcTemplate.update(query, params);
	}

	public int checkImage(Long itemIdx, String image) {
		String query = "select IF(exists(select idx from ItemImage where itemIdx = ? and path = ?),\n" +
				"          (select idx from ItemImage where itemIdx = ? and path = ?),\n" +
				"          0);";
		Object[] params = new Object[]{itemIdx, image, itemIdx, image};
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				params);
	}
	public void updateImage(int imgIdx) {
		String query = "update ItemImage set status = 'Y' where idx = ?;";
		this.jdbcTemplate.update(query, imgIdx);
	}

	public void updateName(Long itemIdx, String name){
		String query = "update Item set name=? where idx = ?;";
		Object[] params = new Object[]{name, itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public void updateCategory(Long itemIdx, String category) {
		String query = "update Item set category=? where idx = ?;";
		Object[] params = new Object[]{category, itemIdx};
		this.jdbcTemplate.update(query, params);
	}
	public int checkTag(Long itemIdx, String Tag) {
		String query = "select IF(exists(select idx from Tag where itemIdx = ? and name = ?),\n" +
				"          (select idx from Tag where itemIdx = ? and name = ?),\n" +
				"          0);";
		Object[] params = new Object[]{itemIdx, Tag, itemIdx, Tag};
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				params);
	}

	public void setTag(Long itemIdx, String tag) {
		String query = "insert into Tag(itemIdx, name) values(?, ?);";
		Object[] params = new Object[]{itemIdx, tag};
		this.jdbcTemplate.update(query, params);
	}

	public void updateTag(int tagIdx) {
		String query = "update Tag set status = 'Y' where idx = ?;";
		this.jdbcTemplate.update(query, tagIdx);
	}

	public void updatePrice(Long itemIdx, Integer price) {
		String query = "update Item set price = ? where idx = ?;";
		Object[] params = new Object[]{price, itemIdx};
		this.jdbcTemplate.update(query, params);
	}
	public void updateDelivery(Long itemIdx, Integer delivery) {
		String query = "update Item set delivery = ? where idx = ?;";
		Object[] params = new Object[]{delivery, itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public void updateStock(Long itemIdx, Integer stock) {
		String query = "update Item set stock = ? where idx = ?;";
		Object[] params = new Object[]{stock, itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public void updateIsNew(Long itemIdx, Integer isNew) {
		String query = "update Item set isNew = ? where idx = ?;";
		Object[] params = new Object[]{isNew, itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public void updateExchange(Long itemIdx, Integer exchange) {
		String query = "update Item set exchange = ? where idx = ?;";
		Object[] params = new Object[]{exchange, itemIdx};
		this.jdbcTemplate.update(query, params);
	}
	public void updateSafePay(Long itemIdx,Integer safePay){
		String query = "update Item set safePay = ? where idx = ?;";
		Object[] params = new Object[]{safePay, itemIdx};
		this.jdbcTemplate.update(query, params);
	}
	public void updateSellerIdx(Long itemIdx,Long sellerIdx){
		String query = "update Item set sellerIdx = ? where idx = ?;";
		Object[] params = new Object[]{sellerIdx, itemIdx};
		this.jdbcTemplate.update(query, params);
	}
	public void updateLocation(Long itemIdx, String location){
		String query = "update Item set location = ? where idx = ?;";
		Object[] params = new Object[]{location, itemIdx};
		this.jdbcTemplate.update(query, params);
	}
	public void updateIsAd(Long itemIdx, Integer isAd){
		String query = "update Item set isAd = ? where idx = ?;";
		Object[] params = new Object[]{isAd, itemIdx};
		this.jdbcTemplate.update(query, params);
	}
	public void updateInspection(Long itemIdx,Integer inspection){
		String query = "update Item set inspection = ? where idx = ?;";
		Object[] params = new Object[]{inspection, itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public void updateContent(Long itemIdx, String content) {
		String query = "update Item set content = ? where idx = ?;";
		Object[] params = new Object[]{content, itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public void deleteAllTags(Long itemIdx) {
		String query = "update Tag set status = 'D' where itemIdx = ?;";
		this.jdbcTemplate.update(query, itemIdx);
	}

	public void deleteAllImages(Long itemIdx) {
		String query = "update ItemImage set status = 'D' where itemIdx = ?;";
		this.jdbcTemplate.update(query, itemIdx);
	}

	public void patchStatus(Long idx, String status) {
		String query = "update Item set status = ? where idx = ?;";
		Object[] params = new Object[]{status, idx};
		this.jdbcTemplate.update(query, params);
	}

	public char getStatus(Long idx) {
		String query = "select status from Item where idx = ?";
		return this.jdbcTemplate.queryForObject(query,
				char.class,
				idx);
	}

	public String getPrice(Long idx) {
		String query = "select price from Item where idx = ?";
		return String.valueOf(this.jdbcTemplate.queryForObject(query,
				int.class,
				idx));
	}

	public String getItemName(Long idx) {
		String query = "select name from Item where idx = ?";
		return this.jdbcTemplate.queryForObject(query,
				String.class,
				idx);
	}

	public String getUpdatedAt(Long idx) {
		String query = "select (case\n" +
				"    when timestampdiff(minute , updatedAt, now()) < 1 then concat(timestampdiff(second, updatedAt, now()), '초 전')\n" +
				"    when timestampdiff(hour, updatedAt, now()) < 1 then concat(timestampdiff(minute, updatedAt, now()), '분 전')\n" +
				"    when timestampdiff(hour, updatedAt, now()) < 24 then concat(timestampdiff(hour, updatedAt, now()), '시간 전')\n" +
				"    when timestampdiff(day, updatedAt, now()) < 31 then concat(timestampdiff(day, updatedAt, now()), '일 전')\n" +
				"    when timestampdiff(week, updatedAt, now()) < 4 then concat(timestampdiff(week, updatedAt, now()), '주 전')\n" +
				"    when timestampdiff(month,updatedAt, now()) < 12 then concat(timestampdiff(month, updatedAt, now()), '개월 전')\n" +
				"    else concat(timestampdiff(year, updatedAt, now()), '년 전')\n" +
				"end) as time\n" +
				"from Item\n" +
				"where idx = ?";
		return this.jdbcTemplate.queryForObject(query,
			String.class,
			idx);
	}

	public List<WishList> getWisher(Long idx, int page) {
		page = 5 * (page-1);
		Object[] params = new Object[]{idx, page};
		String query = "select User.idx userIdx, User.storeName name, User.storeImage image\n" +
				"from WishList\n" +
				"         left join User on WishList.userIdx = User.idx\n" +
				"where itemIdx = ? and WishList.status!='D' and User.status != 'D'" +
				"limit 6 offset ?;";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new WishList(
						String.valueOf(rs.getInt("userIdx")),
						rs.getString("name"),
						rs.getString("image")
				),
				params
		);
	}

	public void postWish(Long itemIdx, Long userIdx) {
		String query = "insert WishList(userIdx, itemIdx) values(?,?)";
		Object[] params = new Object[]{userIdx, itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public int checkWishList(Long userIdx, Long itemIdx) {
		String query = "select exists(select idx from WishList where userIdx = ? and itemIdx = ?)";
		Object[] params = new Object[]{userIdx, itemIdx};
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				params);
	}

	public void deleteWish(Long itemIdx, Long userIdx) {
		String query = "Update WishList set status='D' where userIdx=? and itemIdx=?";
		Object[] params = new Object[]{userIdx, itemIdx};
		this.jdbcTemplate.update(query, params);
	}

	public List<GetWishListRes> getWishList(Long userIdx, int page) {
		page = 5 * (page-1);
		Object[] params = new Object[]{userIdx, page};
		String query = "select wishList.itemIdx itemIdx,\n" +
				"       Item.name        name,\n" +
				"       Item.price       price,\n" +
				"       storeName,\n" +
				"       storeImage,\n" +
				"       Item.safePay     safePay,\n" +
				"       (case\n" +
				"            when timestampdiff(minute, Item.updatedAt, now()) < 1\n" +
				"                then concat(timestampdiff(second, Item.updatedAt, now()), '초 전')\n" +
				"            when timestampdiff(hour, Item.updatedAt, now()) < 1\n" +
				"                then concat(timestampdiff(minute, Item.updatedAt, now()), '분 전')\n" +
				"            when timestampdiff(hour, Item.updatedAt, now()) < 24\n" +
				"                then concat(timestampdiff(hour, Item.updatedAt, now()), '시간 전')\n" +
				"            when timestampdiff(day, Item.updatedAt, now()) < 31\n" +
				"                then concat(timestampdiff(day, Item.updatedAt, now()), '일 전')\n" +
				"            when timestampdiff(week, Item.updatedAt, now()) < 4\n" +
				"                then concat(timestampdiff(week, Item.updatedAt, now()), '주 전')\n" +
				"            when timestampdiff(month, Item.updatedAt, now()) < 12\n" +
				"                then concat(timestampdiff(month, Item.updatedAt, now()), '개월 전')\n" +
				"            else concat(timestampdiff(year, Item.updatedAt, now()), '년 전')\n" +
				"           end) as      updatedAt\n" +
				"from ((select * from WishList where status != 'D') wishList\n" +
				"    left join Item on wishList.itemIdx = Item.idx)\n" +
				"         left join User on Item.sellerIdx = User.idx\n" +
				"where User.status != 'D' && wishList.status != 'D'\n" +
				"  and Item.status != 'D'\n" +
				"  and wishList.userIdx = ?\n" +
				"order by Item.updatedAt desc\n" +
				"limit 6 offset ?;";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetWishListRes(
						String.valueOf(rs.getInt("itemIdx")),
						null,
						rs.getString("name"),
						String.valueOf(rs.getInt("price")),
						String.valueOf(rs.getBoolean("safePay")),
						rs.getString("storeName"),
						rs.getString("storeImage"),
						rs.getString("updatedAt")

				),
				params
		);
	}

	public String getCategoryName(HashMap<String,String> codes) {
		String query = "select name from Category where parentCode = ? and code = ? and status !='D'";
		Object[] params = new Object[]{codes.get("parentCode"), codes.get("subCode")};
		return this.jdbcTemplate.queryForObject(query,
				String.class,
				params);
	}

	public String getCategoryImg(HashMap<String, String> codes) {
		String query = "select image from Category where parentCode = ? and code = ? and status !='D'";
		Object[] params = new Object[]{codes.get("parentCode"), codes.get("subCode")};
		return this.jdbcTemplate.queryForObject(query,
				String.class,
				params);
	}

	public List<String> getWords(String q, int page) {
		page = 5 * (page-1);
		Object[] params = new Object[]{"%"+q+"%", page};
		String query = "select distinct name from Item where name like ? limit 6 offset ?;";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> rs.getString("name"),
				params);
	}

	public List<GetSearchCategoryRes> getCategories(String q, int page){
		page = 2 * (page-1);
		Object[] params = new Object[]{"%"+q+"%", page};
		String query = "select distinct concat(parentCode,code) code, Category.name name, Category.parentCode parent, image\n" +
				"from (select * from Item where name like ? and Item.status != 'D')Item\n" +
				"         left join Category on Item.category = concat(Category.parentCode, Category.code)\n" +
				"where Category.status != 'D'\n" +
				"limit 2 offset ?;";
		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetSearchCategoryRes(
						rs.getString("image"),
						rs.getString("code"),
						rs.getString("parent"),
						rs.getString("name")),
				params);

	}

	public int checkItemSold(Long itemIdx) {
		String query = "select exists(select idx from Item where idx = ? and status != 'S')";
		return this.jdbcTemplate.queryForObject(query,
				int.class,
				itemIdx);
	}

	public Long getReviewIdx(Long itemIdx) {
		String query = "select IF((select EXISTS(select idx from Review where itemIdx = ? and status != 'D')),\n" +
				"          (select idx from Review where itemIdx = ? and status != 'D'), 0);";
		Object[] params = new Object[]{itemIdx, itemIdx};
		return this.jdbcTemplate.queryForObject(query,
				Long.class,
				params);
	}

	public List<GetDealRes> getDeals(Long userIdx, String target, String status, int page) {
		Object[] params;
		String query;
		page = 6 * (page-1);
		if(status.equals("E")){
			query = "select idx itemIdx, name, price, date_format(updatedAt, '%Y년 %m월 %e일') updatedAt, location, hit\n" +
					"from Item\n" +
					"where " + target + " = ?\n" +
					"  and status in ('P','S','F') limit 6 offset ?;";
			params = new Object[]{userIdx, page};
		}
		else {
			query = "select idx itemIdx, name, price, date_format(updatedAt, '%Y년 %m월 %e일') updatedAt, location, hit\n" +
					"from Item\n" +
					"where " + target + " = ?\n" +
					"  and status = ? limit 6 offset ?;";
			params = new Object[]{userIdx, status, page};
		}

		return this.jdbcTemplate.query(query,
				(rs, rowNum) -> new GetDealRes(
						rs.getString("itemIdx"),
						null,
						rs.getString("name"),
						rs.getString("price"),
						rs.getString("updatedAt"),
						null,
						String.valueOf(rs.getInt("hit")),
						null,
						rs.getString("location")),
				params);
	}

	public void postReport(Long userIdx, PostReportReq postReportReq) {
		String query = "insert Into Report(target, writerIdx, type, content) values(?,?,?,?)";
		Object[] params = new Object[]{postReportReq.getTarget(), userIdx, postReportReq.getType(), postReportReq.getContent()};
		this.jdbcTemplate.update(query, params);
	}

	public Long getSellerIdx(Long itemIdx) {
		String query = "select sellerIdx from Item where idx = ? and status != 'D';";
		Object[] params = new Object[]{itemIdx};
		return this.jdbcTemplate.queryForObject(query,
				Long.class,
				params);
	}

//	public List<GetDealRes> getOrder(Long userIdx, String status) {
//		String query = "select idx itemIdx, name, price, date_format(updatedAt, '%Y년 %m월 %e일') updatedAt, location, hit\n" +
//				"from Item\n" +
//				"where buyerIdx = ?\n" +
//				"  and status = ?;";
//
//		if(status.equals("E")){
//			query = "select idx itemIdx, name, price, date_format(updatedAt, '%Y년 %m월 %e일') updatedAt, location, hit\n" +
//					"from Item\n" +
//					"where sellerIdx = ?\n" +
//					"  and status in ('P','S','F');";
//
//		}
//		Object[] params = new Object[]{userIdx, status};
//		return this.jdbcTemplate.query(query,
//				(rs, rowNum) -> new GetDealRes(
//						rs.getString("itemIdx"),
//						null,
//						rs.getString("name"),
//						rs.getString("price"),
//						rs.getString("updatedAt"),
//						null,
//						String.valueOf(rs.getInt("hit")),
//						null,
//						rs.getString("location")),
//				params);
//	}
}
