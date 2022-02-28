package net.doodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.dto.MemberDTO;
import net.doodle.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;


    @Override
    public Long join(String id, String pwd, String name) {

        return null;
    }

    @Override
    public MemberDTO get(Long memberId) {
        return null;
    }

    @Override
    public Long update() {
        return null;
    }

    @Override
    public void delete() {

    }
}
