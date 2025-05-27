package com.codespace.tutorias.JWT.Security;

import com.codespace.tutorias.JWT.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/tutorias/*", "/tutorado/*", "/tutor/*", "/horarios/*");
        return registrationBean;
    }
}
