# 🧳여행자 클럽 마일리지 서비스
> 사용자들이 장소에 리뷰를 작성할 때 포인트를 부여하고, 
> 
> 전체/개인에 대한 포인트 부여 히스토리와 개인별누적 포인트를 관리하고자 합니다.

<br>

## 요구사항
### 기능 요구사항
- 리뷰 작성 시 포인트를 부여한다
- 리뷰 수정 시 수정한 내용에 맞는 포인트를 재계산하고 포인트를 부여하거나 회수한다
- 리뷰 삭제 시 포인트를 회수한다
- 포인트 증감이 있을 때마다 이력이 남아야한다
- 사용자의 현재 시점의 포인트 총점을 조회할 수 있다

### 포인트 요구사항
- (내용점수) 1자 이상 텍스트 : 1점
- (내용점수) 1장 이상 사진 : 1점
- (보너스) 특정 장소에서 첫 리뷰 : 1점
	- 사용자 A가 리뷰를 남겼다가 삭제하고 이후에 사용자 B가 리뷰를 남기면 B에 보너스를 부여한다
	- 사용자 A가 리뷰를 남겼다가 삭제하기 이전에 사용자 B가 리뷰를 남기면 B에 보너스를 부여하지 않는다

### 기타 요구사항
- 서비스에 필요한 스키마를 설계한다
- 포인트 부여 SQL 수행 시, 전체 테이블 스캔이 일어나지 않아야한다
- 리뷰 작성이 이뤄질때마다 리뷰 작성 이벤트가 발생하고, 아래 API로 이벤트를 전달한다
- 리뷰 생성 이벤트의 경우 "ADD" , 수정 이벤트는 "MOD" , 삭제 이벤트는 "DELETE" 값을 가진다
```json
{
  "type": "REVIEW",
  "action": "ADD", /* "MOD", "DELETE" */
  "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "content": "좋아요!",
  "attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"],
  "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```

<br>

## 실행 방법
### 데이터베이스
(MySQL Workbench 8.0 기준)
1. DDL.sql 다운
2. Workbench 왼쪽 상단 File > Open SQL Script..를 눌러 sql 오픈
3. 실행버튼(번개 모양)을 눌러 스키마 생성

### 프로젝트
(intelliJ Community Edition IDE 기준)
1. git clone
2. intellij 실행 후 open project
3. src/main/resources/application.yml의 datasource(url, username, password)부분을 테스터의 환경에 맞게 수정
4. src/main/java/noh/clubmservice/ClubmserviceApplication.java의 실행 버튼(녹색 삼각형)을 눌러 서버 실행
5. localhost:8080으로 접속

<br>

## API Spec
|FUCNTION|METHOD|URI|PARAM|BODY|ACTION|DESC|
|--------|------|---|-----|----|-----|----|
|포인트부여|POST|/events|-|EventReqDTO|ADD|리뷰점수계산 및 포인트 부여|
|포인트수정|POST|/events|-|EventReqDTO|MOD|리뷰점수계산 및 포인트 부여|
|포인트회수|POST|/events|-|EventReqDTO|DELETE|리뷰점수계산 및 포인트 부여|
|포인트총합조회|GET|/point/{id}|userId|-|-|사용자 현재시점의 포인트 총합 조회|
|포인트이력조회|GET|/history/{id}|userId|-|-|사용자 포인트 증감 내역 조회|
