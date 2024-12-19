// package com.transbnk.aws_task.config;


// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.http.MediaType;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.web.AuthenticationEntryPoint;
// import org.springframework.stereotype.Component;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// /*
//  * it provides custom handling for unauthorised request,
//  * typically when authentication is requred but not supplied or valid
//  * 
//  * 
//  * when an unauthorised request is detected, it logs the error and returns a JSON
//  * response with an error message, status code, and the path attempted (the endpoint).
//  * 
//  */

// @Component
// public class AuthEntryPointJwt implements AuthenticationEntryPoint {// ! this interface indicating this class provides
//                                                                     // ! custom handling for authentication failures

//     @Override
//     public void commence(HttpServletRequest request, HttpServletResponse response,
//             AuthenticationException authException) throws IOException, ServletException {

//         System.out.println(authException);
//         System.out.println(authException.getMessage());

//         // ! setting the response type
//         response.setContentType(MediaType.APPLICATION_JSON_VALUE);// Ensures that the client receives a JSON response.
//         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);// This tells the client that authentication is required
//                                                                 // but has failed.

//         final Map<String, Object> body = new HashMap<>();
//         body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//         body.put("error", "UnAuthorised");
//         body.put("message", authException.getMessage());
//         body.put("path", request.getServletPath());

//         ObjectMapper objectMapper = new ObjectMapper();
//         objectMapper.writeValue(response.getOutputStream(), body);// ! mapped json body to the response

//     }

// }
