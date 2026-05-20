# Spring Security Study

Spring Security를 공부하면서 이 라이브러리를 단순히 설정 몇 줄로 사용하는 것만으로는 전체 흐름을 이해하기 어렵다고 느꼈습니다.

저처럼 Spring Security를 처음 배우며 필터 체인, 인증 객체, SecurityContext, UserDetailsService 같은 개념에서 어려움을 겪는 분들이 있을 것 같아, 학습한 내용을 흐름 중심으로 정리하기 위해 만든 저장소입니다.
사실 제가 편하려고 만들었습니다.

단순히 예제 코드를 따라 치는 것에서 끝나지 않고, Spring Security가 내부적으로 어떤 흐름으로 동작하는지 이해하고, 나아가 필요한 상황에서 직접 커스텀할 수 있는 힘을 기르는 것을 목표로 합니다.

내용은 공식 문서와 AI의 도움을 함께 참고하여 작성했습니다. 최대한 검증하며 정리하고 있지만, 틀린 부분이 있을 수 있습니다. 잘못된 내용이 있다면 이슈나 PR로 알려주시면 감사하겠습니다.

## 시작하기 전에

Spring Security를 이해하기 위해서는 먼저 Servlet Filter와 Security Filter Chain의 흐름을 알아야 한다.


## Servlet과 DispatcherServlet, 그리고 tomcat

우리가 브라우저에서 localhost:8080/login 과 같은 주소로 요청을 보내면, 그 요청이 곧바로 컨트롤러로 들어가는 것이 아니다. 시큐리티 라이브러리가 추가되어 있든 아니든 말이다.

큰 흐름으로 보자면 이렇다.

브라우저 요청
-> 톰캣
-> Servlet Filter Chain
   -> Filter
   -> Filter
   -> Filter
-> Servlet
   -> DispatcherServlet
-> Controller


사용자의 요청은 먼저 tomcat과 같은 servlet container라고 불리는 녀석에게 전달이 된다.
톰캣은 filter와 servlet을 호출할 수 있는데 먼저 서블릿에 대해 알아보자.
tomcat은 사용자의 요청을 받는데 여기서 이 요청을 처리할 '자바 객체'가 필요하다. 이 역할을 하도록 정해진 표준을 Servlet 이라고한다.

한마디로, 서버 안에서 요청을 처리하는 자바프로그램의 규칙/형태이다.

그런데 '자바 프로그램의 규칙' 이라고 두루뭉술 이야기하면 이해가 되지 않을 것이기에 한 번 더 이야기해보자면

-> "톰캣이 호출할 수 있도록 정해진 자바 클래스 작성 규칙이라고 이해하면 된다."

예를들어 내가 이런 클래스를 만들었다고 해보자.


public class MyPage {

    public void hello() {
        System.out.println("hello");
    }
}

톰캣은 이걸 보고 아무것도 할 수가 없다.

톰캣: 요청이 들어왔는데 뭘 호출해야 하지? hello()? run()? 파라미터는 뭘 써야하지?

그래서 약속이 필요하다

그 약속이
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) {
        // GET 요청 오면 톰캣이 여기 호출
    }
}

HTTP GET 요청이 오면 doGet()을 호출한다.
POST 요청이 오면 doPost()를 호출한다.
요청 정보는 HttpServletRequest에 담아서 줘.
응답은 HttpServletResponse에 써

이런 식의  "코드 호출 규칙"이다.

다시다시 한 번 "서블릿이 뭐냐" 를 쉽게 말하면
톰캣이 HTTP 요청을 자바 코드로 넘겨주기 위해 정해놓은 클래스 형태이다


그래서 결론은, 톰캣이 하는 역할은 크게 두 가지로 볼 수 있다.
1. 서블릿 구조를 호출할 수 있음
2. Filter를 호출할 수 있음

브라우저 요청
-> 톰캣
-> Servlet Filter Chain
   -> Filter
   -> Filter
   -> Filter
-> Servlet
   -> DispatcherServlet
-> Controller

