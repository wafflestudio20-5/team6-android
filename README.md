
# ๐ฝ Wafflemate

TodoMate ์ดํ์ ์ฐธ๊ณ ํ์ฌ Wafflemate ์ดํ์ ์ ์ํ์ต๋๋ค.
<br/>
<br/>
๊ธฐ์กด ์ฑ์ ์๋ **ํ๋ก์ฐ์ฐจ๋จ ๊ธฐ๋ฅ**, **์๊ฐ์ฒดํฌ ๊ธฐ๋ฅ**์ ์ถ๊ฐํ์ฌ SNS ๊ธฐ๋ฅ์ ๊ฐํํ๊ณ  ์ผ์ ๊ด๋ฆฌ ์ฌ์ฉ์ฑ์ ๋ํ์ต๋๋ค.

---

<center><img src = "https://user-images.githubusercontent.com/40379446/216377971-6cd45f39-484d-4ad9-9451-973dae12fdea.png"></center>


## ๐ญ"์ํ๋ฉ์ดํธ" ๋ ์ด๋ค ์ฑ์ธ๊ฐ์?

์น๊ตฌ๋ค๊ณผ ํจ๊ปํ๋ ์ผ์ ๊ด๋ฆฌ, ์ํ๋ฉ์ดํธ!
<br/>
<br/>
์ผ์ ๊ด๋ฆฌ์ ๋๋ถ์ด ํ๋ก์ฐ ํ๋ก์์ด ๊ฐ๋ฅํ SNS ๊ธฐ๋ฅ์ ์ ๊ณตํฉ๋๋ค.
<br/>
์ผ์ /์ผ๊ธฐ ๊ณต์ ๋ฅผ ํตํด ํจ๊ณผ์ ์ธ ์ผ์ ๊ด๋ฆฌ๋ฅผ ๊ฒฝํํ์ธ์.
<br/>
<br/>

