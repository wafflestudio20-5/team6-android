# 💽 Wafflemate

TodoMate 어플을 참고하여 Wafflemate 어플을 제작했습니다.
<br/>
<br/>
기존 앱에 없는 **팔로우차단 기능**, **시간체크 기능**을 추가하여 SNS 기능을 강화하고 일정관리 사용성을 높혔습니다.

---

<center><img src = "https://user-images.githubusercontent.com/40379446/216377971-6cd45f39-484d-4ad9-9451-973dae12fdea.png"></center>


## 👭"와플메이트" 는 어떤 앱인가요?

친구들과 함께하는 일정관리, 와플메이트!
<br/>
<br/>
일정관리와 더불어 팔로우 팔로잉이 가능한 SNS 기능을 제공합니다.
<br/>
일정/일기 공유를 통해 효과적인 일정관리를 경험하세요.
<br/>
<br/>

## 🔀 워크플로우
![image](https://user-images.githubusercontent.com/40379446/216748806-38cec0ff-630f-4585-86b7-67db31572757.png)

## 🚀 기술스택
![image](https://user-images.githubusercontent.com/40379446/216749114-6e54a6b7-031a-4be4-8c53-ff81a3cd61d7.png)


## 🔨 만든 사람
| [송건우(Gitsgwoo)](https://github.com/Gitsgwoo) | [진채연(hzlcodus)](https://github.com/hzlcodus) | [오동건(ho2921ho)](https://github.com/ho2921ho) |



## 📄 페이지 설명

---
[송건우(Gitsgwoo)](https://github.com/Gitsgwoo)
<br>

### 1. 시작 페이지
![슬라이드1](https://user-images.githubusercontent.com/40379446/216739447-c8828df3-4d10-4fd5-a272-13151c8a1e78.PNG)

### 2. 회원가입 페이지
![슬라이드2](https://user-images.githubusercontent.com/40379446/216739452-2370377a-da14-46fc-a119-4ee94cd75fd8.PNG)

### 3. 이메일 인증 페이지
![슬라이드3](https://user-images.githubusercontent.com/40379446/216739456-be94b0db-831c-49ab-9190-a78cb4c50994.PNG)

### 4. 로그인 페이지
![슬라이드4](https://user-images.githubusercontent.com/40379446/216739458-9fcc461e-e6c8-4b29-a5ef-ac1c3526df0e.PNG)
<br/>

#### 자동 로그인 
- 사용자 로그인 요청 처리 시에 앱의 shared preference에 access token, refresh token, 사용자 정보를 저장합니다. <br/>
- 인증 정보를 나타내는 data class AuthInfo 를 만들고 인증 정보를  AuthStorage class의 변수 authInfo에 StateFlow<AuthInfo>의 형태로 저장합니다. <br/>
- 앱의 startdestination을 메인페이지로 설정해놓고, 메인페이지의 lifecycle 중 OnViewCreated 에서 사용자의 authInfo 값이 null인지 확인합니다. <br/>
- 값이 null이라면 로그인 요청을 처리하는 중첩그래프 login_graph로 navigate 됩니다. <br/>
 <br/>
login_graph : 시작 페이지, 회원가입 페이지, 이메일 인증 페이지, 로그인 페이지로 구성

### 5. 구글 로그인 페이지
![슬라이드5](https://user-images.githubusercontent.com/40379446/216739460-1e77eb2c-91a7-45f7-8728-790b2cc1d4bd.PNG)

### 6. 카카오 로그인 페이지
![슬라이드6](https://user-images.githubusercontent.com/40379446/216739462-d7f6ebf2-d816-4ff4-9bd8-09c7eff2d0f1.PNG)

### 7. 메인 페이지
![슬라이드7](https://user-images.githubusercontent.com/40379446/216739465-7da1ddef-e848-4a04-89d7-97ac0b4eb057.PNG)


---
[진채연(hzlcodus)](https://github.com/hzlcodus)
<br>

### 8. 탐색 페이지
![슬라이드8](https://user-images.githubusercontent.com/40379446/216739468-703ba829-bf03-4e77-ac28-c7c27c1a6c84.PNG)

### 9. 팔로잉 목록 페이지
![슬라이드9](https://user-images.githubusercontent.com/40379446/216739472-e83e9ace-b2e7-4c45-b9c3-cfa717e6d624.PNG)

### 10. 팔로워 목록 페이지
![슬라이드10](https://user-images.githubusercontent.com/40379446/216739478-cbc13d31-a08e-4475-86fc-15e3608a698f.PNG)

### 11. 차단 목록 페이지
![슬라이드11](https://user-images.githubusercontent.com/40379446/216739480-1e74907c-dd9d-485e-b0c9-3c11732c8dc8.PNG)

### 12. 슬라이드 메뉴
![슬라이드12](https://user-images.githubusercontent.com/40379446/216739482-920d2009-7ab7-4824-97d1-5592e3bb99e6.PNG)

---
[오동건(ho2921ho)](https://github.com/ho2921ho)
<br>

### 13. 계정 페이지
![슬라이드13](https://user-images.githubusercontent.com/40379446/216739484-80865431-54e0-4b4d-a1f3-470c6795d3bd.PNG)

### 14. 프로필 페이지
![슬라이드14](https://user-images.githubusercontent.com/40379446/216739487-53ef3589-48c2-486d-9154-fbf7975fc1ef.PNG)

### 15. 비밀번호변경 페이지
![슬라이드15](https://user-images.githubusercontent.com/40379446/216739493-53051afb-de62-4cf3-8276-8e849b9e38b0.PNG)


---
[진채연(hzlcodus)](https://github.com/hzlcodus)
<br>

### 16. 할일 목록 및 달력 페이지
![슬라이드16](https://user-images.githubusercontent.com/40379446/216739495-cc8c1035-ffc5-40d6-b961-8445e1d1a357.PNG)

### 17. 할일 작성 페이지
![슬라이드17](https://user-images.githubusercontent.com/40379446/216739497-ca700913-022c-4e76-9bb9-099b5d262d3f.PNG)

### 18. 할일 수정 페이지
![슬라이드18](https://user-images.githubusercontent.com/40379446/216739500-dcffe18a-0231-42ca-ac7b-52a61e591b7c.PNG)

---
[송건우(Gitsgwoo)](https://github.com/Gitsgwoo)
<br>
### 19. 일기 달력 페이지
![슬라이드19](https://user-images.githubusercontent.com/40379446/216739509-c73265c9-854f-448f-8a36-cc0865749dc9.PNG)

### 20. 일기 목록 페이지
![슬라이드20](https://user-images.githubusercontent.com/40379446/216739513-a2adc12c-1db9-4d17-abac-4076c2d51490.PNG)

### 21. 일기 작성 페이지
![슬라이드21](https://user-images.githubusercontent.com/40379446/216739515-b17202b6-a6d6-4835-a8e8-111b0bceee62.PNG)

### 22. 일기 세부정보 페이지
![슬라이드22](https://user-images.githubusercontent.com/40379446/216739518-8234a4b2-3e7e-4cc7-802b-9af3599527e6.PNG)

### 23. 댓글 목록 페이지
![슬라이드23](https://user-images.githubusercontent.com/40379446/216739519-7cb43991-4efc-493f-b9f6-d7fe482f4d05.PNG)
---
### 헤더에 토큰 자동 추가 및 자동 refresh 기능
#### 토큰 자동 추가 
- retrofit 및 okhttp3의 interceptor 기능을 활용하여 사용자의 access token을 자동으로 Authorization 헤더에 넘겨주는 기능을 추가하였습니다.
- 최초 로그인 또는 재로그인 시에는 access token을 넘겨줄 필요가 없기 때문에 if 문을 통해서 Authorization 헤더에 빈 String을 넘겨주도록 처리했습니다.

#### 토큰 자동 refresh  
- access token의 유효기간이 만료되면 발생하는 401 에러를 catch하여, sharedPreference에 저장된 refresh token을 사용해 access token을 재발급 받도록 했습니다. 
- sharedPreference에 재발급된 토큰을 저장한 후 chain 의 Request 객체를 복사해 재발급한 토큰을 헤더에 넣고 다시 요청을 보내도록 했습니다. 
- refresh token으로 access token을 재발급하는 로직은 Request.Builder()를 사용해 refreshRequest를 생성하여 OkHttpClient().newCall(refreshRequest).execute()를 통해 직접 서버에 요청을 넘기도록 구성하였습니다.
