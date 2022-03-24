package net.doodle.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import net.doodle.domain.member.MemberRepository;
import net.doodle.domain.member.Member;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    @Override
    public Long create(Long mno, String title, String content) {
        Member writer = memberRepository.findById(mno).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        });

        Board board = Board.builder()
                .member(writer)
                .title(title)
                .content(content)
                .build();

        return boardRepository.save(board).getBno();
    }

    @Override
    public Optional<Board> getBoard(Long bno) {

        return boardRepository.findById(bno);

    }

    @Override
    public Page<Board> getList(int page, int size) {

        return boardRepository.findAll(PageRequest.of(page, size));


    }

    @Override
    public void delete(Long bno) {
        boardRepository.deleteById(bno);
        log.info(bno+"가 삭제 되었습니다.");
    }

    @Override
    public Long update(Long bno, String title, String content) {

        Board board = boardRepository.findById(bno).orElseThrow(()->new RuntimeException("없는 게시글 입니다. " + bno));
        board.changeTitleAndContent(title, content);

        return board.getBno();
    }


}
