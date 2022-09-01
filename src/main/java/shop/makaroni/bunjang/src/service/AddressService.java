package shop.makaroni.bunjang.src.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import shop.makaroni.bunjang.src.dao.AddressDao;
import shop.makaroni.bunjang.src.domain.address.model.Address;
import shop.makaroni.bunjang.src.domain.address.model.GetAddrressRes;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
//    private final AddressDao addressDao;
    @Value("${kakao.rest_api_key}")
    private String restApiKey;
    public GetAddrressRes getAddr(String q, Integer page) throws UnsupportedEncodingException, ParseException {
        List<Address> addresses = new ArrayList<>();
        String reqURL = "https://dapi.kakao.com/v2/local/search/address";
        String queryString = "?query=" + URLEncoder.encode(q, "UTF-8") + "&page=" + page + "&size=12"; //+"&size="+searchVO.getPageSize()

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", restApiKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        URI url = URI.create(reqURL + queryString);
        RequestEntity<String> rq = new RequestEntity<>(headers, HttpMethod.GET, url);
        ResponseEntity<String> response = restTemplate.exchange(rq, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject body = (JSONObject) jsonParser.parse(response.getBody().toString());
        JSONArray documents = (JSONArray) body.get("documents");
        JSONObject metaData = (JSONObject) body.get("meta");
        for (Object document : documents) {
            JSONObject tmp = (JSONObject) document;
            addresses.add(new Address((String) tmp.get("address_name"), (String) tmp.get("x"), (String) tmp.get("y")));
        }
        return (new GetAddrressRes(metaData.get("total_count").toString(),addresses));
    }
}
