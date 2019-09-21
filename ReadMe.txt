1.sercurity:http auto-config="true"
上面的auto-config=true需要加，意思自动配置spring Security过滤器链（配置10个，但是总共大约有25个）


2.RememberMe的令牌包含用户信息和权限
  所以hasRole('ROLE_USER') and fullyAuthenticated是有必要的

  public RememberMeAuthenticationToken(String key, Object principal, Collection<? extends GrantedAuthority> authorities) {
        // key 是浏览器带过来的RememberMe key的值以确定你与哪一个记住我令牌绑定
        super(authorities);
        if (key != null && !"".equals(key) && principal != null && !"".equals(principal)) {
            this.keyHash = key.hashCode();
            this.principal = principal;
            this.setAuthenticated(true);
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
   }

3.权限控制的方式
  a.页面级控制  （url控制页面访问）
  b.功能级控制  （一个页面，不同的的角色展示不同的标签或按钮，在页面内需要控制其是否生成）

4. 父子容器问题：
    使用ContextListener初始化了一个父容器来加载spring-security.xml文件
    使用springmvc初始化了一个子容器
  问题：在controller中使用权限注解，必须要有一下配置
       <sercurity:global-method-security pre-post-annotations="enabled" />
        但是cotroller的扫描（也就是代理）是在springmvc容器中完成的。虽然在
        ContextListener父容器开启了pre-post-annotations="enabled"，但是没有产生
        代理对象。
  解决：将web.xml的ContextListener父容器移除，在springmvc.xml使用
  <import resource="spring-security.xml" />，将2个容器合为一体。这样注解才会产生
  代理对象


5.idea如何debug
    https://blog.csdn.net/MonkeyBrothers/article/details/79606127
