package net.doodle.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.dto.ReplyDTO;
import net.doodle.entity.Reply;
import net.doodle.service.BoardService;
import net.doodle.service.ReplyService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reply")
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
