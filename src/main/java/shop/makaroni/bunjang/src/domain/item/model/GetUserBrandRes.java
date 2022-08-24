package shop.makaroni.bunjang.src.domain.item.model;

        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserBrandRes {
    private String logo;
    private String brandIdx;
    private String brandName;
    private String englishName;
    private String itemCnt;
}
