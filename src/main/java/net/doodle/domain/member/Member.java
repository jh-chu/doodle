package net.doodle.domain.member;

import lombok.*;
import net.doodle.domain.board.Board;
import net.doodle.domain.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"boardList"})
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    private Long mno;

    private String loginId;

    private String pwd;

    private String name;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    public void changePwd(String pwd) {
        this.pwd = pwd;
    }
}
