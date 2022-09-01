package shop.makaroni.bunjang.src.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import shop.makaroni.bunjang.src.domain.event.dto.EventBannerDto;
import shop.makaroni.bunjang.src.domain.event.dto.EventInfoDto;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class EventDao {

	private final NamedParameterJdbcTemplate template;

	public List<EventBannerDto> getBanner() {
		var sql = "select idx eventIdx, name, image from Event where endDate > now() and status = 'Y'";
		return template.query(sql, new BeanPropertyRowMapper<>(EventBannerDto.class));
	}

	public List<EventInfoDto> getEventInfos(Long eventIdx) {
		var sql = "select idx infoIdx, " +
				"image, " +
				"title, " +
				"post " +
				"from EventInfo " +
				"where eventIdx = :eventIdx " +
				"and status = 'Y'";
		return template.query(sql, Map.of("eventIdx", eventIdx), BeanPropertyRowMapper.newInstance(EventInfoDto.class));
	}
}
