package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.event.view.EventBannerView;
import shop.makaroni.bunjang.src.domain.event.view.EventInfoView;
import shop.makaroni.bunjang.src.provider.EventProvider;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

	private final EventProvider eventProvider;

	@GetMapping
	public ResponseEntity<List<EventBannerView>> getBanner() {
		return ResponseEntity.ok().body(eventProvider.getBanner());
	}

	@GetMapping("/{eventIdx}")
	public ResponseEntity<List<EventInfoView>> getEvent(@PathVariable Long eventIdx) {
		return ResponseEntity.ok().body(eventProvider.getEvent(eventIdx));
	}
}
