package net.doodle.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.doodle.domain.board.Board;

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

    // member.name
    private String writer;

//    private MemberDTO memberDTO;

    public BoardDTO(Board board) {
        bno = board.getBno();
        title = board.getTitle();
        content = board.getContent();

        regDate = board.getRegDate();
        modDate = board.getModDate();

        writer = board.getMember().getName();

//        memberDTO = MemberDTO.ofIdAndName(board.getMember());
    }
}
