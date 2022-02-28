package net.doodle.service;

import net.doodle.dto.BoardDTO;
import net.doodle.entity.Board;
import net.doodle.entity.Member;
import net.doodle.repository.BoardRepository;
import net.doodle.repository.MemberRepository;
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
        Page<BoardDTO> result = boardService.getList(page,size);
        assertThat(result.getTotalElements()).isEqualTo(9L);
    }

    @Test
    void create_테스트() {

        String title = "testTitle";
        String content = "testContent";
        Long writerId = memberRepository.findByLoginId("chu1").get().getMno();

        Long boardId = boardService.create(writerId, title,content);
        BoardDTO boardDTO = boardService.get(boardId);

        assertThat(boardDTO.getTitle()).isEqualTo(title);
        assertThat(boardDTO.getContent()).isEqualTo(content);
    }

    @Test
    void delete_테스트() {

        Member member = memberRepository.findByLoginId("chu1").get();
        Long bno = boardRepository.findFirstByMember(member).get().getBno();

        boardRepository.deleteById(bno);
        Optional<Board> findBoard = boardRepository.findById(bno);

        assertThat(findBoard.isEmpty()).isTrue();


        List<Board> list = boardRepository.findByMember(member);
        assertThat(list.size()).isEqualTo(8);

    }

}