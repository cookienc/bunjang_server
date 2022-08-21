package shop.makaroni.bunjang.src.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    private Long idx;
    private String loginId;
    private String password;
    private String storeName;
    private String contactStart;
    private String contactEnd;

    private Boolean isCertificated;
    private String storeUrl;
    private String storeImage;
    private String description;
    private String policy;
    private String precaution;

    private String createdAt;
    private String updatedAt;
    private String status;

    @Builder
    public User(Long idx, String loginId, String password, String storeName, String contactStart, String contactEnd, Boolean isCertificated, String storeUrl, String storeImage, String description, String policy, String precaution, String createdAt, String updatedAt, String status) {
        this.idx = idx;
        this.loginId = loginId;
        this.password = password;
        this.storeName = storeName;
        this.contactStart = contactStart;
        this.contactEnd = contactEnd;
        this.isCertificated = isCertificated;
        this.storeUrl = storeUrl;
        this.storeImage = storeImage;
        this.description = description;
        this.policy = policy;
        this.precaution = precaution;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
