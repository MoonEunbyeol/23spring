package com.example.ch16.config.security;

import com.example.ch16.dto.EntryPointErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.build.EntryPoint;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("[commence] 인증 실패로 에러 발생");
        ObjectMapper objectMapper = new ObjectMapper();
        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();
        entryPointErrorResponse.setMsg("인증이 실패하였습니다.");

        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
        // 컨트롤러에서는 자동으로 json 형태로 (return) 보내나 이 영역은 컨트롤이 안되서 json 형태로 만들어서 보내줘야함
    }
}
