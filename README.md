# POSTORY-검색형 웹툰 웹소설 플랫폼


작가들 번아웃과 독자들 피로감을 해결하기 위한 구글식 컨텐츠 플랫폼입니다.  
내용을 검색하고, 마음에 드는 채널을 구독하고, 보고 싶은 내용을 스크랩합니다.

## 프로젝트 설명
지속가능한 웹툰 웹소설 플랫폼을 만들고자 하였습니다.  
그러기 위해 크리에이터에게 비교보단 자기 자신 작품에 집중하는 환경을 만들어주고,  
독자에겐 주어진 작품을 보기보단 원하는 작품을 찾아 보관하는 경험을 제공했습니다.  
독자와 크리에이터, 크리에이터와 크리에이터, 작품과 작품 간 상호작용이 많아질 수록  
비슷한 작품만 만들어지고, 그런 작품 중 수작이 나오는 건 규모가 큰 기업만 가능하다는 판단을 했기 때문입니다.

### 사용한 기술들

1. Back-end

	처음엔 Spring Legacy를 maven 방식으로 빌드했습니다. 그리고 spring security를  적용했습니다. 그리고 완성한 영역 외에 결제, 멤버십, 상품, 포인트 등 모두 68개 테이블을 설계했었습니다.

	spring security를 spring legacy 환경에서 사용할 수 있게 했으나,
	팀원 모두가 이해하기엔 어렵다 판단해 spring boot로 전환했습니다. 프로젝트 완성 기한을 맞추기 위해 앞서 언급한 상당수 기능 구현을 포기했습니다. 더 자세한 이유는 링크로 첨부합니다.  
	https://velog.io/@iamloved5959/Spring-Legacy%EC%99%80-React%EC%97%90%EC%84%9C-spring-security-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0

	최종적으로 사용한 기술은 다음과 같습니다.
	* Spring Boot

		spring security 적용을 모두가 쉽게 할 수 있어 사용했습니다.
	* Spring Security

		JWT 방식을 사용하기 쉬워 사용했습니다.
	* Mybatis

		쿼리를 연습할 시간이라 판단하여 사용했습니다.
	* gradle

		추후 서비스를 확장할 때 변경을 쉽게 할 수 있게 maven보다 gradle을 선택했습니다.
	* MySql

		AWS 서비스들과 연동이 쉬워 사용했습니다.
	* AWS RDS

		공용 database를 위해 선택했습니다. 공식문서와 유저풀이 풍부해 자료를 찾기 쉬웠습니다.
	* AWS S3

		이미지 업로드를 하기 위해 사용했습니다. 처음엔 AWS 공부할 여유가 없다 판단해 local react 서버에 저장하는 방법을 택했습니다. 그러나 client가 server 컴퓨터 경로를 알아낼 수 있다는 점과 리액트 서버가 무거워진다는 단점이 있어 AWS S3를 도입했습니다. 
	* AWS EC2

		배포를 하기 위해 이용한 서비스입니다. 다른 서비스들보다 현장에서 많이 사용하는 서비스였고, 공식문서와 그외 자료들에 접근하기 쉬웠습니다. 또한 웹툰 웹소설 플랫폼 특성상 많은 트래픽 처리를 하게 될 거라 판단했습니다.  접근성과 중요성, 확장성을 모두 만족하는 서비스이기에 선택했습니다.  


3. Front-end

	팀원이 3명밖에 되지 않아 재사용성에 초점을 뒀습니다.

	팀 프로젝트를 위해 상품 게시판, 상품 뷰, 결제, 주문, 주문 목록, 주문 상세를 포기했습니다.

	프로젝트를 기한 내 맞추기 위해 팀원들이 사용할 수 있게 자체 제작 form library를 만들었습니다.  
	참조하는 POSTYPE 사이트 폼 형식에 패턴을 발견했고, 변수만 바꿔서 사용하면  개발 시간이 단축된다 판단했기 때문입니다.  
	백엔드 개발자 취업이 목표기에 렌더링 시간은 고려 대상이 아니었기 때문이기도 합니다.  
	그러나 짧은 시간에 팀원들을 설득하지 못해 사용하지 못했습니다.  
	폼 라이브러리 자료는 컴퓨터 백업 문제로 자료가 날라갔습니다. 자료를 복원시키는 즉시 링크를 첨부하겠습니다.

	* React, JSX

		재사용성을 위해 javascript보다 React를 이용한 JSX를 사용했습니다.
	* styled-components

		위와 같은 이유로 html, css보다  styled-componets를 사용했습니다.
	* javascript, html, css

		위 기술들을 보완하는 차원에서 사용했습니다.
		동적으로 조건을 판단할 때 javascript를 사용했고, 포스트 내용을 통으로 저장할 때 html, css를 사용했습니다.
	* react quill

		핸들러 기능을 사용하고 에디터 객체에 접근하기 쉬워 사용했습니다. DB 비용을 줄여야 했습니다.  
		그렇기 위해서 base64로 나타나는 이미지 태그 src 값을 짧은 String URL로 변환해야 했습니다.  
		react quill은 업로드하는 이미지에 이미지 핸들러로 접근할 수 있게 해줬고,  
		그 이미지 base64 src 값만 서버에 보내 짧은 String 고유값(URL)으로 변환했습니다.  
		S3 서버에 업로드한 후, 고유값으로 바뀐  URL을  client에게 전달했습니다.  
		그리고 에디터 객체에 접근해 현재 커서가 있는 곳 위치에 src를 이 값으로 바꿨습니다.    
		포스트를 수정할 때도 위 편의 기능들을 유용하게 썼습니다.  
	* AWS EC2

		백엔드에서 서술했던 내용과 동일합니다.
	* nginix

		동시에 여러명이 이용하는 웹툰 웹소설 플랫폼 특성을 고려해 비동기 통신에 효율적인 nginix를 이용했습니다.
	* yarn

		팀원 간 컴퓨터 환경이 달랐기에 yarn을 이용해 package를 관리했습니다.
	* ZUSTAND

		로그인한 유저 정보와 같이 전역변수를 관리하기 위해 사용했습니다.

