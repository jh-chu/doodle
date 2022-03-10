package net.doodle.service;

import net.doodle.entity.Reply;
import org.springframework.data.domain.Page;

public interface ReplyService {

    Long addReply(Long memberId, Long boardId, String content);
    Page<Reply> getReplyList(int page, int size, Long boardId);
    void deleteById(Long replyId);
    void update(Long replyId, String content);
}
