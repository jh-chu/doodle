package net.doodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.dto.MemberDTO;
import net.doodle.entity.Member;
import net.doodle.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Optional<MemberDTO> checkLogin(String id, String pwd) {

        Optional<MemberDTO> checkMember = memberRepository.findByLoginId(id)
                .filter(member -> member.getPwd().equals(pwd)).map(MemberDTO::new);

        return checkMember;
    }

    @Override
    public Long join(String loginId, String pwd, String name) {

        if(memberRepository.existsByLoginId(loginId)) {
            throw new RuntimeException("이미 존재하는 회원입니다.");
        }

        Member member = Member.builder()
                .loginId(loginId)
                .pwd(pwd)
                .name(name)
                .build();

        Member joinMember = memberRepository.save(member);
        log.info("회원가입 member ={}",joinMember);

        return joinMember.getMno();
    }

    @Override
    public MemberDTO getMember(String loginId) {

        return memberRepository.findByLoginId(loginId)
                .map(MemberDTO::new)
                .orElseThrow(() -> new RuntimeException("존재 하지 않는 회원입니다."));

    }

    @Override
    public void deleteMember(String loginId) {

        memberRepository.deleteByLoginId(loginId);

    }

    @Override
    public Long changePwd(String loginId, String pwd) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        member.changePwd(pwd);

        return member.getMno();
    }
}
