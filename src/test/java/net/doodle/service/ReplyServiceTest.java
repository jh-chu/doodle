package net.doodle.service;

import net.doodle.dto.ReplyDTO;
import net.doodle.entity.Board;
import net.doodle.entity.Member;
import net.doodle.entity.Reply;
import net.doodle.repository.BoardRepository;
import net.doodle.repository.MemberRepository;
import net.doodle.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
class ReplyServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ReplyService replyService;

    @Autowired
    ReplyRepository replyRepository;

    /**
     * 멤버 5명, 게시글 1개 입력
     */
    @BeforeEach
    void 데이터_입력() {
        IntStream.rangeClosed(1,5).forEach(
                (count)->{
                    Member member = Member.builder()
                            .loginId("chu" + count)
                            .pwd("123")
                            .name("chu" + count)
                            .build();
                    memberRepository.save(member);
                }
        );

        Member chu1 = memberRepository.findByLoginId("chu1").get();

        Board board = Board.builder()
                            .title("title")
                            .content("content")
                            .member(chu1)
                            .build();
        boardRepository.save(board);

    }

    @Test
    void addReply_테스트() {
        //given
        Member member = memberRepository.findByLoginId("chu1").get();
        Member member2 = memberRepository.findByLoginId("chu2").get();
        Board board = boardRepository.findByMemberId(member.getMno()).get(0);
        String content1 = "content1";
        String content2 = "content2";


        //when
        Long rno1 = replyService.addReply(member.getMno(), board.getBno(), content1);
        Long rno2 = replyService.addReply(member2.getMno(), board.getBno(), content2);


        //then
        assertThat(replyRepository.findByBoard(board).size()).isEqualTo(2);

        assertThat(replyRepository.findById(rno1).get().getContent()).isEqualTo(content1);
        assertThat(replyRepository.findById(rno2).get().getContent()).isEqualTo(content2);


    }

    @Test
    void getReplyList_테스트() {
        //given
        Member member = memberRepository.findByLoginId("chu1").get();
        Board board = boardRepository.findByMemberId(member.getMno()).get(0);
        String content = "content";

        IntStream.rangeClosed(1,19).forEach(
                (cnt)->replyService.addReply(member.getMno(), board.getBno(), content+cnt)
        );

        //when
        List<Reply> firstPage = replyService.getReplyList(0, 10, board.getBno()).getContent();
        List<Reply> secondPage = replyService.getReplyList(1, 10, board.getBno()).getContent();

        //then
        assertThat(firstPage.size()).isEqualTo(10);
        assertThat(secondPage.size()).isEqualTo(9);

    }

    @Test
    void deleteById_테스트() {
        //given
        Member member = memberRepository.findByLoginId("chu1").get();
        Board board = boardRepository.findByMemberId(member.getMno()).get(0);
        String content = "content";

        IntStream.rangeClosed(1,19).forEach(
                (cnt)->replyService.addReply(member.getMno(), board.getBno(), content+cnt)
        );

        List<Reply> list = replyService.getReplyList(0, 10, board.getBno()).getContent();
        Long reply1 = list.get(0).getRno();
        Long reply2 = list.get(1).getRno();

        //when
        replyService.deleteById(reply1);
        replyService.deleteById(reply2);


        //then
        assertThat(replyRepository.findAll().size()).isEqualTo(17);

    }

    @Test
    void update_테스트() {
        //given
        Member member = memberRepository.findByLoginId("chu1").get();
        Board board = boardRepository.findByMemberId(member.getMno()).get(0);
        String content = "content";
        String update = "update";
        Long replyId = replyService.addReply(member.getMno(), board.getBno(), content);


        //when
        replyService.update(replyId,update);


        //then
        Reply reply = replyRepository.findById(replyId).get();
        assertThat(reply.getContent()).isEqualTo(update);

    }
}