저기 보이는 servlet filter chain은 이름에 서블릿이 붙었으니 저것도 서블릿 아니야?
-> 아님. 그냥 서블릿(디스패처)까지 도달하기 전에 실행되는 필터들이라 서블릿 필터체인이라고 부른다.



# spring security filter chain

위에서 요청 흐름을 알아보았는데 저 흐름에 우리가 사용하는 security chain을 등록하여 끼워 넣는 것이다.

톰캣
-> Servlet Filter Chain
   -> DelegatingFilterProxy
      -> Spring Security Filter Chain
         -> SecurityContextHolderFilter
         -> UsernamePasswordAuthenticationFilter
         -> AuthorizationFilter
         -> ...
         -> ...
-> DispatcherServlet
-> Controller

기존에 존재하는 서블릿 필터 사이에 시큐리티 필터를 끼워넣는, 이 흐름에 유의하며 따라가야 한다.
DelegatingFilterProxy는 톰캣 필터 세계와 spring bean 세계를 연결하는 프록시이다.



# register security filter chain

그렇다면 이제 실제 코드로 들어가서 시큐리티를 구현하는 방법을 알아보겠다.

시큐리티 구현 방법에는 크게 세 가지가 있다.

1. formLogin
2. jwt
3. Base64

이를 이해하기 위해선
또 하나의 그림이 필요하다
security chain의 흐름도인데

![alt text](images/security-flow.png)

큰 흐름은 위와 같다.
1,2,3 번 방식 모두 비슷한 흐름이지만 초반 과정에서 조금씩 차이가 있기 때문에
차이가 있는 곳은 별도로 설명하겠다.


* 자세한 코드 구현 방법은 공식문서나 이 리파지토리의 코드를 참고해주세요.
먼저 security 관련 종속성을 추가해주면 실행 로그 창에 임시 비밀번호가 asfd-f23f-f23f2... 이런 식으로
출력될 것이다.
이러면 시큐리티가 잘 걸린 것이다.

지금부터 우리가 해야 할 것은 이제 우리만의 "security filter chain" 을 서블릿 필터 체인 사이에 "끼워넣는" 것이다.

그럼 어떻게 끼워넣냐
사실 라이브러리를 등록한 것만으로 이미 끼워넣어진 것이다. spring security가 알아서 해줄 것이다.
이제 SecurityFilterChain을 커스텀하여 작성하기만 하면 된다.


1. formLogin 방식

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        return http
        
                .formLogin(fr -> fr // formLogin 방식 안에 람다 형태로 함수들을 이어준다.
                        .loginPage("/login") //로그인 창이 나오는 페이지
                        .loginProcessingUrl("/loginProc") // jsp에서 action이 도달하는 url
                        .defaultSuccessUrl("/main", true) // 로그인 성공 시 도달하는 페이지
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/loginProc").permitAll() 
                        .requestMatchers("/main").permitAll()

                )
                .csrf(csrf -> csrf.disable())
                .build();
    }
}


formlogin방식은 주로 jsp(ssr) 방식에서 쓰인다.
톰캣과 서블릿을 포함한 총 흐름은 이렇다.
tomcat -> servlet filter chain -> delegatingFilterProxy -> "Spring Security Filter Chain"
이 스프링 시큐리티 체인 안에서 다시

![alt text](images/security-flow.png)

이 흐름으로 들어오는 것이다.

"formLogin방식은 시큐리티가 제공하는 기본적인 로그인 흐름에 맞게 동작하며"

기본적으로 AuthenticationFilter->UsernamePasswordAuthenticationToken(filter) -> AuthenticaitonManager
-> AuthenticationProvider 까지 알아서 자동으로 해준다

따라서 우리는 authmanager나 provider를 신경쓰지 않아도 된다.
UserDetailsService와 UserDetails 구현체만 작성해주면 되는 것이다.

흐름을 설명하자면, 다음과 같다

formLogin에서는 로그인 처리 URL로 요청이 들어왔을 때
UsernamePasswordAuthenticationFilter가 username/password를 꺼내
UsernamePasswordAuthenticationToken을 만들고 AuthenticationManager에게 인증을 위임한다.

