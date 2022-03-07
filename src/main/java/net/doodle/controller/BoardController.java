package net.doodle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.dto.PageRequest;
import net.doodle.dto.PageResultDTO;
import net.doodle.dto.ReplyDTO;
import net.doodle.service.ReplyService;
import net.doodle.dto.BoardDTO;
import net.doodle.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping
    public String list(PageRequest pageRequest, Model model) {
        log.info("list 화면");

        Page<BoardDTO> result = boardService.getList(pageRequest.getPage()-1, pageRequest.getSize());

        model.addAttribute("result",new PageResultDTO(result));
        return "board/list";
    }

    @GetMapping("/{bno}")
    public String getBoard(@PathVariable("bno") Long bno, Model model) {

        BoardDTO boardDTO = boardService.get(bno);

        Page<ReplyDTO> replyList = replyService.getReplyList(0, 10, bno);

        model.addAttribute("board",boardDTO);
        model.addAttribute("reply",replyList);

        return "board/detail";
    }
}
