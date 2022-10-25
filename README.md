# 프로젝트명 : Sporty Note

> : 헬스, 이제 방법과 기록을 한번에! 초보자도 쉽게 따라하고 성장할 수 있는 운동 애플리케이션

<br> - 소프트웨어 마에스트로 13기 인증티켓팀 SportyNote 어플리케이션 서버

<p align="center">
  <br>
  <img width="400" alt="image" src="https://user-images.githubusercontent.com/57786933/196044691-93ad7348-2029-4258-b10a-adb7efca5838.jpg">

  <br>
  <img width="400" alt="image" src="https://user-images.githubusercontent.com/57786933/196044702-7b0689b1-8dc0-4570-b94e-66cdde73aca0.jpg">
  <img width="400" alt="image" src="https://user-images.githubusercontent.com/57786933/196044698-0e1168ba-3e29-4648-86e0-e752d8628f77.jpg">
  <br>
   <a href= "https://sporty.imweb.me/">랜딩페이지 바로 가기</a>
</p>


## ✅  프로젝트 소개

### 프로젝트 구조

<img width="820" alt="image" src="https://user-images.githubusercontent.com/47708717/197838310-f97f804a-4546-4725-9b35-4b3e0acbe7a7.png">

<p align="justify">
### 프로젝트 개요/동기
</p>
1) 헬스장에서 기구 사용 방법에 대한 접근이 어려움. <br>
- 간편하게 확인할 수 있는 기구 사용법에 대한 직관적인 시각 자료 제공. <br><br>
2) 개인 특성에 맞는 운동 방식이 달라서 개인 강습을 받지만, 해당 내용을 단기간에 체득하기까지는 어려움이 있음. <br>
- 학습 내용에 대한 리마인드를 위한 노트 솔루션 제공<br>
<p align="center">
</p>

<br>

## ✅  기술 스택

| Java | SpringBoot |  JPA   |  JPQL   |  QueryDSL   |   MySQL   |   Redis  |  AWS S3/EC2/CDN/CodePipeline   |
| :--------: | :--------: | :------: | :-----: |:------: |:------:  |:------: |:------: |
<!--|   ![js]    |   ![ts]    | ![react] | ![node] |-->

<br>

## 구현 기능

### 기능 1
- Spring Data JPA, JPQL, QueryDSL을 사용하여 일대다, 다대일 관계에서의 다양한 상황의 DB 쿼리를 처리함
- 'N+1 문제' 대해서 fetch join을 사용하여 성능 개선함
- Optional을 사용하여 NullpointerException과 같은 오류를 예외처리함
### 기능 2
- REST API에 대하여 학습하고 Swagger API를 사용하여 API 명세서를 작성하여 프론트 개발자와 협업함
- Swagger API, Postman을 사용하여 API를 개발하고 이를 문서화하여 프론트 개발자와 협업함
### 기능 3
- 배포를 하기 위해 특정 키 값들을 암호화를 하여 배포하는 방식에 대해서도 찾아 @Jasypt 를 사용함
- 배포 환경을 고려하여 브랜치 및 properties를 나누어서 개발함

### 기능 4
- JWT 토큰 방식을 이용한 OAuth2를 통해 소셜 로그인 기능을 개발함
- Redis를 사용하여 토큰을 관리함
<br>

## ✅  배운 점 & 아쉬운 점
- 도메인을 결정하고, 프로젝트 아키텍처 및 DB 설계의 과정을 통해 많은 고민과 숙련된 경험을 얻을 수 있었습니다.
- `Jira`를 사용하여 스토리 포인트와 스프린트 방식을 진행해보았으나, 프로젝트 진행 인원이 3명인 것을 감안하여 효율성을 감안하여 `Notion`으로 회의 및 멘토링 내용들을 정리하며 좀 더 원활하게 프로젝트를 수행 할 수 있었습니다.
- Spring Data JPA에서 페이징(Paging)을 사용해보지 못한 것이 아쉽습니다. 추후 Machine의 조회 과정에서 사진을 다수 불러오기 때문에 이에 대하여 Paging을 도입할 계획이 있습니다.
- `Jenkins`를 이용하여 CI/CD를 구성하려 하였지만 최종 Gitlab 인증 과정에서 실패하여 `AWS ECS`, `CodePipeline`, `Gitlab->GitHub 미러링 방식`을 사용하여 CI/CD를 구성할 수 있었습니다.
- `Redis`의 기본 포트에서 인증없이 접근이 가능하게 하여, 해킹 시도를 당했지만 도커 환경이었기에 피해는 입지 않았습니다. 이러한 실수를 방지하기 위해 인증과 ACL을 간과해서는 안된다는 것을 깨달았습니다.

<p align="justify">

</p>

<br>


<!-- Stack Icon Refernces -->
[js]: /images/stack/javascript.svg
[ts]: /images/stack/typescript.svg
[react]: /images/stack/react.svg
[node]: /images/stack/node.svg
