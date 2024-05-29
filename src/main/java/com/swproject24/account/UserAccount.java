package com.swproject24.account;

import com.swproject24.domain.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {

    // 사용자 계정 정보를 담고 있는 Account 객체
    private final Account account;

    // 사용자 계정 정보를 기반으로 UserAccount 객체를 생성
    public UserAccount(Account account) {
        super(account.getNickname(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }
}
