package shop.makaroni.bunjang.src.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.setting.model.Address;
import shop.makaroni.bunjang.src.domain.setting.model.Keyword;
import shop.makaroni.bunjang.src.domain.setting.model.Notification;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SettingDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public Notification getNotification(Long userIdx) {
        String query = "select " +
                "NA00,\n" +
                "NA01,\n" +
                "NA0100,\n" +
                "NA0101,\n" +
                "NB00,\n" +
                "NC00,\n" +
                "NC01,\n" +
                "NC02,\n" +
                "NC03,\n" +
                "NC04,\n" +
                "NC05,\n" +
                "ND00,\n" +
                "ND01,\n" +
                "ND02,\n" +
                "NE00,\n" +
                "NE01,\n" +
                "NF00,\n" +
                "NG00,\n" +
                "NG01\n"+
                "from Notification\n" +
                "where userIdx = ?;";
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new Notification(
                        rs.getBoolean("NA00"),
                        rs.getBoolean("NA01"),
                        String.valueOf(rs.getInt("NA0100")),
                        String.valueOf(rs.getInt("NA0101")),
                        rs.getBoolean("NB00"),
                        rs.getBoolean("NC00"),
                        rs.getBoolean("NC01"),
                        rs.getBoolean("NC02"),
                        rs.getBoolean("NC03"),
                        rs.getBoolean("NC04"),
                        rs.getBoolean("NC05"),
                        rs.getBoolean("ND00"),
                        rs.getBoolean("ND01"),
                        rs.getBoolean("ND02"),
                        rs.getBoolean("NE00"),
                        rs.getBoolean("NE01"),
                        rs.getBoolean("NF00"),
                        rs.getBoolean("NG00"),
                        rs.getBoolean("NG01")),
                userIdx);
    }

    public void patchNotification(Long userIdx, Notification res) {
        String query = "update Notification\n" +
                "set NA00 = ?,\n" +
                "    NA01 = ?,\n" +
                "    NA0100 = ?,\n" +
                "    NA0101 = ?,\n" +
                "    NB00 = ?,\n" +
                "    NC00 = ?,\n" +
                "    NC01 = ?,\n" +
                "    NC02 = ?,\n" +
                "    NC03 = ?,\n" +
                "    NC04 = ?,\n" +
                "    NC05 = ?,\n" +
                "    ND00 = ?,\n" +
                "    ND01 = ?,\n" +
                "    ND02 = ?,\n" +
                "    NE00 = ?,\n" +
                "    NE01 = ?,\n" +
                "    NF00 = ?,\n" +
                "    NG00 = ?,\n" +
                "    NG01 = ?\n" +
                "where userIdx = ?";
        Object[] params = res.getNotification(userIdx);
        this.jdbcTemplate.update(query, params);
    }

    public List<Address> getUserAddress(Long userIdx) {
        String query = "select idx,\n" +
                "       name,\n" +
                "       phoneNum,\n" +
                "       address,\n" +
                "       detail,\n" +
                "       isDefault\n" +
                "from Address\n" +
                "where userIdx = ? and status != 'D';";
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new Address(
                        rs.getString("idx"),
                        rs.getString("name"),
                        rs.getString("phoneNum"),
                        rs.getString("address"),
                        rs.getString("detail"),
                        rs.getBoolean("isDefault")),
                userIdx);
    }

    public Long postAddress(Long userIdx, Address req) {
        String query = "Insert into Address(userIdx, name, phoneNum, address, detail, isDefault) values(?,?,?,?,?,?);";
        Object[] params = new Object[]{userIdx, req.getName(), req.getPhoneNum(), req.getAddress(), req.getDetail(), req.getIsDefault()};
        this.jdbcTemplate.update(query, params);
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, Long.class);
    }

    public void deleteAddrDefault(Long userIdx, Long addrIdx) {
        String query = "update Address set isDefault=false, updatedAt = CURRENT_TIMESTAMP where userIdx = ? and idx != ?";
        Object[] params = new Object[]{userIdx, addrIdx};
        this.jdbcTemplate.update(query, params);
    }

    public Address getAddress(Long addrIdx) {
        String query = "select idx,\n" +
                "       name,\n" +
                "       phoneNum,\n" +
                "       address,\n" +
                "       detail,\n" +
                "       isDefault\n" +
                "from Address\n" +
                "where idx = ? and status != 'D';";
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new Address(
                        rs.getString("idx"),
                        rs.getString("name"),
                        rs.getString("phoneNum"),
                        rs.getString("address"),
                        rs.getString("detail"),
                        rs.getBoolean("isDefault")),
                addrIdx);
    }

    public void patchAddress(Long idx, Address res) {
        String query = "update Address set name = ?, phoneNum = ?, address = ?, detail = ?, isDefault = ?, updatedAt = CURRENT_TIMESTAMP where idx = ?;";
        Object[] params = res.getAddress(idx);
        this.jdbcTemplate.update(query, params);
    }

    public int checkAddress(Long idx) {
        String query = "select exists(select idx from Address where idx = ?)";
        return this.jdbcTemplate.queryForObject(query, int.class, idx);
    }

    public Long getAddressUser(Long idx) {
        String query = "select userIdx from Address where idx = ?";
        return this.jdbcTemplate.queryForObject(query, Long.class, idx);
    }

    public void deleteAddress(Long idx) {
        String query = "update Address set status = 'D', updatedAt = CURRENT_TIMESTAMP where idx = ?;";
        this.jdbcTemplate.update(query, idx);
    }

    public List<Keyword> getKeyword(Long userIdx) {
        String query = "select idx,\n" +
                "keyword,\n" +
                "notification,\n" +
                "category,\n" +
                "location,\n" +
                "minPrice,\n" +
                "maxPrice\n" +
                "from Keyword where userIdx = ? and status != 'D'";
        Object[] params = new Object[]{userIdx};
        return this.jdbcTemplate.query(query,
                (rs, rowNum) -> new Keyword(
                    rs.getString("idx"),
                    rs.getString("keyword"),
                    rs.getBoolean("notification"),
                    rs.getString("category"),
                    rs.getString("location"),
                    rs.getString("minPrice"),
                    rs.getString("maxPrice")),
                params);
    }
}
