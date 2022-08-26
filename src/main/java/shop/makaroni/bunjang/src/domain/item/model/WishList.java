package shop.makaroni.bunjang.src.domain.item.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WishList {
    private String userIdx;
    private String name;
    private String image;
}
