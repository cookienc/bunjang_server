package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.response.ResponseInfo;
import shop.makaroni.bunjang.src.service.FollowService;

import java.net.URI;

import static shop.makaroni.bunjang.src.response.SuccessStatus.SAVE_NOTIFICATION_SUCCESS;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final FollowService followService;

	@PostMapping("/users/{userIdx}/stores/{storeIdx}")
	public ResponseEntity<ResponseInfo> saveNotification(@PathVariable Long userIdx, @PathVariable Long storeIdx) {
		Long notificationId = followService.saveNotification(userIdx, storeIdx);
		String url = "/notifications/" + notificationId;
		return ResponseEntity.created(URI.create(url)).body(ResponseInfo.of(SAVE_NOTIFICATION_SUCCESS));
	}

}
