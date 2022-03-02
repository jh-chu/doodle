package net.doodle.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BoardForm {

    @NotEmpty
    private String title;

    private String content;
}
