package net.doodle.service;

import net.doodle.entity.Board;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface BoardService {
    public Long create(Long mno, String title, String content);
    public Optional<Board> getBoard(Long bno);
    public Page<Board> getList(int page, int size);
    public void delete(Long bno);
    public Long update(Long bno, String title, String content);
}
