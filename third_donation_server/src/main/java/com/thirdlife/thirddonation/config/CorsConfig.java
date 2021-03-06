package com.thirdlife.thirddonation.config;

import com.thirdlife.thirddonation.common.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS 스프링 빈 설정 클래스입니다.
 */
@Configuration
public class CorsConfig {

    /**
     * 스프링 빈에 CorsFilter 를 설정합니다.
     *
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);   // 내 서버가 응답할 때, JSON을 자바스크립트에서 처리할 수 있게 할지 설정
        config.addAllowedOriginPattern("*");   // 모든 ip에 응답 허용
        config.addAllowedHeader("*");   // 모든 header에 응답 허용
        config.addExposedHeader(JwtTokenUtil.HEADER_STRING); //Authorization 정보를 담을 수 있게 허용
        config.addAllowedMethod("*");   // 모든 post, get, put, delete, patch 요청을 허용
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
