package hello.springmvc.basic.requestmapping;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"/hello-basic", "hello-go"})
    public String helloBasic() {
        log.info("hellobasic");
        return "Ok";
    }


    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "Ok";
    }


    /*
      편리한 축약 애노테이션 (코드보기)
      @GetMapping
      @PostMapping
      @PutMapping
      @DeleteMapping
      @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }


    /*
     PathVariable 사용✨✨✨
     변수명이 같으면 생략 가능
     @PathVariable("userId") String userId -> @PathVariable userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(
//            @PathVariable ("userId") String data  // PathVariable의 이름{ name } 과 매개변수의 이름이 같다면 생략가능
            @PathVariable String userId
    ) {
//        log.info("mappingPath userId={}", data);
        log.info("mappingPath userId={}", userId);
        return "OK";
    }

    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath2(
            @PathVariable String userId,
            @PathVariable("orderId") Long orderNum
    ) {
        log.info("mapping Path userId={}, orderId={}", userId, orderNum);
        return "Ok";
    }


    /*
     * 파라미터로 추가 매핑
     * 일종의 조건을 추가
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }



    /*
     * 특정 헤더로 추가 매핑
     * 헤더에 이런 조건이 있을 때만 가능하다는 것!
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")  // 이 또한 잘 사용되지 않는다~
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }


    /*
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json")
                                                // consume = Content-type
                                                //
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }



    /*
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = "application/json")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }


}
