package net.doodle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.dto.BoardDTO;
import net.doodle.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model) {
        log.info("index 화면");

        Page<BoardDTO> result = boardService.getList(0, 10);
        model.addAttribute("boardList",result.getContent());
        model.addAttribute("page","");
        return "board/index";
    }
}
