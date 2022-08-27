package shop.makaroni.bunjang.src.domain.item.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetWisherRes {
    private ItemPreview items;
    private int wishCnt;
    private List<WishList> wishList;
}
