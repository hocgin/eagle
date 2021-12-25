package in.hocg.eagle.basic.ext.security;

import in.hocg.eagle.basic.ext.security.authentication.token.TokenAuthenticationEndpoint;
import in.hocg.eagle.basic.ext.security.authentication.token.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by hocgin on 2020/1/6.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.debug(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(
                "/actuator/**",
                "/v2/api-docs",
                // 短链接
                "/s/*",
                "/wx-mp/*",
                "/social/**",
                "/api/wx/material/**",
                "/",
                // 支付网关
                "/payment/**",
                "/api/order/async/*",

                // = APP
                "/api-mini/**",

                // 变更密码:验证码
                "/api/change-password:sms-code",
                // 登录:密码
                "/login/oauth2/code/*",
                // 登录:验证码
                "/api/account/authenticate:sms-code",
                // 注册账号
                "/api/sign-up",
                // 发送验证码
                "/api/sms-code",
                "/captcha",
                TokenAuthenticationEndpoint.ACCOUNT_TOKEN_URI).permitAll()
            .anyRequest().authenticated()
//            .authorizationEndpoint()
//            .authorizationRequestRepository(new HttpSessionOAuth2AuthorizationRequestRepository())
//            .and()
//            .tokenEndpoint().accessTokenResponseClient()
//            .and()
        ;

        http.exceptionHandling()
            .defaultAuthenticationEntryPointFor(new AjaxAuthenticationEntryPoint(), new IsAjaxRequestMatcher())
            .defaultAccessDeniedHandlerFor(new AjaxAccessDeniedHandler(), new IsAjaxRequestMatcher());

        // ==== Token 登录方式 ====
        {
            http.addFilterBefore(new TokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        }
    }
}
