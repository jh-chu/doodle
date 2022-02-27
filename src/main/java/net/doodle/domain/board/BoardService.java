package net.doodle.domain.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    public long createBoard() {

        Board newBoard = Board.builder().build();

        boardRepository.save(newBoard);

        return newBoard.getId();
    }

    public List<BoardDto> getList(int page, int size) {

        return boardRepository.findAll(PageRequest.of(page, size))
                .stream().map(BoardDto::new).collect(Collectors.toList());
    }
}
