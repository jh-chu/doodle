package net.doodle.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"member","board"})
@Entity
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue
    private Long rno;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

}
