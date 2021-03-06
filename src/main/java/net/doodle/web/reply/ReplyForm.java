package net.doodle.web.reply;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ReplyForm {

    @NotEmpty
    private Long writerId;

    @NotEmpty
    private Long boardId;

    @NotEmpty
    private String content;
}
