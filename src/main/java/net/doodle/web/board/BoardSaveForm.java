package net.doodle.web.board;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BoardSaveForm {

    @NotEmpty
    private String title;

    private String content;
}
