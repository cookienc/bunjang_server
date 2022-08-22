package shop.makaroni.bunjang.src.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.domain.user.StoreSaleResponse;
import shop.makaroni.bunjang.src.domain.user.dto.MyStoreResponse;
import shop.makaroni.bunjang.src.domain.user.dto.PatchUserRequest;
import shop.makaroni.bunjang.src.provider.UserProvider;
import shop.makaroni.bunjang.src.response.ResponseInfo;
import shop.makaroni.bunjang.src.service.UserService;

import java.util.List;

import static shop.makaroni.bunjang.src.response.SuccessStatus.PATCH_SUCCESS;
import static shop.makaroni.bunjang.src.response.SuccessStatus.WITHDRAWAL_SUCCESS;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserProvider userProvider;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<MyStoreResponse> getMyStore(@PathVariable Long userId) {
        return ResponseEntity.ok(userProvider.getMyStore(userId));
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<StoreSaleResponse>> getMyStoreItem(@PathVariable Long userId, @RequestParam("condition") String condition) {
        return ResponseEntity.ok(userProvider.getMyStoreItem(userId, condition));
    }

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
