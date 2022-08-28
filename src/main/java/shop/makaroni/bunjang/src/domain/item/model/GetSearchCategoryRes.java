package shop.makaroni.bunjang.src.domain.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
public class GetSearchCategoryRes {
    private String image;
    private String code;
    private String parent;
    private String name;
    public GetSearchCategoryRes(){
        ;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
}
