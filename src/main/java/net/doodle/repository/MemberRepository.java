package net.doodle.repository;

import net.doodle.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);

    void deleteByLoginId(String loginId);
}
