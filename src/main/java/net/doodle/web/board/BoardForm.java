package net.doodle.web.board;

import lombok.Data;

@Data
public class BoardForm {

    private String boardId;
    private String title;
    private String writer;
}
