package com.swproject24.account;


import com.swproject24.domain.Account;
import com.swproject24.settings.Profile;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final JavaMailSender javamailSender;
    private final PasswordEncoder passwordEncoder;


// 회원가입을 처리하고, 새로운 계정을 생성하는 메소드
    public Account processNewAccount(SignUpForm signUpForm) {
        Account newAccount = saveNewAccount(signUpForm);
        newAccount.generateEmailCheckToken();
        sendSignUpConfirmEmail(newAccount);
        return newAccount;

    }

// 회원가입 양식을 기반으로 새로운 계정을 저장하는 메소드
    private Account saveNewAccount(@Valid SignUpForm signUpForm){
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword())) // 비밀번호 인코딩? 평문x
                .studyEnrollmentResultByWeb(true)
                .studyCreatedByWeb(true)
                .studyUpdatedByWeb(true)
                .build();
        return accountRepository.save(account);
    }

// 회원가입 확인 이메일을 보내는 메소드
    private void sendSignUpConfirmEmail(Account newAccount){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText("/check-email-token=" + newAccount.getEmailCheckToken() + "&mail=" + newAccount.getEmail());
        mailMessage.setSubject("회원 가입 인증");
        mailMessage.setTo(newAccount.getEmail());
        javamailSender.send(mailMessage);
    }


    //사용자를 로그인 상태로 설정하는 메소드
    public void login(Account account) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                account.getNickname(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);


    }

    // 사용자 정보를 이용하여 스프링 시큐리티 UserDetails를 반환하는 메소드
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String emailOrNickname) throws UsernameNotFoundException {
        // 사용자의 이메일 또는 닉네임을 기반으로 계정을 조회
        Account account = accountRepository.findByEmail(emailOrNickname);
        if (account == null) {
            // 이메일로 찾지 못하면 닉네임으로 다시 조회
            account = accountRepository.findByNickname(emailOrNickname);

        }

        if (account == null) {
            // 계정이 존재하지 않으면 예외
            throw new UsernameNotFoundException(emailOrNickname);
        }
        return new UserAccount(account);
    }

    // 회원가입을 완료하고 사용자를 로그인 상태로 변경하는 메소드
    public void completeSignUp(Account account) {
        account.completeSignUp();
        login(account);
    }

    //사용자의 프로필을 업데이트 하는 메소드
    public void updateProfile(Account account, Profile profile) {
        account.setUrl(profile.getUrl());
        account.setOccupation(profile.getOccupation());
        account.setLocation(profile.getLocation());
        account.setBio(profile.getBio());
        accountRepository.save(account);
    }
}
