package com.example.ch16.config.security;

import com.example.ch16.service.UserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailService userDetailService;
    private final long tokenValidMilliSecond = 1000L * 60 * 60; // 60분
    private String secretKey = "daelimSPring@#$daelimSpring!@#$daelimSpring!@#$daelimSpring!@#$daelimSpring!";

    @PostConstruct
    protected void init() {
        System.out.println("[init] JwtTokenProvider init start >>> ");
        secretKey = Base64.getEncoder(). encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println("secretKey : " + secretKey);
    }

    public String createToken(String userUid, List<String> roles) {
        System.out.println("[createToken] 토큰 생성 시작 ");
        Claims claims = Jwts.claims().setSubject(userUid);
        claims.put("roles", roles);
        Date now = new Date();
        String token = Jwts.builder().setClaims(claims).
                setIssuedAt(now).
                setExpiration(new Date(now.getTime() + tokenValidMilliSecond)). // 해당 토큰 1시간 사용 가능
                signWith(SignatureAlgorithm.HS256, secretKey).compact();
        System.out.println("token : " + token);
        return token;
    }

    public Authentication getAuthentication(String token) {
        System.out.println("[getAuthentication] 토큰 정보 조회");
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUsername(token));
                return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        System.out.println("[getUsername] " + info);
        return info;
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN"); // 프로젝트별로 들어가는 내용 다르게 설정 가능.
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}