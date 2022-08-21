package shop.makaroni.bunjang.src.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.user.model.dto.PatchUserRequest;
import shop.makaroni.bunjang.src.user.model.response.ResponseInfo;

import static shop.makaroni.bunjang.src.user.model.response.SuccessStatus.PATCH_SUCCESS;
import static shop.makaroni.bunjang.src.user.model.response.SuccessStatus.WITHDRAWAL_SUCCESS;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}")
    public ResponseEntity<ResponseInfo> update(@PathVariable("userId") Long userId, @RequestBody PatchUserRequest request) {
        userService.update(userId, request);
        return ResponseEntity.ok(ResponseInfo.of(PATCH_SUCCESS));
    }

    @PatchMapping("/d/{userId}")
    public ResponseEntity<ResponseInfo> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok(ResponseInfo.of(WITHDRAWAL_SUCCESS));
    }
}
