package remember_ext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @auther 丁溪贵
 * @date 2019/9/20
 *
 *          不起作用，而且感觉没什么用，懒的搞这个了，直接跳过了。
 *
 *          <bean class="remember_ext.IPTokenBasedRememberMeServices" >
                <constructor-arg index="0" value="dxg_remember"></constructor-arg>
                <constructor-arg index="1" ref="userService" />
            </bean>
 */
public class IPTokenBasedRememberMeServices extends TokenBasedRememberMeServices {

    // 必须要调用父类的这个构造器（因为父类没有无参的构造器）
    public IPTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService){
        super(key,userDetailsService);
    }

    private static final ThreadLocal<HttpServletRequest> requestHolder =
            new ThreadLocal<HttpServletRequest>();

    public HttpServletRequest getContext() {
        return requestHolder.get();
    }
    public void setContext(HttpServletRequest context) {
        requestHolder.set(context);
    }

    protected String getUserIPAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    // 在RememberMe校验成功后
    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        try
        {
            setContext(request); // 只是为了获取到request对象，所以重新此方法

            super.onLoginSuccess(request, response, successfulAuthentication);
        } finally {
            setContext(null);
        }
    }

    /**
             父类的 onLoginSuccess 方法将会触发 makeTokenSignature 方法来创建认证凭证的 MD5 哈希
             值。我们将要重写此方法，以实现从 request 中获取 IP 地址并使用 Spring 框架的一个工具类
             编码要返回的 cookie 值。（这个方法在进行 remember me 校验时还会被调用到，以判断前台
             传递过来的 cookie 值与后台根据用户名、密码、IP 地址等信息生成的 MD5 值是否一致。—
     * @param tokenExpiryTime
     * @param username
     * @param password
     * @return
     */
    @Override
    protected String makeTokenSignature(long tokenExpiryTime, String username, String password) {
        // 用户名+令牌过去时间+密码+key+ip地址
        //DigestUtils这个工具类没有过啊
        return  DigestUtils.md5DigestAsHex((username + ":" +
                        tokenExpiryTime + ":" + password + ":" + getKey() + ":" + getUserIPAddress
                (getContext())).getBytes());
    }

    @Override
    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
    // append the IP adddress to the cookie
        String[] tokensWithIPAddress =
                Arrays.copyOf(tokens, tokens.length+1);
        tokensWithIPAddress[tokensWithIPAddress.length-1] =
                getUserIPAddress(request);
        super.setCookie(tokensWithIPAddress, maxAge,
                request, response);
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {


        try{
            setContext(request);
            // take off the last token
            String ipAddressToken = cookieTokens[cookieTokens.length-1];
            if(!getUserIPAddress(request).equals(ipAddressToken)) {
                throw new InvalidCookieException("Cookie IP Address did not contain a matching IP (contained '" + ipAddressToken + "')");
            }
            return super.processAutoLoginCookie(Arrays.copyOf(cookieTokens,
                    cookieTokens.length-1), request, response);
        }finally{
            setContext(null);
        }
    }

}
