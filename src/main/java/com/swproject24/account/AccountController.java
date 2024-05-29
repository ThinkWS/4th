package com.swproject24.account;

import com.swproject24.domain.Account;
import jakarta.validation.Valid; // jakarta.validation.Valid 어노테이션을 사용하기 위해 import 합니다.
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;




@Controller // Spring MVC 컨트롤러로 선언
@RequiredArgsConstructor // Lombok 어노테이션으로, final 필드를 자동으로 생성자로 주입
public class AccountController {

    private final SignUpFormValidator signUpFormValidator; // SignUpForm 유효성 검증기를 주입
    private final AccountService accountService; // AccountService를 주입
    private final AccountRepository accountRepository; // AccountRepository를 주입


    @InitBinder("signUpForm") // WebDataBinder를 초기화하는 메소드입니다. "signUpForm" 모델 객체에만 적용
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator); // signUpFormValidator를 추가하여 SignUpForm 객체의 유효성을 검사
    }


    @GetMapping("/sign-up") // GET 요청에 매핑되는 메소드
    public String signUpForm(Model model) { // 회원가입 폼을 보여주는 메소드
        model.addAttribute(new SignUpForm()); // SignUpForm을 모델에 추가하여 뷰에 전달
        return "account/sign-up";
    }

    @PostMapping("/sign-up") // POST 요청에 매핑되는 메소드
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors) { // 회원가입 정보를 처리
        if (errors.hasErrors()) {
            return "account/sign-up";
        }

        Account account = accountService.processNewAccount(signUpForm); // 회원가입 정보를 처리하고 새로운 계정을 생성
        accountService.login(account); // 새로 생성된 계정으로 로그인
        return "redirect:/";
    }

    @GetMapping("/check-email-token") // 이메일 토큰을 확인하는 GET 요청에 매핑되는 메소드.
    public String checkEmailToken(String token, String email, Model model) { // 이메일 토큰을 확인하는 메소드
        Account account = accountRepository.findByEmail(email); // 이메일을 사용하여 계정을 찾습니다.
        String view = "account/checked-email";
        if (account == null) { // 계정이 존재하지 않는 경우
            model.addAttribute("error", "wrong.email"); // 에러 메시지를 모델에 추가
            return view;
        }

        if (!account.isValidToken(token)) { // 토큰이 유효하지 않은 경우
            model.addAttribute("error", "wrong.token"); // 에러 메시지를 모델에 추가.
            return view; //
        }

        accountService.completeSignUp(account); // 회원가입을 완료 처리합니다.
        model.addAttribute("numberOfUser", accountRepository.count()); // 사용자 수를 모델에 추가
        model.addAttribute("nickname", account.getNickname()); // 닉네임을 모델에 추가
        return view;
    }

    @GetMapping("/profile/{nickname}") // 닉네임을 통해 프로필을 보여주는 GET 요청에 매핑되는 메소드
    public String viewProfile(@PathVariable String nickname, Model model, @CurrentAccount Account account) { // 프로필을 보여주는 메소드
        Account byNickname = accountRepository.findByNickname(nickname); // 닉네임을 사용하여 계정을 찾습니다.
        if (byNickname == null) { // 계정이 존재하지 않는 경우
            throw new IllegalArgumentException(nickname + "에 해당하는 사용자가 없습니다"); // 예외
        }

        model.addAttribute(byNickname); // 계정을 모델에 추가
        model.addAttribute("isOwner", byNickname.equals(account)); // 계정이 현재 사용자의 것인지 여부를 모델에 추가
        return "account/profile";
    }
}