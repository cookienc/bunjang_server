# 번개장터 
+ 기간 : 2022.08.20~2022.09.02
+ 내용 : 중고거래 어플 백엔드 개발

## 개발일지
###  2022/08/20
### 에단
- [x] 서버 구축
- [x] 서버 도메인 및 서브 도메인
- [x] 도메인 리다이렉트 및 https 보안 설정
- [x] ERD 작업중~ 

### 워니
- [x] 서버 구축 및 보안 설정 진행
- [x] 도메인 및 서브도메인 설정
- [x] ERD 설계
- [x] Post 도메인 API 설계 진행중
---
###  2022/08/21
### 에단
- [x] 회원 수정 api
- [x] 회원 삭제 api
- [x] 회원 수정 / 삭제 api validation

### 워니
- [x] 더미데이터 생성
- [x] 상품 검색 API


---
###  2022/08/22
### 에단
- [x] 사용자가 판매하는 아이템 목록 조회
  - [x] 페이징 및 날짜 정렬 조건 추가
- [x] 사용자가 판매하는 아이템 목록 검색 기능 추가

### 워니
- [x] 상품 조회(상세페이지) API
- [x] 상품 검색 API
- [x] 기존 API validation 추가
- [x] 기존 API의 Response parameter 타입들 int->String으로 변경(클라이언트 요청)
---
###  2022/08/23
### 에단
- [x] 상품 문의 사항 등록 API 추가
- [x] 회원 가입 API 추가
- [x] 중복된아이디 검증 API 추가

### 워니
- [x] 전체 상품 조회 API response 형태 수정 - 클라이언트 요청
- [x] 사용자 최근 본 상품 조회 API 추가
- [x] 사용자가 팔로우 하는 브랜드 목록 조회 API 추가
---
###  2022/08/24
### 에단
- [x] 회원 로그인 API 추가
- [x] 상점 조회 API 추가

### 워니
- [x] 유저가 팔로우하는 브랜드 조회 API 추가
- [x] 모든 브랜드 목록 조회 API 추가
- [x] 브랜드 검색 API 추가
---
###  2022/08/25
### 에단
- [x] 리뷰 조회 API 추가
- [x] 상점 조회 API 추가

### 워니
- [x] 상품 검색 API 반환값 추가(status)
- [x] 해당 카테고리의 상품 조회 및 하위 카테고리 목록 조회 API 추가
---
###  2022/08/26
### 에단
- [x] 리뷰 등록 API 추가
- [x] 리뷰 작성 가능 자격을 검사하는 API 추가
- [x] 리뷰 삭제 API 추가
- [x] 리뷰 수정 API 추가

### 워니
- [x] 상품 등록 API 추가
- [x] 상품 수정 API 추가
- [x] 상품 삭제 API 추가
- [x] 상품 상태 변경(판매중/예약중/판매완료) API 추가

---
###  2022/08/27
### 에단
- [x] 리뷰 단건 조회 API 추가
- [x] 리뷰 댓글 작성 API 추가
- [x] 리뷰 댓글 삭제 API 추가
- [x] 리뷰 답글 수정 API 추가

### 워니
- [x] 최근 본 상품 조회 response parameter 수정
- [x] 브랜드 목록 조회 및 검색 분리
- [x] 상품을 찜한 사람 목록 조회 API 추가
- [x] 사용자가 찜한 상품 조회 API 추가

---
###  2022/08/28
### 에단
- [x] 카카오 로그인 API 구현
- [x] 리뷰 답글 벨리데이션 추가
- [x] 팔로워 조회 API 추가

### 워니
- [x] 검색 키워드와 연관 키워드 조회 API 추가
- [x] jwt 검증 메소드 추가, 브랜드 조회 API 회원 API로 수정
- [x] 오류 해결 및 모든 식별자 데이터 타입 Long으로 변경
- [x] 연관 검색어 조회 API - 검색어 validation 추가 
- [x] 거래내역 조회 API 추가
---
###  2022/08/29
### 에단
- [x] 팔로잉 조회 API 추가
- [x] 팔로우 등록 API 추가
- [x] 팔로우 삭제 API 추가
- [x] 알람 설정 API 추가
- [x] 알람 해제 API 추가

### 워니
- [x] 신고 API 추가
- [x] 상점 상품 문의 조회 API 추가
- [x] 문의 등록 API 추가
- [x] 문의 삭제 API 추가
- [x] 조회 API들 페이징 처리 추가

---
###  2022/08/30
### 에단
- [x] Controller uri 수정

### 워니
- [x] 추천 상품 조회 API 추가
- [x] 검색 API 수정 - 상품 설명도 검색 가능하도록 수정
- [x] Setting 도메인 추가, 알림 설정 조회 API 추가

---
###  2022/08/31
### 에단
- [x] 문자 인증 API 구현
- [x] Jwt 인증 추가

### 워니
- [x] 알림 설정 수정 API 추가
- [x] 주소 관리 배송지 조회 API 추가
- [x]  주소 관리 배송지 등록 API 추가 
- [x]  주소 관리 배송지 수정 API 추가
- [x]  주소 관리 배송지 삭제 API 추가
- [x]  키워드 알림 - 키워드 조회 API 추가
- [x]  키워드 알림 - 키워드 등록 API 추가
- [x]  키워드 알림 - 키워드 삭제 API 추가
- [x]  키워드 알림 - 키워드 설정 수정 API 추가

---
###  2022/09/01
### 에단
- [x] 이벤트 베너 조회 API 추가 
- [x] 이벤트 단건 조회 API 추가

### 워니