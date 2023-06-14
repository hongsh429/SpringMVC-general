package hello.springmvc.basic.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */

@Slf4j
@Controller
public class RequestBodyJsonController {

    ObjectMapper objectMapper = new ObjectMapper(); // 이건 Jackson 라이브러리를 사용하지 않을때,Servlet에서 사용하는 방식

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}. age={}", helloData.getUsername(), helloData.getAge());
        response.getWriter().write("ok");

        /*reponse.getWriter().write() 로 json을 그대로 보내주기 위해서 사용*/
//        String s = objectMapper.writeValueAsString(helloData);
//        response.getWriter().write(s);
    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username={}. age={}", helloData.getUsername(), helloData.getAge());
        return "ok";

    }


    @ResponseBody
    @PostMapping("/request-body-json-v3")
                                    /*@ModelAtrribute는 바인딩의 개념*/
                                    /*@RequestBody는 converter의 개념*/
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {
        log.info("messageBody={}", helloData);
        log.info("username={}. age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
        /*
        @RequestParam은 null값이 넘어오면 에러발생
        @ModelAttribute는 객체 자체가 기본으로 초기화를 해주기 때문에 에러는 나지 않는다
        */
    }


    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData data = httpEntity.getBody();
        log.info("messageBody={}", data);
        log.info("username={}. age={}", data.getUsername(), data.getAge());
        return "ok";
    }


    @PostMapping("/request-body-json-v4-1")
    public HttpEntity<HelloData> requestBodyJsonV41(HttpEntity<HelloData> httpEntity) throws IOException {
        HelloData data = httpEntity.getBody();
        log.info("messageBody={}", data);
        log.info("username={}. age={}", data.getUsername(), data.getAge());
        return new HttpEntity<>(data);
        /* 이렇게 처리하는 것도 기억하자! */
    }


    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) throws IOException {
                                        /*json -> HelloData 객체*/
        log.info("messageBody={}", helloData);
        log.info("username={}. age={}", helloData.getUsername(), helloData.getAge());
        return helloData;  /*HelloData 객체 -> json*/
    }

}
