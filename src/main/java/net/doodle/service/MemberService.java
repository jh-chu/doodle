package net.doodle.service;

import net.doodle.entity.Member;

import java.util.Optional;

public interface MemberService {

    Optional<Member> checkLogin(String loginId, String pwd);
    Long join(String loginId, String pwd, String name);
    Optional<Member> getMember(String loginId);
    Optional<Member> getMember(Long id);
    void deleteMember(String loginId);
    void deleteMember(Long id);

    Long changePwd(String loginId, String pwd);

    void updateMember();

}
