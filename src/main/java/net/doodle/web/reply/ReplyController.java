package net.doodle.web.reply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.web.dto.ReplyDTO;
import net.doodle.domain.reply.Reply;
import net.doodle.domain.board.BoardService;
import net.doodle.domain.reply.ReplyService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reply")
public class ReplyController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/{bno}")
    public Page<ReplyDTO> getReplyList(@PathVariable("bno") Long bno) {

        Page<Reply> result = replyService.getReplyList(0, 10, bno);

        return result.map(ReplyDTO::new);

    }

    @DeleteMapping("/{rno}")
    public void deleteReply(@PathVariable("rno") Long rno) {

        replyService.deleteById(rno);
    }

    @PostMapping("/{bno}")
    public Long addReply(@PathVariable("bno") Long bno, ReplyForm replyForm) {

        String content = replyForm.getContent();
        Long writerId = replyForm.getWriterId();

        Long rno = replyService.addReply(writerId, bno, content);

        return rno;
    }
}
