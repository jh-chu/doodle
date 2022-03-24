package net.doodle.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> checkLogin(String id, String pwd) {

        Optional<Member> checkMember = memberRepository.findByLoginId(id);

        if(checkMember.isEmpty()) {
            return Optional.empty();
        }

        Member member = checkMember.get();
        if(!member.getPwd().equals(pwd)) {
            return Optional.empty();
        }

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
    public Optional<Member> getMember(String loginId) {

        return memberRepository.findByLoginId(loginId);

    }

    @Override
    public Optional<Member> getMember(Long id) {

        return memberRepository.findById(id);

    }

    @Override
    public void deleteMember(String loginId) {

        memberRepository.deleteByLoginId(loginId);

    }

    @Override
    public void deleteMember(Long id) {

        memberRepository.deleteById(id);

    }

    @Override
    public Long changePwd(String loginId, String pwd) {

        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        member.changePwd(pwd);

        return member.getMno();
    }

    @Override
    public void updateMember() {

    }
}
