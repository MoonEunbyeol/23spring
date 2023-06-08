package com.example.ch16.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity // ID 어노테이션 지정해줘야 오류 X
@Getter
@Setter
@NoArgsConstructor // 매개변수 없는 생성자
@AllArgsConstructor // 전체 멤버를 받는 생성자
@Builder // 롬복에서 제공하는 빌더
@Table
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,  unique = true) // 필수값, 유일해야 함
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // pw는 노출이 되면 안되니까 쓰기만 가능
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//        this.roles.stream().map(item -> SimpleGrantedAuthority(item)); // SimpleGrantedAuthority::new와 같은 내용

    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료되지 않음
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠겨있지않음
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 상태
    }
}
