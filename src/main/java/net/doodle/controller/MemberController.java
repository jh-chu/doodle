package net.doodle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.controller.form.LoginMember;
import net.doodle.controller.form.MemberSaveForm;
import net.doodle.controller.form.MemberUpdateForm;
import net.doodle.dto.MemberDTO;
import net.doodle.entity.Member;
import net.doodle.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * TODO : 현재 MemberController 미구현상태, CRUD 메소드 구현
     * TODO : bootstrap 을 이용한 기본적인 뷰 구현
     */

    @GetMapping("/add")
    public String getAdd(Model model) {
        model.addAttribute("member",new MemberSaveForm());
        return "members/addForm";
    }

    @PostMapping("/add")
    public String postAdd(@Validated @ModelAttribute("member") MemberSaveForm memberSaveForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "members/addForm";
        }

        memberService.join(memberSaveForm.getId(), memberSaveForm.getPwd(), memberSaveForm.getName());

        return "redirect:/";
    }

    @GetMapping("/my-page")
    public String getMyPage(HttpServletRequest request, Model model) {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        /**
         * 인터셉터로 로그인 체크 함.
         */
/*
        if(session == null || session.getAttribute("loginMember") == null) {
            return "redirect:/" + requestURI;
        }
*/

        LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
        Optional<Member> findMember = memberService.getMember(loginMember.getId());

        if(findMember.isPresent()) {
            MemberDTO dto = findMember.map(MemberDTO::new).get();
            model.addAttribute("member", dto);
            return "members/mypage";
        }

        return "redirect:/" + requestURI;

    }

    /**
     * TODO : Member 속성과 Update 속성 정하고, MemberService :: updateMember 구현하기
     */
    @PostMapping("/my-page")
    public String updateMyPage(@ModelAttribute("member") MemberUpdateForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            bindingResult.reject(null, "잘 못 입력하셨습니다.");
            return "members/mypage";
        }

        memberService.updateMember();

        return "redirect:/my-page";
    }

    @DeleteMapping("/members/{memberId}")
    public String deleteMember(@PathVariable("memberId") Long memberId, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        session.invalidate();

        memberService.deleteMember(memberId);
        log.info("회원 삭제 = {}", memberId);
        return "redirect:/";
    }

}
