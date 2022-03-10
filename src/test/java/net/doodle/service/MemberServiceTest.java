package net.doodle.service;

import net.doodle.entity.Member;
import net.doodle.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @BeforeEach
    void 데이터_입력() {
        IntStream.rangeClosed(1,10).forEach(
                (count)->{
                    Member member = Member.builder()
                            .loginId("chu" + count)
                            .pwd("123")
                            .name("name" + count)
                            .build();
                    memberRepository.save(member);
                }
        );
    }


    @Test
    void login_check_테스트() {

        String id = "chu1";
        String pwd = "123";
        String wrongPwd = "312";
        String name = "name1";

        Optional<Member> successMember = memberService.checkLogin(id,pwd);
        Optional<Member> failMember = memberService.checkLogin(id,wrongPwd);

        assertThat(successMember.get().getLoginId()).isEqualTo(id);
        assertThat(successMember.get().getName()).isEqualTo(name);

        assertThat(failMember.isEmpty()).isTrue();

    }

    @Test
    void join_테스트() {

        String loginId = "jh";
        String pwd = "123";
        String name = "chu";
        String duplicatedLoginId = "chu1";

        Long mno = memberService.join(loginId, pwd, name);

        Member member = memberRepository.findById(mno).get();

        assertThat(member.getLoginId()).isEqualTo(loginId);
        assertThat(member.getPwd()).isEqualTo(pwd);
        assertThat(member.getName()).isEqualTo(name);

        assertThatThrownBy(()->{
            memberService.join(duplicatedLoginId, pwd, name);
        }).isInstanceOf(RuntimeException.class);

    }

    @Test
    void getMember_테스트() {

        String loginId = "chu1";
        String notExistsId = "chuuuu";

        Member member = memberService.getMember(loginId).get();

        assertThat(member.getPwd()).isEqualTo("123");
        assertThat(member.getName()).isEqualTo("name1");

        Optional<Member> notExistsMember = memberService.getMember(notExistsId);

        assertThat(notExistsMember.isEmpty()).isTrue();
    }

    @Test
    void deleteMember_테스트() {

        String loginId = "chu2";

        memberService.deleteMember(loginId);

        Optional<Member> findMember = memberRepository.findByLoginId(loginId);

        assertThat(findMember.isEmpty()).isTrue();

    }

    @Test
    void changePwd_테스트() {

        String loginId = "chu1";
        String pwd = "456";

        memberService.changePwd(loginId, pwd);

        Member member = memberService.getMember(loginId).get();

        assertThat(member.getPwd()).isEqualTo(pwd);
    }

}