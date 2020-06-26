package in.hocg.eagle.basic.ext.security;

import in.hocg.eagle.basic.ext.security.authentication.token.TokenAuthenticationEndpoint;
import in.hocg.eagle.basic.ext.security.authentication.token.TokenAuthenticationFilter;
import in.hocg.eagle.basic.ext.security.social.SocialUserService;
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
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;

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
    private final SocialUserService socialUserService;

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
                // 短链接
                "/s/*",
                "/wx-mp/*",
                "/social/**",
                "/api/wx/material/**",
                "/",
                // 支付网关
                "/payment/**",
                "/api/order/async/*",
                // 登录
                "/login/oauth2/code/*",
                // 注册账号
                "/sign-up",
                // 验证码
                "/sms-code",
                "/captcha",
                TokenAuthenticationEndpoint.ACCOUNT_TOKEN_URI).permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
//            .authorizationEndpoint()
//            .authorizationRequestRepository(new HttpSessionOAuth2AuthorizationRequestRepository())
//            .and()
//            .tokenEndpoint().accessTokenResponseClient()
//            .and()
            .userInfoEndpoint()
            .userService(socialUserService)
        ;

        http.exceptionHandling()
            .defaultAuthenticationEntryPointFor(new AjaxAuthenticationEntryPoint(), new IsAjaxRequestMatcher())
            .defaultAccessDeniedHandlerFor(new AjaxAccessDeniedHandler(), new IsAjaxRequestMatcher());

        // ==== Token 登录方式 ====
        {
            http.addFilterBefore(new TokenAuthenticationFilter(), OAuth2AuthorizationRequestRedirectFilter.class);
        }
    }
}
