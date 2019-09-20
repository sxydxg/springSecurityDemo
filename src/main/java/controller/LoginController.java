package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther 丁溪贵
 * @date 2019/9/20
 *
 *      你使用/login 且为get请求，被spring Security拦截到了，发现和默认的
 *      表单过滤器的 url=/login 重合，会去spring-Security的配置文件
 *      中寻找<sercurity:form-login login-page="/login" />的login-page
 *      的值是多少，如果没有配置，则自动生成一个默认的登录表单，并短路了spring mvc
 *      的mapping，如果配置了login-page则放行，交由controller来处理视图。
 */
@Controller
public class LoginController {

    // 如果没有返回值，窥探response的流中是否有数据
    // 如果有则不走视图解析器，如果没有则或去value的值进行匹配
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public void login(){}

    @RequestMapping(value="/logoutpage",method = RequestMethod.GET)
    public void logout(){}
}
