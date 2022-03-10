package net.doodle.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BoardUpdateForm {

    @NotBlank
    private Long bno;

    @NotBlank
    private String title;

    @NotNull
    private String content;
}
