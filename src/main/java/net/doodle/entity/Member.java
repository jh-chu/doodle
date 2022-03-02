package net.doodle.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"boardList"})
@Entity
public class Member extends BaseEntity{

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
