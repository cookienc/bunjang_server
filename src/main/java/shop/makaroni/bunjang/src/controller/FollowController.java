package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.follow.view.FollowersView;
import shop.makaroni.bunjang.src.domain.follow.view.FollowingsView;
import shop.makaroni.bunjang.src.provider.FollowProvider;
import shop.makaroni.bunjang.src.response.ResponseInfo;
import shop.makaroni.bunjang.src.response.SuccessStatus;
import shop.makaroni.bunjang.src.service.FollowService;

import java.net.URI;
import java.util.List;

import static shop.makaroni.bunjang.src.response.SuccessStatus.FOLLOW_SUCCESS;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;
	private final FollowProvider followProvider;

	@PostMapping("/users/{userIdx}/stores/{storeIdx}")
	public ResponseEntity<ResponseInfo> doFollow(@PathVariable Long userIdx, @PathVariable Long storeIdx) {
		Long followId = followService.doFollow(userIdx, storeIdx);
		String url = "/follows/" + followId + "/users/" + userIdx + "/stores/" + storeIdx;
		return ResponseEntity.created(URI.create(url)).body(ResponseInfo.of(FOLLOW_SUCCESS));
	}

	@PatchMapping("/users/{userIdx}/stores/{storeIdx}")
	public ResponseEntity<ResponseInfo> deleteFollowing(@PathVariable Long userIdx, @PathVariable Long storeIdx) {
		followService.delete(userIdx, storeIdx);
		return ResponseEntity.ok(ResponseInfo.of(SuccessStatus.DELETE_FOLLOW_SUCCESS));
	}

	@GetMapping("/users/{userIdx}/followers")
	public ResponseEntity<List<FollowersView>> getFollowers(@PathVariable Long userIdx) {
		return ResponseEntity.ok(followProvider.getFollowers(userIdx));
	}

	@GetMapping("/users/{userIdx}/followings")
	public ResponseEntity<List<FollowingsView>> getFollowings(@PathVariable Long userIdx) {
		return ResponseEntity.ok(followProvider.getFollowings(userIdx));
	}
}
