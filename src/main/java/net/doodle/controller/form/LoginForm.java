package net.doodle.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank
    private String id;

    @NotBlank
    private String pwd;
}
