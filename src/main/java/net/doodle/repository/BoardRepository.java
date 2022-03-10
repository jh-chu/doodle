package net.doodle.repository;

import net.doodle.entity.Board;
import net.doodle.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {


    @Query("select b from Board b where b.member.mno = :mno")
    List<Board> findByMemberId(@Param("mno")Long mno);

    Optional<Board> findFirstByMember(Member member);

}
