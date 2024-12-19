// package com.transbnk.aws_task.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.transbnk.aws_task.api_key.ApiFilter;

// import lombok.RequiredArgsConstructor;

// @Configuration
// @RequiredArgsConstructor
// public class SecurityConfig {

//     private final ApiFilter apiFilter;

//     private final AuthEntryPointJwt unAuthEntryPointJwt;


//     @Bean
//     SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
      
//         http.authorizeHttpRequests((requests) -> requests.requestMatchers("/v1/user/hi","/actuator/**").permitAll()
//                 .anyRequest().authenticated());
//         http.exceptionHandling(exception -> exception.authenticationEntryPoint(unAuthEntryPointJwt));
//         http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//         http.addFilterBefore(apiFilter, UsernamePasswordAuthenticationFilter.class);
//         http.csrf(csrf -> csrf.disable());
//         // http.httpBasic(Customizer.withDefaults());
//         return http.build();
//     }

// }
