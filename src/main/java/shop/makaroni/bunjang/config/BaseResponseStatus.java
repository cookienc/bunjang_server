package shop.makaroni.bunjang.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false, 2003, "권한이 없는 유저의 접근입니다."),
    EMPTY_SEARCH_WORD(false, 2004, "검색어를 입력해주세요"),
    INVALID_SEARCH_WORD(false, 2005, "검색어 길이를 확인하세요"),


    // users
    USERS_EMPTY_NAME(false, 2008, "사용자의 상점 이름을 입력하세요"),
    USERS_IMAGE(false, 2009, "사용자의 상점 이미지 경로를 입력하세요."),
    USERS_INVALID_IDX(false, 2010, "유저 아이디 값을 확인해주세요."),


    // [POST] /Item
    POST_ITEM_INVALID_SELLER(false, 2012, "판매자 아이디를 확인해주세요"),
    POST_ITEM_INVALID_CONTENT(false, 2013, "상품 설명은 10글자 이상 200글자 이하로 입력해주세요"),
    POST_ITEM_INVALID_STOCK(false, 2014, "수량은 1개 이상 입력해주세요"),
    POST_ITEM_INVALID_NAME(false, 2015, "상품명은 2글자 이상 40자 이하로 입력해주세요"),
    POST_ITEM_EMPTY_IMAGE(false, 2016, "상품 사진을 등록해주세요."),
    POST_ITEM_INVALID_CATEGORY(false, 2017, "유효하지 않은 카테고리 코드값입니다"),
    POST_ITEM_INVALID_PRICE(false,2018, "금액은 100원이상 100000000이하 입력해주세요"),
    POST_ITEM_INVALID_SAFEPAY(false, 2019, "유효하지 않은 안전결제 상태값입니다"),


    // items
    ITEM_NO_EXIST(false, 2021, "존재하지 않는 상품 아이디 값입니다."),
    ITEM_NO_NAME(false, 2022, "검색어를 입력해주세요"),

    ITEM_INVALID_CATEGORY(false, 2024, "잘못된 카데고리 값입니다."),
    ITEM_INVALID_BRAND(false, 2025, "잘못된 브랜드 아이디 값입니다."),
    ITEM_INVALID_PRICE_MIN(false, 2026, "최소 금액 값을 확인해주세요"),
    ITEM_INVALID_SORT(false, 2027, "잘못된 정렬 기준 값입니다"),

    ITEM_INVALID_PERIOD(false, 2028, "잘못된 기간입니다"),
    ITEM_INVALID_PAGE(false, 2029, "조회하고자 하는 페이지 번호를 입력해주세요"),


    ITEM_INVALID_DELIVERY(false, 2030, "잘못된 배송비 포함 여부 값입니다"),


    // Report
    POST_REPORT_INVALID_TYPE(false, 2040, "유효하지 않은 신고유형입니다."),
    POST_REPORT_INVALID_TARGET(false, 2041, "신고 대상의 식별자를 확인해 주세요."),
    POST_REPORT_CONTENT_LENGTH(false, 2042, "신고 내용은 1자 이상 100자 이하로 입력하세요"),

    // Inquiry
    INQUIRY_INVALID_TYPE(false, 2051, "문의 작성 대상을 확인해 주세요"),
    INQUIRY_INVALID_TARGET(false, 2052, "문의 작성 대상의 식별자를 확인해 주세요"),
    INQUIRY_POST(false, 2053, "문의 내용은 1자 이상 100자 이하로 입력하세요"),
    INQUIRY_INVALID_PARENTIDX(false, 2054, "답글을 달고자 하는 상위 댓글의 식별자값이 유효하지 않습니다."),

    //Setting
    SETTING_INVALID_TIME(false, 2061, "유효하지 않은 시간 설정입니다"),
    SETTING_INVALID_SILENCE(false, 2062, "방해금지 시간 설정을 활성화하지 않은 경우 시작시간과 종료시간을 설정할 수 없습니다"),
    SETTING_INVALID_NAME(false, 2063, "이름은 1자 이상 20자 이하로 입력해주세요"),
    SETTING_INVALID_PHONE_NUM(false, 2064, "유효하지 않은 전화번호입니다"),
    SETTING_INVALID_ADDRESS(false, 2065, "주소 및 상세주소를 입력해주세요"),
    SETTING_EMPTY_DEFAULT(false, 2066, "기본주소 설정 여부를 입력해주세요"),
    SETTING_INVALID_ADDR_IDX(false ,2067, "배송지 식별자를 확인해주세요"),
    SETTING_ADDRESS_CAPACITY(false, 2068, "배송지는 최대 10개까지 등록 가능합니다"),
    SETTING_INVALID_CATEGORY(false, 2069, "유효하지 않은 카테고리 코드값입니다"),
    SETTING_INVALID_PRICE(false, 2070, "유효하지 않은 가격 제한 값입니다"),
    SETTING_KEYWORD_CAPACITY(false, 2071, "키워드는 최대 50개까지 등록 가능합니다"),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false, 3014, "없는 아이디거나 비밀번호가 틀렸습니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false, 4014, "유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),
    ;


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
