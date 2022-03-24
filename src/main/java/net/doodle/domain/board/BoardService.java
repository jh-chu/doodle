package net.doodle.domain.board;

import net.doodle.domain.board.Board;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface BoardService {
    public Long create(Long mno, String title, String content);
    public Optional<Board> getBoard(Long bno);
    public Page<Board> getList(int page, int size);
    public void delete(Long bno);
    public Long update(Long bno, String title, String content);
}
