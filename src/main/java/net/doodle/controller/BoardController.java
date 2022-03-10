package net.doodle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.controller.form.BoardSaveForm;
import net.doodle.controller.form.BoardUpdateForm;
import net.doodle.controller.form.LoginMember;
import net.doodle.dto.PageRequestDTO;
import net.doodle.dto.PageResultDTO;
import net.doodle.dto.ReplyDTO;
import net.doodle.entity.Board;
import net.doodle.entity.Reply;
import net.doodle.service.ReplyService;
import net.doodle.dto.BoardDTO;
import net.doodle.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/community")
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping
    public String list(PageRequestDTO pageRequest, Model model) {
        log.info("list 화면");

        Page<Board> result = boardService.getList(pageRequest.getPage()-1, pageRequest.getSize());

        model.addAttribute("result",new PageResultDTO(result.map(BoardDTO::new)));

        return "board/list";
    }

    @GetMapping("/{bno}")
    public String getBoard(@PathVariable("bno") Long bno, Model model) {

        Optional<Board> board = boardService.getBoard(bno);
        if(board.isPresent()) {
            Optional<BoardDTO> dto = board.map(BoardDTO::new);
            model.addAttribute("board",dto.get());
        }

        Page<Reply> replyList = replyService.getReplyList(0, 10, bno);
        Page<ReplyDTO> replyDTO = replyList.map(ReplyDTO::new);

        model.addAttribute("replies",replyDTO);

        return "board/detail";
    }

    @GetMapping("/add")
    public String getAdd(Model model) {
        model.addAttribute("board",new BoardSaveForm());
        return "board/add";
    }

    @PostMapping("/add")
    public String postAdd(@ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult, HttpSession session) {

        if(bindingResult.hasErrors()) {
            bindingResult.reject(null, "잘 못 입력 하였습니다.");
            return "board/add";
        }
        LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
        Long bno = boardService.create(loginMember.getId(), form.getTitle(), form.getContent());

        return "redirect:/community/"+bno;
    }

    @GetMapping("/{bno}/edit")
    public String getEdit(Model model) {
        model.addAttribute("board",new BoardUpdateForm());
        return "board/update";
    }

    /**
     * Edit 항목 정하고, 구현하기
     */
    @PostMapping("/{bno}/edit")
    public String postEdit(@PathVariable Long bno, @ModelAttribute("board") BoardUpdateForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            bindingResult.reject(null, "잘 못 입력하였습니다.");
            return "board/update";
        }

        boardService.update(bno, form.getTitle(), form.getContent());

        return "redirect:/community/{bno}";
    }

    @DeleteMapping("/{bno}")
    @ResponseBody
    public String deleteBoard(@PathVariable Long bno) {

        boardService.delete(bno);

        return "ok";
    }
}
