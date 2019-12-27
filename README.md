### freelec 스프링 부트로 구현하는 웹 서비스 책 따라해보기

- boot 와 JUnit 만 최신껄로 올림.
- 스프링 부트 와이프 가르쳐주기 용도로 딱인듯.

### application-oauth.properties 

~~~properties
## google
spring.security.oauth2.client.registration.google.client-id=클라이언트 ID
spring.security.oauth2.client.registration.google.client-secret=시크릿키
spring.security.oauth2.client.registration.google.scope=profile,email


## naver registration
spring.security.oauth2.client.registration.naver.client-id=클라이언트 ID
spring.security.oauth2.client.registration.naver.client-secret=시크릿키
spring.security.oauth2.client.registration.naver.redirect-uri={baseUrl}/{action}/oauth2/code/{registrationId}
spring.security.oauth2.client.registration.naver.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.naver.scope=name,email,profile_image
spring.security.oauth2.client.registration.naver.client-name=Naver

## naver provider
spring.security.oauth2.client.provider.naver.authorization_uri=https://nid.naver.com/oauth2.0/authorize
spring.security.oauth2.client.provider.naver.token_uri=https://nid.naver.com/oauth2.0/token
spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me

### 기준이 되는 user_name의 이름을 네이버에서는 response로 해야 함. 네이버의 회원 조회 시 반환되는 JSON 형태 때문
spring.security.oauth2.client.provider.naver.user_name_attribute=response
~~~

