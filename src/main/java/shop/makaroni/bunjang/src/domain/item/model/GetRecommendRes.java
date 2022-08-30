package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRecommendRes {
    private String idx;
    private String price;
    private String name;
    private String location;
    private String updatedAt;
    private String safePay;
    private String wishCnt;
    private Boolean isWished;
    private String image;
}
