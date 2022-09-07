package com.abnamro.recipe.log;

import com.abnamro.recipe.log.filter.RecipeLoggingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogFilterConfig {

    @Bean
    @ConditionalOnProperty(prefix = "recipe.logging", name = "enabled", havingValue = "true")
    public FilterRegistrationBean<RecipeLoggingFilter> loggingFilter(){
        FilterRegistrationBean<RecipeLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RecipeLoggingFilter());

        registrationBean.addUrlPatterns("/recipe/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
