package com.swproject24.service;

import com.swproject24.constant.BoardTypeEnum;
import com.swproject24.constant.DeleteFlagEnum;
import com.swproject24.domain.Board;
import com.swproject24.domain.BoardForm;
import com.swproject24.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> makeDummyData(int count) { // 메서드 이름 수정
        List<Board> boardList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Board board = new Board();
            board.setTitle("테스트 게시글 " + i);
            board.setField("안녕하세요");
            board.setCreateDate(String.valueOf(LocalDateTime.now()));
            board.setUpdateDate(String.valueOf(LocalDateTime.now()));
            board.setDeleteDate(null);
            board.setDeleteFlag(Math.random() < 0.5 ? DeleteFlagEnum.ACTIVE : DeleteFlagEnum.DELETED);
            board.setBoardType(Math.random() < 0.5 ? BoardTypeEnum.FREE : BoardTypeEnum.TIP);
            board.setUserId((long) i);
            boardList.add(board);
        }
        return boardRepository.saveAll(boardList);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public List<Board> getAllActiveFreeBoards() {
        return boardRepository.findByDeleteFlagAndBoardType(DeleteFlagEnum.ACTIVE, BoardTypeEnum.FREE);
    }

    public List<Board> getAllActiveTipBoards() {
        return boardRepository.findByDeleteFlagAndBoardType(DeleteFlagEnum.ACTIVE, BoardTypeEnum.TIP);
    }

    public Optional<Board> getBoardDetail(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public Board createBoard(BoardForm boardForm) {
        Board board = new Board();
        board.setTitle(boardForm.getTitle());
        board.setField(boardForm.getContent()); // 'field' 대신 'content'로 수정
        board.setCreateDate(String.valueOf(LocalDateTime.now()));
        board.setUpdateDate(String.valueOf(LocalDateTime.now()));
        board.setDeleteFlag(DeleteFlagEnum.ACTIVE); // 기본값 설정
        board.setBoardType(boardForm.getCategory()); // 폼 데이터에서 값 설정
        board.setUserId(boardForm.getUserId()); // 폼 데이터에서 사용자 ID 설정
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(Long boardId, BoardForm boardForm) { // 파라미터 타입 수정
        Board existingBoard = boardRepository.findById(boardId).orElse(null);
        if (existingBoard == null) {
            return null;
        }
        existingBoard.setTitle(boardForm.getTitle());
        existingBoard.setField(boardForm.getContent());
        existingBoard.setUpdateDate(String.valueOf(LocalDateTime.now()));
        existingBoard.setBoardType(boardForm.getCategory());
        return boardRepository.save(existingBoard);
    }

    public Board partialDeleteBoard(Long boardId) {
        Board existingBoard = boardRepository.findById(boardId).orElse(null);
        if (existingBoard == null) {
            return null;
        }
        existingBoard.setDeleteFlag(DeleteFlagEnum.DELETED);
        existingBoard.setDeleteDate(String.valueOf(LocalDateTime.now()));

        return boardRepository.save(existingBoard);
    }

    public List<Board> getFreeBoardPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.findByBoardType(BoardTypeEnum.FREE, pageable);
        return boardPage.getContent();
    }

    public List<Board> getTipBoardPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardRepository.findByBoardType(BoardTypeEnum.TIP, pageable);
        return boardPage.getContent();
    }

    public List<Board> searchFreeBoards(String title) {
        return boardRepository.findByTitleContainingAndBoardType(title, BoardTypeEnum.FREE);
    }

    public List<Board> searchTipBoards(String title) {
        return boardRepository.findByTitleContainingAndBoardType(title, BoardTypeEnum.TIP);
    }
}