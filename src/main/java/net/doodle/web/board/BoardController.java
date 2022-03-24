package net.doodle.web.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.doodle.web.login.LoginMember;
import net.doodle.web.dto.PageRequestDTO;
import net.doodle.web.dto.PageResultDTO;
import net.doodle.web.dto.ReplyDTO;
import net.doodle.domain.board.Board;
import net.doodle.domain.reply.Reply;
import net.doodle.domain.reply.ReplyService;
import net.doodle.web.dto.BoardDTO;
import net.doodle.domain.board.BoardService;
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
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping
    public String list(PageRequestDTO pageRequest, Model model) {

        Page<Board> result = boardService.getList(pageRequest.getPage()-1, pageRequest.getSize());

        model.addAttribute("result",result.map(BoardDTO::new));

        return "board/list";
    }

    @GetMapping("/{bno}")
    public String getBoard(@PathVariable("bno") Long bno, Model model) {

        Optional<Board> board = boardService.getBoard(bno);

        if(board.isEmpty()) {
            return "redirect:/board";
        }

        Optional<BoardDTO> dto = board.map(BoardDTO::new);
        model.addAttribute("board",dto.get());


        Page<Reply> replyList = replyService.getReplyList(0, 10, bno);
        Page<ReplyDTO> replyDTO = replyList.map(ReplyDTO::new);

        model.addAttribute("replies",replyDTO);

        return "board/detail";
    }

    @GetMapping("/add")
    public String getAdd(Model model) {
        model.addAttribute("board",new BoardSaveForm());
        return "board/addForm";
    }

    @PostMapping("/add")
    public String postAdd(@ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult, HttpSession session) {

        if(bindingResult.hasErrors()) {
            bindingResult.reject(null, "잘 못 입력 하였습니다.");
            return "board/add";
        }
        LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
        Long bno = boardService.create(loginMember.getId(), form.getTitle(), form.getContent());

        return "redirect:/board/"+bno;
    }

    @GetMapping("/{bno}/edit")
    public String getEdit(Model model) {
        model.addAttribute("board",new BoardUpdateForm());
        return "board/updateForm";
    }

    /**
     * Edit 항목 정하고, 구현하기
     */
    @PostMapping("/{bno}/edit")
    public String postEdit(@PathVariable Long bno, @ModelAttribute("board") BoardUpdateForm form, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            bindingResult.reject(null, "잘 못 입력하였습니다.");
            return "board/updateForm";
        }

        boardService.update(bno, form.getTitle(), form.getContent());

        return "redirect:/board/{bno}";
    }

    @DeleteMapping("/{bno}")
    @ResponseBody
    public String deleteBoard(@PathVariable Long bno) {

        boardService.delete(bno);

        return "ok";
    }
}
