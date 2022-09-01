package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.makaroni.bunjang.src.dao.EventDao;
import shop.makaroni.bunjang.src.domain.event.view.EventBannerView;
import shop.makaroni.bunjang.src.domain.event.dto.EventBannerDto;
import shop.makaroni.bunjang.src.domain.event.dto.EventInfoDto;
import shop.makaroni.bunjang.src.domain.event.view.EventInfoView;

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

	public List<EventInfoView> getEvent(Long eventIdx) {
		List<EventInfoDto> dtos = eventDao.getEventInfos(eventIdx);
		return dtos.stream()
				.map(EventInfoView::of)
				.collect(Collectors.toList());
	}
}

