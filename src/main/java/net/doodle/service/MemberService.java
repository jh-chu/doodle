package net.doodle.service;

import net.doodle.dto.MemberDTO;

import java.util.Optional;

public interface MemberService {

    Optional<MemberDTO> checkLogin(String loginId, String pwd);
    Long join(String loginId, String pwd, String name);
    MemberDTO getMember(String loginId);
    void deleteMember(String loginId);

    Long changePwd(String loginId, String pwd);
}
