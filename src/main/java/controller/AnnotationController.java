package controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther 丁溪贵
 * @date 2019/9/21
 *
 *   1. 需要在配置文件中开启spring security注解配置
 *   2.  @PreAuthorize/@PostAuthorize()
 *
 */
@Controller
public class AnnotationController {


    //  User前面都需要加上 ROLE_的前缀，你可以点击这个
    // 注解的hasRole('user')查看源码
    // 数据库中角色是user    在里将hasRole('user')--> ROLE_user
    // 数据库中 user!=ROLE_user -------> 认证不通过
    @PreAuthorize("hasRole('admin')")
    @RequestMapping("/testAnnotation")
    public String testAnnotation(){
        return "/page/auth/testAnnotation" ;
    }
}
