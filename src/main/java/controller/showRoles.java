package controller;

import org.apache.taglibs.standard.tag.common.core.ForEachSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @auther 丁溪贵
 * @date 2019/9/21
 *      显示一个用户的所有角色
 */
@Controller
public class showRoles {

    public Authentication getAuth(){
        //获得当前 安全上下文对象
        SecurityContext context = SecurityContextHolder.getContext();
        // 获得当亲用户的认证信息
        Authentication authentication = context.getAuthentication();
        // 认证信息里面包含了什么信息
        //用户名
        System.out.println("getName--> "+authentication.getName());
       // WebAuthenticationDetails 包括sessionid，ip地址之类的
        System.out.println("getDetails--> "+authentication.getDetails());
        // userdetails
        System.out.println("getPrincipal--> "+authentication.getPrincipal());
        // null ,在shiro中是密码
        System.out.println("getCredentials--> "+authentication.getCredentials());

        return authentication ;
    }

    @RequestMapping("/showRoles")
    public String showRoles(Model model){
        // 获取
        Collection<? extends GrantedAuthority> authorities = getAuth().getAuthorities();
        Iterator<? extends GrantedAuthority> it = authorities.iterator();
        List roles = new ArrayList();
        while (it.hasNext()){
            GrantedAuthority next = it.next();
            //SimpleGrantedAuthority实现类，返回的就是角色字符串
            roles.add(next.getAuthority());
        }

        // 设置用户名
        model.addAttribute("username",getAuth().getName());
        // 设置拥有的角色
        model.addAttribute("roles",roles);

        return "/page/auth/showRoles" ;
    }

}
