package com.swproject24.main;

import com.swproject24.account.CurrentUser;
import com.swproject24.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);
        } else {
            model.addAttribute("account", new Account()); // 빈 Account 객체 추가
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // login.html을 반환합니다.
    }

    @GetMapping("/error")
    public String handleError() {
        // Custom error handling logic
        return "error";
    }
}