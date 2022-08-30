package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.response.ResponseInfo;
import shop.makaroni.bunjang.src.service.FollowService;
import shop.makaroni.bunjang.utils.resolver.Login;

import java.net.URI;

import static shop.makaroni.bunjang.src.response.SuccessStatus.DELETE_NOTIFICATION_SUCCESS;
import static shop.makaroni.bunjang.src.response.SuccessStatus.SAVE_NOTIFICATION_SUCCESS;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final FollowService followService;

	@PostMapping("/stores/{storeIdx}")
	public ResponseEntity<ResponseInfo> saveNotification(@Login Long userIdx, @PathVariable Long storeIdx) {
		Long notificationId = followService.saveNotification(userIdx, storeIdx);
		String url = "/notifications/" + notificationId;
		return ResponseEntity.created(URI.create(url)).body(ResponseInfo.of(SAVE_NOTIFICATION_SUCCESS));
	}

	@PatchMapping("/stores/{storeIdx}")
	public ResponseEntity<ResponseInfo> deleteNotification(@Login Long userIdx, @PathVariable Long storeIdx) {
		followService.deleteNotification(userIdx, storeIdx);
		return ResponseEntity.ok(ResponseInfo.of(DELETE_NOTIFICATION_SUCCESS));
	}
}
