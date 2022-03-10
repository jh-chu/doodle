package net.doodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.dto.ReplyDTO;
import net.doodle.entity.Board;
import net.doodle.entity.Member;
import net.doodle.entity.Reply;
import net.doodle.repository.BoardRepository;
import net.doodle.repository.MemberRepository;
import net.doodle.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public Long addReply(Long memberId, Long boardId, String content) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 입니다."));

        Reply reply = Reply.builder()
                .member(member)
                .board(board)
                .content(content)
                .build();

        replyRepository.save(reply);

        return reply.getRno();
    }

    public Page<Reply> getReplyList(int page, int size, Long bno) {

        Optional<Board> board = boardRepository.findById(bno);
        if(board.isPresent()) {

            Page<Reply> result = replyRepository.findByBoard(board.get(), PageRequest.of(page, size));
            return result;
        }

        return Page.empty();
    }

    public void deleteById(Long rno) {

        replyRepository.deleteById(rno);
        log.info("deleted reply no = {}" + rno);
    }

    @Override
    public void update(Long rno, String content) {

        Reply reply = replyRepository.findById(rno).orElseThrow(() -> new RuntimeException("존재하지 않는 댓글 입니다."));
        reply.changeContent(content);

    }


}
