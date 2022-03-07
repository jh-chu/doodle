package net.doodle.service;

import net.doodle.dto.BoardDTO;
import org.springframework.data.domain.Page;


public interface BoardService {
    public Long create(Long mno, String title, String content);
    public BoardDTO get(Long bno);
    public Page<BoardDTO> getList(int page, int size);
    public void delete(Long bno);
    public Long update(Long bno, String title, String content);
}
