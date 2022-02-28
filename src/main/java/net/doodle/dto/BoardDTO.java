package net.doodle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.doodle.entity.Board;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {

    private Long bno;
    private String title;
    private String content;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private MemberDTO memberDTO;

    public BoardDTO(Board board) {
        bno = board.getBno();
        title = board.getTitle();
        content = board.getContent();

        regDate = board.getRegDate();
        memberDTO = new MemberDTO(board.getMember());
    }
}
