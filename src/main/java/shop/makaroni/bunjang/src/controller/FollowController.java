package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.follow.view.FollowersView;
import shop.makaroni.bunjang.src.provider.FollowProvider;
import shop.makaroni.bunjang.src.service.FollowService;

import java.util.List;

@RestController
@RequestMapping("/follows/users")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;
	private final FollowProvider followProvider;

	@GetMapping("/{userIdx}/followers")
	public ResponseEntity<List<FollowersView>> getFollowers(@PathVariable Long userIdx) {
		return ResponseEntity.ok(followProvider.getFollowers(userIdx));
	}
}
