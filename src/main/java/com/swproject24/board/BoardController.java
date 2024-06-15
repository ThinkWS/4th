package com.swproject24.board;

import com.swproject24.domain.Board;
import com.swproject24.domain.BoardForm;
import com.swproject24.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 더미 데이터 생성용
    @GetMapping("/make-dummy")
    @ResponseBody
    public ResponseEntity<List<Board>> makeDummyData(@RequestParam("count") int count) {
        if (count < 1 || count > 100) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
        List<Board> boards = boardService.makeDummyData(count);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 글 전체 불러오기
    @GetMapping("/all-posts")
    @ResponseBody
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 자유게시판 글 전체 불러오기 (delete-flag가 ACTIVE인 데이터만 조회)
    @GetMapping("/free")
    @ResponseBody
    public ResponseEntity<List<Board>> getAllActiveFreeBoards() {
        List<Board> boards = boardService.getAllActiveFreeBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // TIP게시판 글 전체 불러오기 (delete-flag가 ACTIVE인 데이터만 조회)
    @GetMapping("/tip")
    @ResponseBody
    public ResponseEntity<List<Board>> getAllActiveTipBoards() {
        List<Board> boards = boardService.getAllActiveTipBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 글 상세페이지
    @GetMapping("/{boardId}")
    @ResponseBody
    public ResponseEntity<Board> getBoardDetail(@PathVariable Long boardId) {
        Optional<Board> board = boardService.getBoardDetail(boardId);
        return board.map(
                value -> new ResponseEntity<>(value, HttpStatus.OK)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    // 글 작성 (REST API)
    @PostMapping("/api/write")
    @ResponseBody
    public ResponseEntity<Board> writeBoard(@RequestBody BoardForm boardForm) {
        Board newBoard = boardService.createBoard(boardForm);
        return new ResponseEntity<>(newBoard, HttpStatus.CREATED);
    }

    // 글 수정
    @PutMapping("/{boardId}/edit")
    @ResponseBody
    public ResponseEntity<Board> editBoard(@PathVariable Long boardId, @RequestBody BoardForm boardForm) {
        Board updatedBoard = boardService.updateBoard(boardId, boardForm);
        return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
    }

    // 글 삭제 (soft-delete 방식)
    @PatchMapping("/{boardId}")
    @ResponseBody
    public ResponseEntity<Board> partialDeleteBoard(@PathVariable Long boardId) {
        Board deleteBoard = boardService.partialDeleteBoard(boardId);
        return new ResponseEntity<>(deleteBoard, HttpStatus.OK);
    }

    // 자유게시판 글 페이징
    @GetMapping("/free/paginated")
    @ResponseBody
    public ResponseEntity<List<Board>> getFreeBoardPaginated(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        List<Board> boards = boardService.getFreeBoardPaginated(page, size);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // TIP게시판 글 페이징
    @GetMapping("/tip/paginated")
    @ResponseBody
    public ResponseEntity<List<Board>> getTipBoardPaginated(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        List<Board> boards = boardService.getTipBoardPaginated(page, size);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 자유게시판 글 검색 (제목으로 검색)
    @GetMapping(value = "/free", params = "title")
    @ResponseBody
    public ResponseEntity<List<Board>> searchFreeBoards(@RequestParam String title) {
        List<Board> boards = boardService.searchFreeBoards(title);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // TIP게시판 글 검색 (제목으로 검색)
    @GetMapping(value = "/tip", params = "title")
    @ResponseBody
    public ResponseEntity<List<Board>> searchTipBoards(@RequestParam String title) {
        List<Board> boards = boardService.searchTipBoards(title);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 페이지 렌더링 (자유게시판 목록 페이지)
    @GetMapping("/board")
    public String showBoardList(Model model) {
        List<Board> boards = boardService.getAllActiveFreeBoards();
        model.addAttribute("boards", boards);
        return "board_list"; // board_list.html 파일과 매핑
    }

    @GetMapping("/write")
    public String showCreateBoardForm(Model model) {
        model.addAttribute("boardForm", new BoardForm());
        return "create_board";  // create_board.html 파일과 매핑
    }

    // 글 작성 (페이지 렌더링)
    @PostMapping("/write")
    public String writeBoard(@Valid @ModelAttribute("boardForm") BoardForm boardForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("boardForm", boardForm);
            return "create_board";  // create_board.html 파일과 매핑
        }
        boardService.createBoard(boardForm);
        return "redirect:/boards/board";  // 글 작성 후 리디렉션
    }

    // /board 경로에 대한 리다이렉트 설정
    @GetMapping("/board-redirect")
    public String boardRedirect() {
        return "redirect:/boards/board";
    }
}