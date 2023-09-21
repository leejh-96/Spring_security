package com.example.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.sun.tools.doclint.Entity.or;
import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity
//@EnableWebSecurity는 스프링MVC와 스프링 시큐리티를 결합하는 클래스임
//@EnableWebSecurity 애노테이션을 WebSecurityconfigurerAdapter 를 상속하는 설정 객체에 붙혀주면 SpringSecurityFilterChain에 등록된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();//비활성화
        http.authorizeRequests().antMatchers("/user/**").authenticated()
        .antMatchers("/manager/**").access(hasRole("ROLE_ADMIN"),hasRole("ROLE_MANAGER"));


        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);//강제 인코딩을 설정
//        이 설정을 true로 하면, 요청이나 응답에서 설정한 인코딩을 강제로 적용함
//        이렇게 함으로써 어떤 브라우저나 클라이언트가 설정한 인코딩과 관계없이 항상 UTF-8 인코딩을 사용함
        http.addFilterBefore(filter, CsrfFilter.class);
//        생성한 CharacterEncodingFilter를 http 보안 설정에 추가
//        CharacterEncodingFilter가 CsrfFilter 앞에 추가되므로, 먼저 요청의 문자 인코딩을 처리한 후에 CSRF 필터가 동작하게 됌
    }
}
