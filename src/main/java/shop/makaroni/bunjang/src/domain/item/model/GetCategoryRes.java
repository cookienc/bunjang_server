package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.AbstractList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCategoryRes {
    List<GetSubcategoryRes> subCategory;
    List<GetSearchRes> items;

    public GetCategoryRes() {
        ;
    }
}
