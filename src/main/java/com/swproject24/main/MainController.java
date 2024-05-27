package com.swproject24.main;

import com.swproject24.account.CurrentUser;
import com.swproject24.domain.Account;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class MainController {

    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model) {
        if (account != null) {
            model.addAttribute(account);

        }

        return "index";
    }


    @GetMapping("login")
    public String login() {
        return "login";
    }
}
