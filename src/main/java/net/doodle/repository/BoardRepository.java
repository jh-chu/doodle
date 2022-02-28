package net.doodle.repository;

import net.doodle.entity.Board;
import net.doodle.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByMember(Member member);

    Optional<Board> findFirstByMember(Member member);
}
