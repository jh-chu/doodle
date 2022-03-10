package net.doodle.repository;

import net.doodle.entity.Board;
import net.doodle.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

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
    void findByMemberId_테스트 () {

        Long mno = memberRepository.findAll().get(0).getMno();

        List<Board> boardList = boardRepository.findByMemberId(mno);

        for (Board board : boardList) {
            System.out.println("board = " + board);
            System.out.println("member = " + board.getMember());
        }
    }


}