package net.doodle.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberSaveForm {

    @NotBlank
    String id;

    @NotBlank
    String pwd;

    @NotBlank
    String name;
}