## ๐ ์ํฌํ๋ก์ฐ
![image](https://user-images.githubusercontent.com/40379446/216748806-38cec0ff-630f-4585-86b7-67db31572757.png)

## ๐ ๊ธฐ์ ์คํ
![image](https://user-images.githubusercontent.com/40379446/216749114-6e54a6b7-031a-4be4-8c53-ff81a3cd61d7.png)


## ๐จ ๋ง๋  ์ฌ๋
| [์ก๊ฑด์ฐ(Gitsgwoo)](https://github.com/Gitsgwoo) | [์ง์ฑ์ฐ(hzlcodus)](https://github.com/hzlcodus) | [์ค๋๊ฑด(ho2921ho)](https://github.com/ho2921ho) |



## ๐ ํ์ด์ง ์ค๋ช

---
[์ก๊ฑด์ฐ(Gitsgwoo)](https://github.com/Gitsgwoo)
<br>

### 1. ์์ ํ์ด์ง
![์ฌ๋ผ์ด๋1](https://user-images.githubusercontent.com/40379446/216739447-c8828df3-4d10-4fd5-a272-13151c8a1e78.PNG)

### 2. ํ์๊ฐ์ ํ์ด์ง
![์ฌ๋ผ์ด๋2](https://user-images.githubusercontent.com/40379446/216739452-2370377a-da14-46fc-a119-4ee94cd75fd8.PNG)

### 3. ์ด๋ฉ์ผ ์ธ์ฆ ํ์ด์ง
![์ฌ๋ผ์ด๋3](https://user-images.githubusercontent.com/40379446/216739456-be94b0db-831c-49ab-9190-a78cb4c50994.PNG)

### 4. ๋ก๊ทธ์ธ ํ์ด์ง
![์ฌ๋ผ์ด๋4](https://user-images.githubusercontent.com/40379446/216739458-9fcc461e-e6c8-4b29-a5ef-ac1c3526df0e.PNG)
<br/>

#### ์๋ ๋ก๊ทธ์ธ 
- ์ฌ์ฉ์ ๋ก๊ทธ์ธ ์์ฒญ ์ฒ๋ฆฌ ์์ ์ฑ์ shared preference์ access token, refresh token, ์ฌ์ฉ์ ์ ๋ณด๋ฅผ ์ ์ฅํฉ๋๋ค. <br/>
- ์ธ์ฆ ์ ๋ณด๋ฅผ ๋ํ๋ด๋ data class AuthInfo ๋ฅผ ๋ง๋ค๊ณ  ์ธ์ฆ ์ ๋ณด๋ฅผ  AuthStorage class์ ๋ณ์ authInfo์ StateFlow<AuthInfo>์ ํํ๋ก ์ ์ฅํฉ๋๋ค. <br/>
- ์ฑ์ startdestination์ ๋ฉ์ธํ์ด์ง๋ก ์ค์ ํด๋๊ณ , ๋ฉ์ธํ์ด์ง์ lifecycle ์ค OnViewCreated ์์ ์ฌ์ฉ์์ authInfo ๊ฐ์ด null์ธ์ง ํ์ธํฉ๋๋ค. <br/>
- ๊ฐ์ด null์ด๋ผ๋ฉด ๋ก๊ทธ์ธ ์์ฒญ์ ์ฒ๋ฆฌํ๋ ์ค์ฒฉ๊ทธ๋ํ login_graph๋ก navigate ๋ฉ๋๋ค. <br/>
 <br/>
login_graph : ์์ ํ์ด์ง, ํ์๊ฐ์ ํ์ด์ง, ์ด๋ฉ์ผ ์ธ์ฆ ํ์ด์ง, ๋ก๊ทธ์ธ ํ์ด์ง๋ก ๊ตฌ์ฑ

### 5. ๊ตฌ๊ธ ๋ก๊ทธ์ธ ํ์ด์ง
![์ฌ๋ผ์ด๋5](https://user-images.githubusercontent.com/40379446/216739460-1e77eb2c-91a7-45f7-8728-790b2cc1d4bd.PNG)

### 6. ์นด์นด์ค ๋ก๊ทธ์ธ ํ์ด์ง
![์ฌ๋ผ์ด๋6](https://user-images.githubusercontent.com/40379446/216739462-d7f6ebf2-d816-4ff4-9bd8-09c7eff2d0f1.PNG)

### 7. ๋ฉ์ธ ํ์ด์ง
![์ฌ๋ผ์ด๋7](https://user-images.githubusercontent.com/40379446/216739465-7da1ddef-e848-4a04-89d7-97ac0b4eb057.PNG)


---
[์ง์ฑ์ฐ(hzlcodus)](https://github.com/hzlcodus)
<br>

### 8. ํ์ ํ์ด์ง
![์ฌ๋ผ์ด๋8](https://user-images.githubusercontent.com/40379446/216739468-703ba829-bf03-4e77-ac28-c7c27c1a6c84.PNG)

### 9. ํ๋ก์ ๋ชฉ๋ก ํ์ด์ง
![์ฌ๋ผ์ด๋9](https://user-images.githubusercontent.com/40379446/216739472-e83e9ace-b2e7-4c45-b9c3-cfa717e6d624.PNG)

### 10. ํ๋ก์ ๋ชฉ๋ก ํ์ด์ง
![์ฌ๋ผ์ด๋10](https://user-images.githubusercontent.com/40379446/216739478-cbc13d31-a08e-4475-86fc-15e3608a698f.PNG)

### 11. ์ฐจ๋จ ๋ชฉ๋ก ํ์ด์ง
![์ฌ๋ผ์ด๋11](https://user-images.githubusercontent.com/40379446/216739480-1e74907c-dd9d-485e-b0c9-3c11732c8dc8.PNG)

### 12. ์ฌ๋ผ์ด๋ ๋ฉ๋ด
![์ฌ๋ผ์ด๋12](https://user-images.githubusercontent.com/40379446/216739482-920d2009-7ab7-4824-97d1-5592e3bb99e6.PNG)

---
[์ค๋๊ฑด(ho2921ho)](https://github.com/ho2921ho)
<br>

### 13. ๊ณ์  ํ์ด์ง
![์ฌ๋ผ์ด๋13](https://user-images.githubusercontent.com/40379446/216739484-80865431-54e0-4b4d-a1f3-470c6795d3bd.PNG)

### 14. ํ๋กํ ํ์ด์ง
![์ฌ๋ผ์ด๋14](https://user-images.githubusercontent.com/40379446/216739487-53ef3589-48c2-486d-9154-fbf7975fc1ef.PNG)

### 15. ๋น๋ฐ๋ฒํธ๋ณ๊ฒฝ ํ์ด์ง
![์ฌ๋ผ์ด๋15](https://user-images.githubusercontent.com/40379446/216739493-53051afb-de62-4cf3-8276-8e849b9e38b0.PNG)


---
[์ง์ฑ์ฐ(hzlcodus)](https://github.com/hzlcodus)
<br>

### 16. ํ ์ผ ๋ชฉ๋ก ๋ฐ ๋ฌ๋ ฅ ํ์ด์ง
![์ฌ๋ผ์ด๋16](https://user-images.githubusercontent.com/40379446/216739495-cc8c1035-ffc5-40d6-b961-8445e1d1a357.PNG)

### 17. ํ ์ผ ์์ฑ ํ์ด์ง
![์ฌ๋ผ์ด๋17](https://user-images.githubusercontent.com/40379446/216739497-ca700913-022c-4e76-9bb9-099b5d262d3f.PNG)

### 18. ํ ์ผ ์์  ํ์ด์ง
![์ฌ๋ผ์ด๋18](https://user-images.githubusercontent.com/40379446/216739500-dcffe18a-0231-42ca-ac7b-52a61e591b7c.PNG)

---
[์ก๊ฑด์ฐ(Gitsgwoo)](https://github.com/Gitsgwoo)
<br>
### 19. ์ผ๊ธฐ ๋ฌ๋ ฅ ํ์ด์ง
![์ฌ๋ผ์ด๋19](https://user-images.githubusercontent.com/40379446/216739509-c73265c9-854f-448f-8a36-cc0865749dc9.PNG)

### 20. ์ผ๊ธฐ ๋ชฉ๋ก ํ์ด์ง
![์ฌ๋ผ์ด๋20](https://user-images.githubusercontent.com/40379446/216739513-a2adc12c-1db9-4d17-abac-4076c2d51490.PNG)

### 21. ์ผ๊ธฐ ์์ฑ ํ์ด์ง
![์ฌ๋ผ์ด๋21](https://user-images.githubusercontent.com/40379446/216739515-b17202b6-a6d6-4835-a8e8-111b0bceee62.PNG)

### 22. ์ผ๊ธฐ ์ธ๋ถ์ ๋ณด ํ์ด์ง
![์ฌ๋ผ์ด๋22](https://user-images.githubusercontent.com/40379446/216739518-8234a4b2-3e7e-4cc7-802b-9af3599527e6.PNG)

### 23. ๋๊ธ ๋ชฉ๋ก ํ์ด์ง
![์ฌ๋ผ์ด๋23](https://user-images.githubusercontent.com/40379446/216739519-7cb43991-4efc-493f-b9f6-d7fe482f4d05.PNG)
---
### ํค๋์ ํ ํฐ ์๋ ์ถ๊ฐ ๋ฐ ์๋ refresh ๊ธฐ๋ฅ
#### ํ ํฐ ์๋ ์ถ๊ฐ 
- retrofit ๋ฐ okhttp3์ interceptor ๊ธฐ๋ฅ์ ํ์ฉํ์ฌ ์ฌ์ฉ์์ access token์ ์๋์ผ๋ก Authorization ํค๋์ ๋๊ฒจ์ฃผ๋ ๊ธฐ๋ฅ์ ์ถ๊ฐํ์์ต๋๋ค.
- ์ต์ด ๋ก๊ทธ์ธ ๋๋ ์ฌ๋ก๊ทธ์ธ ์์๋ access token์ ๋๊ฒจ์ค ํ์๊ฐ ์๊ธฐ ๋๋ฌธ์ if ๋ฌธ์ ํตํด์ Authorization ํค๋์ ๋น String์ ๋๊ฒจ์ฃผ๋๋ก ์ฒ๋ฆฌํ์ต๋๋ค.

#### ํ ํฐ ์๋ refresh  
- access token์ ์ ํจ๊ธฐ๊ฐ์ด ๋ง๋ฃ๋๋ฉด ๋ฐ์ํ๋ 401 ์๋ฌ๋ฅผ catchํ์ฌ, sharedPreference์ ์ ์ฅ๋ refresh token์ ์ฌ์ฉํด access token์ ์ฌ๋ฐ๊ธ ๋ฐ๋๋ก ํ์ต๋๋ค. 
- sharedPreference์ ์ฌ๋ฐ๊ธ๋ ํ ํฐ์ ์ ์ฅํ ํ chain ์ Request ๊ฐ์ฒด๋ฅผ ๋ณต์ฌํด ์ฌ๋ฐ๊ธํ ํ ํฐ์ ํค๋์ ๋ฃ๊ณ  ๋ค์ ์์ฒญ์ ๋ณด๋ด๋๋ก ํ์ต๋๋ค. 
- refresh token์ผ๋ก access token์ ์ฌ๋ฐ๊ธํ๋ ๋ก์ง์ Request.Builder()๋ฅผ ์ฌ์ฉํด refreshRequest๋ฅผ ์์ฑํ์ฌ OkHttpClient().newCall(refreshRequest).execute()๋ฅผ ํตํด ์ง์  ์๋ฒ์ ์์ฒญ์ ๋๊ธฐ๋๋ก ๊ตฌ์ฑํ์์ต๋๋ค.
