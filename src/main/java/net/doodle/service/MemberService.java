package net.doodle.service;

import net.doodle.dto.MemberDTO;

public interface MemberService {
    Long join(String id, String pwd, String name);
    MemberDTO get(Long memberId);
    Long update();
    void delete();
}
