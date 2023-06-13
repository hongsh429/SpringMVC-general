package hello.springmvc.basic.request;


import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 그냥 꺼내는 방식
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);
        response.getWriter().write("Ok");   // html/text
//        request.getRemoteAddr()
    }


    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     */

    @ResponseBody // @RestController와 같은 효과
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge  // 알아서 parsing 됨(binding이라고함)

    ) {
        log.info("username={}, age={}", memberName, memberAge);
        return "Ok"; // 이렇게 되면, @Controller라면 view를 찾는다.
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age
    ) {
        log.info("username={}, age={}", username, age);
        return "Ok";
    }


    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "Ok";
    }

    /*
    required 옵션
        true: 해당 파라미터가 없다면, 오류를 던짐. spec 상 해당 파라미터가 true일 때!
    */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer /*int*/ age
            // 만약 값이 없었을 때 null이 들어가야하는데 primitive type은 null을 받을 수 없다
    ) {
        log.info("username={}, age={}", username, age);
        return "Ok";

        /*
        주의! - 파라미터 이름만 사용
        /request-param?username=   >> "" != null 이어서 통과한다!
        파라미터 이름만 있고 값이 없는 경우 빈문자로 통과

        null 을 int 에 입력하는 것은 불가능(500 예외 발생)
        따라서 null 을 받을 수 있는 Integer 로 변경하거나, 또는 다음에 나오는 defaultValue 사용
        */
    }


    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(defaultValue = "-1") int age
    ) {
        log.info("username={}, age={}", username, age);
        return "Ok";

        /*
        defaultValue 는 빈 문자의 경우에도 설정한 기본 값이 적용된다.
        /request-param-default?username=
        */
    }


//    @ResponseBody
//    @RequestMapping("/request-param-map")
//    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
//
//        String username = (String) paramMap.get("username");
//
//        log.info("username={}, age={}", username, paramMap.get("age"));
//        return "Ok";
//    }


    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam MultiValueMap<String, Object> paramMap) {

        List<Object> username = paramMap.get("username");

        log.info("username={}, age={}", username, paramMap.get("age"));
        return "Ok";

        // http://localhost:8080/request-param-map?username=hong&age=30&username=kim&age=34
        // username=[hong, kim], age=[30, 34]
    }


//    @ResponseBody
//    @RequestMapping("/model-attribute-v1")
//    public String modelAttrebuteV1(@RequestParam String username, @RequestParam int age) {
//        HelloData helloData = new HelloData();
//        helloData.setAge(age);
//        helloData.setUsername(username);
//        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
//        log.info("helloData={}", helloData); // @Data 에서 toString()해주기 때문에
//        return "ok";
//    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData); // @Data 에서 toString()해주기 때문에
        return "ok";

        /*
        스프링MVC는 @ModelAttribute 가 있으면 다음을 실행한다.
        HelloData 객체를 생성한다.
        요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다.
        그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩) 한다.
        예) 파라미터 이름이 username 이면 setUsername() 메서드를 찾아서 호출하면서
            값을 입력한다
        */
    }


    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData); // @Data 에서 toString()해주기 때문에
        return "ok";
    }
}
