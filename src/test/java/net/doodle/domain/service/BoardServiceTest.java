package net.doodle.domain.service;

import net.doodle.domain.board.Board;
import net.doodle.domain.board.BoardServiceImpl;
import net.doodle.domain.member.Member;
import net.doodle.domain.board.BoardRepository;
import net.doodle.domain.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardServiceImpl boardService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    /**
     * 멤버 10명, 게시글 9개 입력
     */
    @BeforeEach
    void 데이터_입력() {
        IntStream.rangeClosed(1,10).forEach(
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

        IntStream.rangeClosed(1,9).forEach(
                (count)->{
                    Board board = Board.builder()
                            .title("title" + count)
                            .content("content" + count)
                            .member(chu1)
                            .build();
                    boardRepository.save(board);
                }
        );

    }

    @Test
    void getList_테스트() {

        int page = 0;
        int size = 10;
        Page<Board> result = boardService.getList(page,size);
        assertThat(result.getTotalElements()).isEqualTo(9L);
    }

    @Test
    void create_테스트() {

        String title = "testTitle";
        String content = "testContent";
        Long writerId = memberRepository.findByLoginId("chu1").get().getMno();

        Long boardId = boardService.create(writerId, title,content);
        Board board = boardService.getBoard(boardId).get();

        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
    }

    @Test
    void delete_테스트() {

        Member member = memberRepository.findByLoginId("chu1").get();
        Long bno = boardRepository.findFirstByMember(member).get().getBno();

        boardRepository.deleteById(bno);
        Optional<Board> findBoard = boardRepository.findById(bno);

        assertThat(findBoard.isEmpty()).isTrue();


        List<Board> list = boardRepository.findByMemberId(member.getMno());
        assertThat(list.size()).isEqualTo(8);

    }

    @Test
    void update_테스트() {

        String title = "changeTitle";
        String content = "changeContent";

        Board board = boardRepository.findAll().stream().findFirst().get();
        board.changeTitleAndContent(title,content);

        Long bno = board.getBno();

        Board findBoard = boardRepository.findById(bno).get();

        assertThat(findBoard.getTitle()).isEqualTo(title);
        assertThat(findBoard.getContent()).isEqualTo(content);

    }

}