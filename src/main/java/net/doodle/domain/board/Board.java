package net.doodle.domain.board;

import lombok.*;
import net.doodle.domain.BaseEntity;
import net.doodle.domain.member.Member;
import net.doodle.domain.reply.Reply;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"member","replies"})
@Entity
public class Board extends BaseEntity {

    @Id @GeneratedValue
    private Long bno;

    @Column(nullable = false)
    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();

    public void changeTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
