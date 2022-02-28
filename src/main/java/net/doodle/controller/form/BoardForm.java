package net.doodle.controller.form;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class BoardForm {

    @NotEmpty
    private String title;

    private String content;
}
