package net.doodle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.controller.form.LoginForm;
import net.doodle.dto.MemberDTO;
import net.doodle.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {

        return "login/loginForm";
    }

    @PostMapping("/login")
    public String loginCheck(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult
            ,@RequestParam(defaultValue = "/") String redirectURL) {

        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Optional<MemberDTO> login = memberService.checkLogin(loginForm.getId(), loginForm.getPwd());

        if(login.isEmpty()) {
            bindingResult.reject(null, "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        return "redirect:"+redirectURL;
    }

}
