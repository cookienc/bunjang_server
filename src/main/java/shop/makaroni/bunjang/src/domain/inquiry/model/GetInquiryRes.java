package shop.makaroni.bunjang.src.domain.inquiry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetInquiryRes {
    private String idx;
    private String userIdx;
    private String image;
    private String userName;
    private String post;
    private String updatedAt;
}
