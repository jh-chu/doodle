package net.doodle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.doodle.entity.Member;


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

    public static MemberDTO ofIdAndName(Member member) {

        return MemberDTO.builder()
                .mno(member.getMno())
                .name(member.getName())
                .build();
    }
}
