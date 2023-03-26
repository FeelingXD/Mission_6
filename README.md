# fastlms

5주차 선택 과제내용입니다.

### 사용기술

- jquery
- javascript
- thymeleaf
- spring boot
- spring security
- maria db
- docker

### Banner

구현내용은 category 를 참고하여 구현하였습니다.

Configuration 경로의 webMvcConfig 를 추가하여 files 의 외부 접근경로를 추가하였습니다.

상대경로를 구현하려고하였으나 몇가지 제약이 있어 application.yml에 경로를 설정하여 진행하였습니다.(저장경로)

### UserLogin history

Configuration 의 success handler를 추가하고 성공시에 로그인 히스토리가 새로 생성 되도록 저장하였습니다.

로그인 히스토리생성시 ip 가 ipv6로 저장되는게 기본값이기에 테스트 환경에서는 인텔리제이의 VM옵션에
-Djava.net.preferIPv4Stack=true
-Djava.net.preferIPv4Addresses=true
를 추가하여 저장하도록하였습니다.
