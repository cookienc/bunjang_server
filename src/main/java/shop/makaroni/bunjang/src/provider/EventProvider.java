package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.EventDao;
import shop.makaroni.bunjang.src.domain.event.EventBannerView;
import shop.makaroni.bunjang.src.domain.event.dto.EventBannerDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventProvider {

	private final EventDao eventDao;

	public List<EventBannerView> getBanner() {
		List<EventBannerDto> dtos = eventDao.getBanner();
		return dtos.stream()
				.map(EventBannerView::of)
				.collect(Collectors.toList());
	}
}

