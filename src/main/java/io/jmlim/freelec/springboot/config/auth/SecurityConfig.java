package io.jmlim.freelec.springboot.config.auth;

import io.jmlim.freelec.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOauth2UserService customOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests() // URL 별 권한관리를 설정하는 옵션의 시작점. (이게 선언되어야 antMatchers 옵션 사용가능
                    .antMatchers("/","/css/**", "/images/**","/js/**","/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 권한 가진사람만 열람가능
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/")
                .and()
                    // oauth2 로그인 기능에 대한 진입점
                    .oauth2Login()
                    // 사용자 정보를 가져올 때의 설정들을 담당
                    .userInfoEndpoint()
                    // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                    .userService(customOauth2UserService);

    }
}
