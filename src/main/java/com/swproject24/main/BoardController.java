package com.swproject24.main;

import com.swproject24.domain.Board;
import com.swproject24.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


    @RestController
    @RequestMapping("/boards")
    public class BoardController {

        private final BoardService boardService;

        @Autowired
        public BoardController(BoardService boardService) {
            this.boardService = boardService;
        }

        // 더미데이터 생성용
        @GetMapping("/make-dummy")
        public ResponseEntity<List<Board>> makeDummyData(@RequestParam("count") int count) {
            if (count < 1 || count > 100) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
            List<Board> boards = boardService.makeDummyDate(count);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }

        // 글 전체 불러오기
        @GetMapping("/all-posts")
        public ResponseEntity<List<Board>> getAllBoards() {
            List<Board> boards = boardService.getAllBoards();
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }

        // 자유게시판 글 전체 불러오기
        // delete-flag 가 ACTIVE 인 데이터만 조회
        @GetMapping("/free")
        public ResponseEntity<List<Board>> getAllActiveFreeBoards() {
            List<Board> boards = boardService.getAllActiveFreeBoards();
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }

        // tip게시판 글 전체 불러오기
        // delete-flag 가 ACTIVE 인 데이터만 조회
        @GetMapping("/tip")
        public ResponseEntity<List<Board>> getAllActiveTipBoards() {
            List<Board> boards = boardService.getAllActiveTipBoards();
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }

        // 글 상세페이지
        @GetMapping("/{boardId}")
        public ResponseEntity<Board> getBoardDetail(@PathVariable Long boardId) {
            Optional<Board> board = boardService.getBoardDetail(boardId);
            return board.map(
                    value -> new ResponseEntity<>(value, HttpStatus.OK)
            ).orElseGet(
                    () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
            );
        }

        // 글 작성
        @PostMapping("/write")
        public ResponseEntity<Board> writeBoard(@RequestBody Board board) {
            Board newBoard = boardService.createBoard(board);
            return new ResponseEntity<>(newBoard, HttpStatus.CREATED);
        }

        // 글 수정
        @PutMapping("/{boardId}/edit")
        public ResponseEntity<Board> editBoard(@PathVariable Long boardId, @RequestBody Board updatedboard) {
            Board updatedBoard = boardService.updateBoard(boardId, updatedboard);
            return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
        }

        // 글 삭제 (soft-delete 방식)
        @PatchMapping("/{boardId}")
        public ResponseEntity<Board> partialDeleteBoard(@PathVariable Long boardId) {
            Board deleteBoard = boardService.partialDeleteBoard(boardId);
            return new ResponseEntity<>(deleteBoard, HttpStatus.OK);
        }

        // 자유게시판 글페이징
        @GetMapping("/free/paginated")
        public ResponseEntity<List<Board>> getFreeBoardPaginated(
                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                @RequestParam(value = "size", required = false, defaultValue = "10") int size)
        {
            List<Board> boards = boardService.getFreeBoardPaginated(page, size);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }

        // TIP게시판 글페이징
        @GetMapping("/tip/paginated")
        public ResponseEntity<List<Board>> getTipBoardPaginated(
                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                @RequestParam(value = "size", required = false, defaultValue = "10") int size)
        {
            List<Board> boards = boardService.getTipBoardPaginated(page, size);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }

        // 자유게시판 글 검색 (제목으로 검색)
        @GetMapping(value = "/free", params = "title")
        public ResponseEntity<List<Board>> searchFreeBoards(@RequestParam String title) {
            List<Board> boards = boardService.searchFreeBoards(title);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }

        // TIP게시판 글 검색 (제목으로 검색)
        @GetMapping(value = "/tip", params = "title")
        public ResponseEntity<List<Board>> searchTipBoards(@RequestParam String title) {
            List<Board> boards = boardService.searchTipBoards(title);
            return new ResponseEntity<>(boards, HttpStatus.OK);
        }

    }


