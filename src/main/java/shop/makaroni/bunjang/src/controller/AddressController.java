package shop.makaroni.bunjang.src.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.config.BaseResponse;
import shop.makaroni.bunjang.src.domain.address.model.AddrClient;
import shop.makaroni.bunjang.utils.JwtService;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private final AddressProvider addressProvider;
//    @Autowired
//    private final AddressService addressService;
    @Autowired
    JwtService jwtService;

    @Value("${kakao.rest_api_key}")
    private String restApiKey;

    public AddressController(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
//    public BaseResponse<List<String>>
        public void getAddr(@RequestParam() String q,
                                  @RequestParam() Integer page) {
        String reqURL = "dapi.kakao.com/v2/local/search/address";
//                +q+"&page="+ page;

//        try {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(reqURL, String.class);

            System.out.println(result);
//        }
//        } catch (BaseException exception) {
//
//
//        }
    }
}
