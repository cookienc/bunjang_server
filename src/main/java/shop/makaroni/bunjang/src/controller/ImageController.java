package shop.makaroni.bunjang.src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.service.ImageService;
import shop.makaroni.bunjang.utils.JwtService;

import java.util.HashMap;
import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.INVALID_USER_JWT;
import static shop.makaroni.bunjang.config.BaseResponseStatus.ITEM_NO_EXIST;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;
    @Autowired
    JwtService jwtService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("")
    public BaseResponse<HashMap<String, String>> postFile(@RequestParam() Long item,
                                                            @RequestPart(value = "files") List<MultipartFile> files) throws BaseException {

        if (item <= 0) {
            return new BaseResponse<>(ITEM_NO_EXIST);
        }
        try {
            if (!jwtService.validateJWT(jwtService.getJwt())) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            return new BaseResponse<>(imageService.uploadFile(String.valueOf(item), files));
        }
        catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @PatchMapping("")
    public BaseResponse<HashMap<String, String>> patchFile(@RequestParam() Long item,
                                                            @RequestPart(value = "files") List<MultipartFile> files) throws BaseException {

        if (item <= 0) {
            return new BaseResponse<>(ITEM_NO_EXIST);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(imageService.modifyFile(userIdx, String.valueOf(item), files));
        }
        catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
