package shop.makaroni.bunjang.src.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.inquiry.model.*;
import shop.makaroni.bunjang.src.provider.InquiryProvider;
import shop.makaroni.bunjang.src.service.InquiryService;
import shop.makaroni.bunjang.utils.JwtService;

import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.INQUIRY_INVALID_TYPE;
import static shop.makaroni.bunjang.config.BaseResponseStatus.INVALID_USER_JWT;

@Transactional
@RestController
@RequestMapping("/inquiry")
public class InquiryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final InquiryProvider inquiryProvider;
    @Autowired
    private final InquiryService inquiryService;
    @Autowired
    JwtService jwtService;

    public InquiryController(InquiryProvider inquiryProvider, InquiryService inquiryService, JwtService jwtService) {
        this.inquiryProvider = inquiryProvider;
        this.inquiryService = inquiryService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("{targetIdx}")
    public BaseResponse<List<GetInquiryRes>> getInquiry(@PathVariable("targetIdx") Long targetIdx,
                                                        @RequestParam() char type ) {
        if(!(type=='S'||type=='I')){
            return new BaseResponse(INQUIRY_INVALID_TYPE);
        }
        try {
            if(!jwtService.validateJWT(jwtService.getJwt())){
                return new BaseResponse(INVALID_USER_JWT);
            }
            return new BaseResponse<>(inquiryProvider.getInquiry(targetIdx, type));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}