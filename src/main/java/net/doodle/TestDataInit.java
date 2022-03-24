package net.doodle;

import lombok.RequiredArgsConstructor;
import net.doodle.domain.board.Board;
import net.doodle.domain.member.Member;
import net.doodle.domain.board.BoardRepository;
import net.doodle.domain.member.MemberRepository;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @PostConstruct
    public void init() {
        IntStream.rangeClosed(1,10).forEach(
                (count)->
                {
                    Member member = Member.builder()
                            .loginId("chu" + count)
                            .name("name" + count)
                            .pwd("123")
                            .build();
                    Member saveMember = memberRepository.save(member);

                    IntStream.rangeClosed(1,10).forEach(
                            (count2)-> {
                                Board board = Board.builder()
                                        .member(saveMember)
                                        .title("title" + count2)
                                        .content("content" + count2)
                                        .build();
                                boardRepository.save(board);
                            }
                    );
                }
        );

    }
}
