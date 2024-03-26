
# Spring & React_Diet_HomePage(java_final_project)
:page_with_curl: spring과 react로 건강관리 웹페이지 만들기</br>
## SPRING 이란?
- 자바(Java)플랫폼을 위한 오픈소스 애플리케이션 프레임워크
- <b>자바객체 담고 있는 컨테이너</b>로 자바 객체 생성, 소멸같은 라이프사이클을 관리</br>
       📌 컨테이너란?(본 프로젝트와는 관계없지만 요즘 개발자 커뮤니티던 채용공고에서 자주 보이는 키워드라서 궁금해서 알아봤고 기록) ->https://github.com/DoYongJu/java_final_project/issues/1#issue-1977089700
- <b>POJO</b>(Plain Old Java Object)방식 프레임 워크</br>
  📍 Pojo이란?
간단한 자바 오브젝트 <-> 프레임워크에 종속된 무거운 객체(특정 기술에 종속되지 않은 순수한 자바 객체)
- DI(Dependency Injection)의 의존성 주입</br>
  📍 DI란?
  객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 방식이다. DI(의존성 주입)를 통해서 모듈 간의 결합도가 낮아지고 유연성이 높아진다.</br>
- AOP(Aspect Oriented Progrmming)의 관점 지향 프로그래밍</br>
  📍 AOP란?
  '관점지향프로그래밍':예를 들어 A라는 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화 하는것. 
  
- LOC(Inversion of Controller)의 제어의 역전 기반</br>
  :page_with_curl: LOC란?"제어의 역전" 이라는 의미로, 말 그대로 메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것을 의미한다.
IoC는 제어의 역전이라고 말하며, 간단히 말해 "제어의 흐름을 바꾼다"라고 한다.</br>
객체의 의존성을 역전시켜 객체 간의 결합도를 줄이고 유연한 코드를 작성할 수 있게 하여 가독성 및 코드 중복, 유지 보수를 편하게 할 수 있게 한다.</br>
  
- MVC(Model View Controller)패턴 지원</br>
  :page_with_curl: MVC란? Controller:Http Request를 처리하는 부분, Model:데이터를 처리해 정제된 데이터를 넣는 부분, View: 사용자에게 보여지는 부분
  
- Spring의 장점: 다른 프레임워크나 데이터베이스 관리 서비스와 연계가 자연스럽고 동적인 웹 제작에 최적화. 개발에 필요한 구조가 이미 만들어져있기에 비즈니스 로직에 집중 가능.
   
  

### 0.다운로드 및 개발환경
- [JDK Download](https://www.oracle.com/java/technologies/downloads/#java8, "JDK link") [17.0.7ver.]
- [Apache Tomcat Download](https://tomcat.apache.org/download-80.cgi, "Apache Tomcat link")  [9.0.82ver.]
- [Eclipse Download](https://www.eclipse.org/downloads/, "Eclipse link")
- [H2 Download](https://www.h2database.com/html/download.html, "H2 link")
- [H2 Visualizer Download](https://www.dbvis.com/, "H2 Visualizer link")

### 1.프로젝트 생성 및 서버 연결
- [SpringBoot project 생성](https://start.spring.io, "springStart link") [gradle, 2.7.16ver. , jar]


## Project 설명
### 0.프로젝트 개발 환경
- 사용 Tool: Eclipse. H2 visualizer, Visual Studio
- 언어: Java, JS, HTML, CSS, SQL
- 기술: React, JSON
- FrameWork: SpringBoot
- Server: apacheTomcat9.0v, AWS
### 1.홈페이지 구조도
![image](https://github.com/DoYongJu/java_final_project/assets/43160573/f51d3ce4-0029-4209-aaa7-87d7cea700fb)
### 2. 데이터베이스 구축
- ![image](https://github.com/DoYongJu/java_final_project/assets/43160573/eb772a97-a73a-48d6-a205-a4ef4bb1bb39)
### 3.기능 ( 홈페이지 주소: http://43.201.19.112/ -2024.01까지 유효)
-회원가입,로그인,건강정보서비스제공(칼로리 사전, 스케줄러, 유형검사), 트레이너 매칭 플랫폼, 마이페이지, 커뮤니티, 쇼핑몰, 관리자페이지
### 4.타임라인
(개인정보 보호로 인해 필자 이외의 팀원은 가명으로 대체함)
![image](https://github.com/DoYongJu/java_final_project/assets/43160573/70dea376-8290-4759-ab06-55f62ee6d61c)
### 5.트러블 슈팅
![image](https://github.com/DoYongJu/java_final_project/assets/43160573/1e2d4a25-ba59-4d85-882e-6f5f97b0c30d)
![image](https://github.com/DoYongJu/java_final_project/assets/43160573/b8a16f51-a6ba-4786-b286-9e94033c930e)




