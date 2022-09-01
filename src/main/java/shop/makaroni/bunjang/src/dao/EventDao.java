package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.event.dto.EventBannerDto;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventDao {

	private final NamedParameterJdbcTemplate template;

	public List<EventBannerDto> getBanner() {
		var sql = "select idx eventIdx, name, image from Event where endDate > now() and status = 'Y'";
		return template.query(sql, new BeanPropertyRowMapper<>(EventBannerDto.class));
	}
}
