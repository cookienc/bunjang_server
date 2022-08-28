package shop.makaroni.bunjang.src.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.makaroni.bunjang.src.provider.ItemProvider;
import shop.makaroni.bunjang.src.service.ItemService;
import shop.makaroni.bunjang.utils.JwtService;

@Transactional
@RestController
@RequestMapping("/inquiry")
public class InquiryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final ItemProvider itemProvider;
    @Autowired
    private final ItemService itemService;
    @Autowired
    JwtService jwtService;

    public InquiryController(ItemProvider itemProvider, ItemService itemService, JwtService jwtService) {
        this.itemProvider = itemProvider;
        this.itemService = itemService;
        this.jwtService = jwtService;
    }


}