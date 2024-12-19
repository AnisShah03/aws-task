// package com.transbnk.aws_task.api_key;

// import java.io.IOException;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class ApiFilter extends OncePerRequestFilter {

//     @Value("${spring.config.api-key.header}")
//     private String apiKeyHeader;

//     @Value("${spring.config.api-key.prefix}")
//     private String apiKeyPrefix;

//     @Value("${spring.config.api}")
//     private String API;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {

//         // important https://chatgpt.com/share/676160f2-4a14-8001-aa26-18e64a0b6126
//         // Skip the API key validation for /v1/user/hi endpoint
//         if (request.getRequestURI().startsWith("/v1/user/hi") || request.getRequestURI().startsWith("/actuator")) {
//             filterChain.doFilter(request, response);
//             return; // Skip further processing (no API key check for this path)
//         }

//         String header = request.getHeader(apiKeyHeader);
//         if (header == null || !header.startsWith(apiKeyPrefix)) {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             response.getWriter().write("api key is null or invalid");
//             return;
//         }

//         String apiKey = header.substring(apiKeyPrefix.length() + 1);
//         if (!isValid(apiKey)) {
//             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//             response.getWriter().write("invalid api");
//             return;
//         }
//         filterChain.doFilter(request, response);

//     }

//     private boolean isValid(String apiKey) {
//         return API.equals(apiKey);
//     }

// }
