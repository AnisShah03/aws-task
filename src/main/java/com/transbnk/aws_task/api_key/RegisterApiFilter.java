// package com.transbnk.aws_task.api_key;

// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class RegisterApiFilter {

//     @Bean
//     FilterRegistrationBean<ApiFilter> registerFilterBean(ApiFilter apiFilter) {
//         FilterRegistrationBean<ApiFilter> filterRegistrationBean = new FilterRegistrationBean<>();
//         filterRegistrationBean.setFilter(apiFilter);
//         filterRegistrationBean.addUrlPatterns("/v1/user/db/*");  // Apply to any path under /v1/user/db/
//         return filterRegistrationBean;
//     }

// }
