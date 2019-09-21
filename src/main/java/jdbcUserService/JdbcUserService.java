package jdbcUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther 丁溪贵
 * @date 2019/9/20
 */
public class JdbcUserService implements UserDetailsService {

    private ConcurrentHashMap<String,String> database = new ConcurrentHashMap();


    // 加一点数据（相当于数据库了）
    public void init(){
        database.put("dxg","12345");
        database.put("dxg2","54321");
    }
    // 模拟从数据库中读取数据，
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String password = database.get(username);
//        UserDetails 有一个子类，User

//        获得加密后的密码（我们使用username属性最为盐）
        password = getEncodingPassword(password, username);
//      ReflectionSaltSource会根据指定的属性名从UserDetails对象中获取到salt
//        注意是从我们返回的这个UserDetails对象中通过反射取得盐
        User user = new User(username,password,getAuthorities());
        return user;
    }

    // 密码使用sha哈希函数进行加密
    public String getEncodingPassword(String password,Object salt){
        ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder();
        return shaPasswordEncoder.encodePassword(password, salt);
    }


    //所有的用户都返回user和admin对象角色
    public List<SimpleGrantedAuthority> getAuthorities(){
        //GrantedAuthority有很多子类，使用SimpleGrantedAuthority
        SimpleGrantedAuthority r1 = new SimpleGrantedAuthority("ROLE_admin");
        SimpleGrantedAuthority r2 = new SimpleGrantedAuthority("user");
        ArrayList<SimpleGrantedAuthority> list = new ArrayList();
        list.add(r1);
        list.add(r2);
        return list ;
    }
}
