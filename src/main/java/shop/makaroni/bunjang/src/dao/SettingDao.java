package shop.makaroni.bunjang.src.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.item.model.GetItemRes;
import shop.makaroni.bunjang.src.domain.setting.model.GetNotificationRes;

import javax.sql.DataSource;
@Repository
public class SettingDao {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public GetNotificationRes getNotification(Long userIdx) {
        String query = "select alarmSet,\n" +
                "       silentTime,\n" +
                "       silentStart,\n" +
                "       silentEnd,\n" +
                "       chat,\n" +
                "       itemComment,\n" +
                "       itemWish,\n" +
                "       itemDiscount,\n" +
                "       reservedUp,\n" +
                "       resell,\n" +
                "       priceSuggestion,\n" +
                "       storeComment,\n" +
                "       storeFollow,\n" +
                "       storeReview,\n" +
                "       deliveryProcess,\n" +
                "       deliveryDone,\n" +
                "       event,\n" +
                "       wishContact,\n" +
                "       townEvent\n"+
                "from Notification\n" +
                "where userIdx = ?;";
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new GetNotificationRes(
                        rs.getBoolean("alarmSet"),
                        rs.getBoolean("silentTime"),
                        String.valueOf(rs.getInt("silentStart")),
                        String.valueOf(rs.getInt("silentEnd")),
                        rs.getBoolean("chat"),
                        rs.getBoolean("itemComment"),
                        rs.getBoolean("itemWish"),
                        rs.getBoolean("itemDiscount"),
                        rs.getBoolean("reservedUp"),
                        rs.getBoolean("resell"),
                        rs.getBoolean("priceSuggestion"),
                        rs.getBoolean("storeComment"),
                        rs.getBoolean("storeFollow"),
                        rs.getBoolean("storeReview"),
                        rs.getBoolean("deliveryProcess"),
                        rs.getBoolean("deliveryDone"),
                        rs.getBoolean("event"),
                        rs.getBoolean("wishContact"),
                        rs.getBoolean("townEvent")),
                userIdx);
    }
}
