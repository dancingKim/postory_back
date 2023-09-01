package com.jungsuk_2_1.postory.config;

import com.jungsuk_2_1.postory.security.JwtAuthenticationFilter;
import com.jungsuk_2_1.postory.security.OAuthSuccessHandler;
import com.jungsuk_2_1.postory.security.RedirectUrlCookieFilter;
import com.jungsuk_2_1.postory.service.CustomOAuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@Slf4j
//DispatcherServlet이 실행되기 전에 가장 먼저 실행 됨
//Spring Security의 초기 설정을 구성하기 위함
public class WebSecurityConfig {
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private CustomOAuthUserService oAuthUserService;

    private OAuthSuccessHandler oAuthSuccessHandler;
    private RedirectUrlCookieFilter redirectUrlFilter;

    @Autowired
    WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomOAuthUserService oAuthUserService, OAuthSuccessHandler oAuthSuccessHandler, RedirectUrlCookieFilter redirectUrlFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.oAuthUserService = oAuthUserService;
        this.oAuthSuccessHandler = oAuthSuccessHandler;
        this.redirectUrlFilter = redirectUrlFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                        .sessionManagement((session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                .authorizeHttpRequests((authorize)-> authorize.requestMatchers(
                        "/",
                                "/auth/**",
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/favicon.ico")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login((oauth2Login) ->oauth2Login.redirectionEndpoint(
                        (redirect) -> redirect
                                .baseUri("/login/oauth2/code/**"))
                        .authorizationEndpoint((authEnd) ->authEnd.baseUri("/auth/authorize"))
                        .userInfoEndpoint(userInfoEnd -> userInfoEnd.userService(oAuthUserService))
                        .successHandler(oAuthSuccessHandler)).exceptionHandling(exceptHandle->exceptHandle.authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
        ;
        // filter 등록
        // 매 요청마다
        // CorsFilter 실행한 후에
        // jwtAuthenticationFilter 실행한다.
        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
        http.addFilterBefore(
                redirectUrlFilter,
                OAuth2AuthorizationRequestRedirectFilter.class
        );
        return http.build();
    }
    @Bean
    @CrossOrigin
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "http://3.39.6.61:3000"));
        configuration.setAllowedMethods(Arrays.asList("HEAD","GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
//WebSecurityConfigurerAdapter - Deprecated
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //http 시큐리티 필터
//        http.cors()//WebMvcConfig에서 이미 설정했으므로 기본 cors 설정.
//                .and()
//                .csrf() //csrf는 현재 사용하지 않으므로 disable
//                .disable()
//                .httpBasic() //token을 사용하므로 basic 인증 disable
//                .disable()
//                .sessionManagement() //session 기반이 아님을 선언
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests() // "/" 와 " /auth/** " 경로는 인증 안 해도 됨.
//                .requestMatchers("/", "/auth/**").permitAll()
//                .anyRequest() // "/" 와 " /auth/** " 경로 이외에는 인증 해야 됨.
//                .authenticated();
//
//        // filter 등록
//        // 매 요청마다
//        // CorsFilter 실행한 후에
//        // jwtAuthenticationFilter 실행한다.
//        http.addFilterAfter(
//                jwtAuthenticationFilter,
//                CorsFilter.class
//        );
//    }
//}
