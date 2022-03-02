package net.doodle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.doodle.entity.Member;

import java.util.ArrayList;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDTO {

    private Long mno;
    private String loginId;
    private String pwd;
    private String name;

    public MemberDTO(Member member) {
        mno = member.getMno();
        loginId = member.getLoginId();
        pwd = member.getPwd();
        name = member.getName();
    }

}
