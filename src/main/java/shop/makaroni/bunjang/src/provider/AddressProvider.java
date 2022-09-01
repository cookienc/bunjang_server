package shop.makaroni.bunjang.src.provider;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import shop.makaroni.bunjang.src.dao.AddressDao;
import shop.makaroni.bunjang.src.domain.address.model.Address;
import shop.makaroni.bunjang.src.domain.address.model.GetAddrressRes;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AddressProvider {
    private final AddressDao addressDao;



}
