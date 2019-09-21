package controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther 丁溪贵
 * @date 2019/9/19
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/","/index"})
    public String index(){
        // 展示首页
        return "index" ;
    }

    // 商品的增删查改,为了方便使用resultful风格跳转页面
    @RequestMapping("/page/{productPage}")
    public String showPage( @PathVariable String productPage){
        return "/page/"+productPage ;
    }

    @RequestMapping("/mydenied")
    public void mydenied(){}
}
