package net.doodle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.doodle.entity.Reply;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyDTO {

    private Long rno;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private Long boardId;
    private Long memberId;

    public ReplyDTO(Reply reply) {
        rno = reply.getRno();
        content = reply.getContent();
        regDate = reply.getRegDate();
        modDate = reply.getModDate();
        boardId = reply.getBoard().getBno();
        memberId = reply.getMember().getMno();
    }
}
