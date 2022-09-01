package shop.makaroni.bunjang.src.controller;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.address.model.GetAddrressRes;
import shop.makaroni.bunjang.src.provider.AddressProvider;
import shop.makaroni.bunjang.src.service.AddressService;
import shop.makaroni.bunjang.utils.JwtService;

import java.io.UnsupportedEncodingException;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final AddressProvider addressProvider;
    @Autowired
    private final AddressService addressService;
    @Autowired
    JwtService jwtService;

    public AddressController(JwtService jwtService, AddressProvider addressProvider, AddressService addressService){
        this.jwtService = jwtService;
        this.addressProvider = addressProvider;
        this.addressService = addressService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetAddrressRes> getAddr(@RequestParam() String q,
                                                @RequestParam() Integer page)
                        throws UnsupportedEncodingException, ParseException {

        if(page <= 0){
            return new BaseResponse<>(ITEM_INVALID_PAGE);
        }
        if(q.isEmpty() || q.isBlank()){
            return new BaseResponse<>(EMPTY_SEARCH_WORD);
        }
        try {
            if (!jwtService.validateJWT(jwtService.getJwt())) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            return new BaseResponse<>(addressService.getAddr(q, page));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}