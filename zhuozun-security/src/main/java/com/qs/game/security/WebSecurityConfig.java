//package com.qs.game.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
///**
// * Created by zun.wei on 2018/8/17.
// * To change this template use File|Default Setting
// * |Editor|File and Code Templates|Includes|File Header
// */
//@Configuration
//@EnableWebSecurity
//@Deprecated
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated()
//            .and()
//            .exceptionHandling()
//            .defaultAuthenticationEntryPointFor
//                    (swaggerAuthenticationEntryPoint(), new CustomRequestMatcher(AUTH_LIST))
//            .and()
//            .httpBasic()
//            .authenticationEntryPoint(restAuthenticationEntryPoint)
//            .and()
//            .csrf().disable();
//
//        //表示所有的访问都必须进行认证请求处理后才能正常进行
//        http.httpBasic().and().authorizeRequests().anyRequest().fullyAuthenticated();
//        //设置session为无状态,提升操作效率
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//
//    @Autowired
//    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        //inMemoryAuthentication 从内存中获取
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("zhangsan")
//                .password(this.getEncodePassword("123"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(this.getEncodePassword("admin"))
//                .roles("adminstrator");
//
//        //注入userDetailsService的实现类
//        //auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//
//    private String getEncodePassword(String orginPassword) {
//        return new BCryptPasswordEncoder().encode(orginPassword);
//    }
//
//
//
//
//
//
//    private static final List<String> AUTH_LIST = Arrays.asList(
//            "/swagger-resources/**",
//            "/swagger-ui.html**",
//            "/webjars/**",
//            "favicon.ico");
//
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//
//    @Bean
//    public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint() {
//        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
//        entryPoint.setRealmName("Swagger Realm");
//        return entryPoint;
//    }
//
//    private class CustomRequestMatcher implements RequestMatcher {
//
//        private List<AntPathRequestMatcher> matchers;
//
//        private CustomRequestMatcher(List<String> matchers) {
//            this.matchers = matchers.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
//        }
//
//        @Override
//        public boolean matches(HttpServletRequest request) {
//            return matchers.stream().anyMatch(a -> a.matches(request));
//        }
//
//    }
//
//}
