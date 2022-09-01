package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCategoryRes {
    private String name;
    private String image;
    private List<GetSubcategoryRes> subCategory;
    private List<GetSearchRes> items;

    public GetCategoryRes() {
        ;
    }
}
