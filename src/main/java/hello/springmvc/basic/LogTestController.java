package hello.springmvc.basic;



import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class LogTestController {

    /*  @Slf4j annotation은 아래의 코드를 대신해준다. 이는 lombok에서 제공하는 기능이다   */
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {

        String name = "Spring";
        System.out.println("name = " + name); // 얘는 그냥 어디에나 남겨져있다...
                                                // 고객의 요청이 엄청 많다면 이를 다 찍어 줄 수는 없다.


        /* 로그는 절대로 문자열 더하기 형태로 로그를 남기는 것도 가능하나, 사용하지 말라!✨✨✨

        log.trace("trace log="+ name);
        이렇게 쓰면 해당 log가 사용이 되던 안되던,
        내부적으로 문자열 + 문자열 이 일어난다.
        출력이 안될 것인데도,✨✨✨ "trace log=Spring"을 가지고 있게 되는 작업이 수행된다.

        반면에
        log.trace("trace log = {}", name);
        이렇게 쓰면 파라미터 형태로 name과 문자열을 넘겼기 때문에, trace되지 않는다면, 애초에 문자열 + 문자열 연산을 하지 않는다.
        */


        log.trace("trace log = {}", name);
        log.debug("debug log = {}", name);

        // 운영시스템에서 볼 정보야. 필요한 정보야
        log.info("info log = {}", name);

        // 경고 메세지여서 따로 봐야되
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);

        return "ok";
        /*
        @Controller는 반환 값이 String이라면? >> 뷰를 찾는다.
        @RestController : 이것을 사용하면, 해당 메소드의 리턴 타입이 String 이라면,
                            진짜 String값을 "http 메세지 바디"에 가지고 간다!



        log 정보를 보면,
        시간                                              쓰레드                     bean                            그리고 문자
        2023-06-12T20:26:35.527+09:00  INFO 41036 --- [nio-8080-exec-2] c.springmvc.basic.LogTestController      : info log =Spring
        * */
    }
}
