package net.doodle.repository;

import net.doodle.entity.Board;
import net.doodle.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByBoard(Board board);
}
