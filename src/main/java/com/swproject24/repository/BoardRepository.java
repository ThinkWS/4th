package com.swproject24.repository;

import com.swproject24.domain.Board;
import com.swproject24.constant.BoardTypeEnum;
import com.swproject24.constant.DeleteFlagEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByDeleteFlagAndBoardType(DeleteFlagEnum deleteFlagEnum, BoardTypeEnum boardTypeEnum);

    Page<Board> findByBoardType(BoardTypeEnum boardType, Pageable pageable);

    List<Board> findByTitleContainingAndBoardType(String title, BoardTypeEnum boardTypeEnum);
}