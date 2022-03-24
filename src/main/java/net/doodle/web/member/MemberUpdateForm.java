package net.doodle.web.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberUpdateForm {

    @NotBlank
    String id;

    @NotBlank
    String loginId;

    @NotBlank
    String pwd;

    @NotBlank
    String name;
}