## 프로젝트 설치 및 실행 방법
develop branch 기준으로 진행해주세요.(main, dev_kjh는 완성본이 아닙니다.)  

1. git clone으로 코드를 받아주세요.
   ``` 
   git clone git clone -b develop https://github.com/dancingKim/postory_back.git
   ```

2. 아직 도메인을 사진 않았습니다. 현재 AWS 계정 비밀번호를 아는 것 외에 프로그램을 로컬에서 실행시킬 수 있는 방법이 없습니다. 추후 도메인을 산 후에 업데이트 하겠습니다.
3. 불편하시더라도 코드 위주로 봐주세요.
## 프로젝트 사용 방법



원하는 키워드로 검색을 합니다.
![home_webtoon](https://github.com/dancingKim/postory_back/assets/111945532/4b8a77df-2d56-48c4-877d-ae97fa3fb7d7)

만화 키워드로 웹툰을 검색했습니다.
관심 포스트를 스크랩합니다.

![search_webtoon](https://github.com/dancingKim/postory_back/assets/111945532/ae910869-8a02-41e4-bc18-70b61d2134ff)


보관함에서 스크랩한 포스트를 모아볼 수 있습니다.

![save_scrap](https://github.com/dancingKim/postory_back/assets/111945532/a0d2ba9b-f044-46b3-b38d-297bcf340ec4)


채널을 검색해 들어갈 수도 있습니다.
채널을 구독할 수도 있습니다.
구독한 채널과 채널이 발행한 포스트들은 구독 탭에서 볼 수 있습니다.

![other_webnovel_home](https://github.com/dancingKim/postory_back/assets/111945532/31fa729b-e2cd-4132-8554-bb856222994c)


내 채널에선 포스트를 발행할 수 있습니다.
구독버튼 자리에 포스트 발행하기 버튼이 뜹니다.

![channel_home](https://github.com/dancingKim/postory_back/assets/111945532/626a66e7-1891-40f5-84c5-dd7537941f9e)


웹툰, 웹소설 타입으로 포스트를 발행할 수 있습니다.

![post_create](https://github.com/dancingKim/postory_back/assets/111945532/ed92b0f1-d629-48eb-ae22-8f853d5245d9)

포스트를 보고, 마음에 안 들면 수정할 수도 있습니다.
이미지를 추가해 보았습니다.

![edited_post_view](https://github.com/dancingKim/postory_back/assets/111945532/d5a0c3cd-47b8-4c3d-9600-100f84e92d4f)


## 팀원 및 참고 자료
### 팀 소개
1. 팀장
황인태

2. 팀원
김정호
강세민

### 참고 자료
### 참조 사이트
POSTYPE
### 참조 컨텐츠
NAVER WEBTOON

https://comic.naver.com/webtoon/list?titleId=769209

https://comic.naver.com/webtoon/list?titleId=687915

https://comic.naver.com/webtoon/list?titleId=809054

https://comic.naver.com/webtoon/list?titleId=807178

https://comic.naver.com/webtoon/list?titleId=747269

https://comic.naver.com/webtoon/list?titleId=758662

NAVER WEBNOVEL

https://novel.naver.com/webnovel/list?novelId=1062250

https://novel.naver.com/webnovel/list?novelId=1073928

https://novel.naver.com/webnovel/list?novelId=949763

https://novel.naver.com/webnovel/list?novelId=1058954

https://novel.naver.com/best/list?novelId=22398

OTHERS

https://bbs.ruliweb.com/family/212/board/300063/read/30643759

https://cafe.naver.com/steamindiegame/5396703

https://softwaremill.com/programmers-day-programming-memes-2022/

https://velog.io/@heelieben/%EA%B0%9C%EB%B0%9C-%EA%B3%B5%EA%B0%90-%EC%A7%A4-%EA%B5%AC%EA%B2%BD%ED%95%98%EA%B3%A0-%EA%B0%80%EC%84%B8%EC%9A%94

## 라이센스

“This project is licensed under the terms of the MIT license.”
