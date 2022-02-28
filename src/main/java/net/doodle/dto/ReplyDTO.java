package net.doodle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReplyDTO {

    private Long id;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private Long boardId;
    private Long memberId;
}
