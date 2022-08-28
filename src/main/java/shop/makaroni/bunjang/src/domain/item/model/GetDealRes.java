package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDealRes {
    private String itemIdx;
    private String image;
    private String name;
    private String price;
    private String updatedAt;
    private String chat;
    private String hit;
    private String reviewIdx;
    private String location;
}
