package net.doodle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.controller.form.BoardForm;
import net.doodle.dto.BoardDTO;
import net.doodle.entity.Board;
import net.doodle.entity.Member;
import net.doodle.repository.BoardRepository;
import net.doodle.repository.MemberRepository;
import net.doodle.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;


    @Override
    public Long create(Long writerId, String title, String content) {
        Member writer = memberRepository.findById(writerId).orElseThrow(() -> {
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
    public BoardDTO get(Long boardId) {

        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new RuntimeException("존재하지 않는 게시글 입니다.");
        });

        BoardDTO boardDTO = new BoardDTO(findBoard);

        return boardDTO;
    }

    @Override
    public Page<BoardDTO> getList(int page, int size) {

        Page<Board> result = boardRepository.findAll(PageRequest.of(page, size));
        Pageable pageable = result.getPageable();
        List<BoardDTO> content = result.get().map(BoardDTO::new).collect(Collectors.toList());

        return new PageImpl<BoardDTO>(content, pageable, result.getTotalElements());
    }

    @Override
    public void delete(Long bno) {
        boardRepository.deleteById(bno);
        log.info(bno+"가 삭제 되었습니다.");
    }

    @Override
    public Long update(Long bno, String title, String content) {

        Board board = boardRepository.findById(bno).orElseThrow(()->new RuntimeException("없는 게시글 입니다."));
        board.changeTitleAndContent(title, content);

        return board.getBno();
    }


}
