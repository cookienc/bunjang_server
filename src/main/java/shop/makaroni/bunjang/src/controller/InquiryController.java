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

import java.util.HashMap;
import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;

@Transactional
@RestController
@RequestMapping("/inquiries")
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
    @ResponseBody
    @PostMapping("")
    public BaseResponse<HashMap<String, String>> PostInquiry(@RequestBody PostInqueryReq postInqueryReq) {
        if(postInqueryReq.getTargetIdx() <= 0){
            return new BaseResponse<>(INQUIRY_INVALID_TARGET);
        }
        if(postInqueryReq.getPost().length() > 100 || postInqueryReq.getPost().isEmpty()){
            return new BaseResponse<>(INQUIRY_POST);
        }
        if(!(postInqueryReq.getType().equals("S")|| postInqueryReq.getType().equals("I"))){
            return new BaseResponse<>(INQUIRY_INVALID_TYPE);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(inquiryService.PostInquiry(userIdx, postInqueryReq));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @DeleteMapping("")
    public BaseResponse<HashMap<String, String>> DeleteInquiry(@RequestParam() Long idx) {
        if(idx <= 0){
            return new BaseResponse<>(REQUEST_ERROR);
        }
        try {
            Long userIdx = jwtService.getUserIdx();
            return new BaseResponse<>(inquiryService.DeleteInquiry(userIdx,idx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}