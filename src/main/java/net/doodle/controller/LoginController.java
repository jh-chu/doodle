package net.doodle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.controller.form.LoginForm;
import net.doodle.controller.form.LoginMember;
import net.doodle.entity.Member;
import net.doodle.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final MemberService memberService;

    /**
     * TODO : LoginController 로그인, 로그아웃, 회원가입 구현 및 LoginForm 검증처리
     */

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm) {

        return "login/loginForm";
    }

    /**
     * TODO: session 처리
     */
    @PostMapping("/login")
    public String loginCheck(@Validated @ModelAttribute LoginForm loginForm,
                             BindingResult bindingResult,
                             @RequestParam(defaultValue = "/") String redirectURL,
                             HttpServletRequest request) {

        if(bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Optional<Member> result = memberService.checkLogin(loginForm.getId(), loginForm.getPwd());


        if(result.isEmpty()) {
            bindingResult.reject(null, "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "login/loginForm";
        }

        Member loginMember = result.get();
        HttpSession session = request.getSession();
        session.setAttribute("loginMember",new LoginMember(loginMember.getMno(), loginMember.getLoginId(), loginMember.getName()));

        return "redirect:"+redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
