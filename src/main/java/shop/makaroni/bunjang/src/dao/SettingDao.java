package shop.makaroni.bunjang.src.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.setting.model.Notification;

import javax.sql.DataSource;
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
}
