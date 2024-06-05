package com.swproject24.domain;

import com.swproject24.constant.BoardTypeEnum;
import com.swproject24.constant.DeleteFlagEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "board")
public class Board {
        @Id
        @Column(name = "board_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long boardId;  // 보드 게시글 식별자

        @Column(name = "title", nullable = false)
        private String title;   // 제목

        @Column(name = "field", nullable = false)
        private String field;   // 내용

        @Column(name = "create_date", nullable = false)
        private String createDate; // 게시일

        @Column(name = "update_date", nullable = false)
        private String updateDate; // 업데이트일

        @Column(name = "delete_date", nullable = true)
        private String deleteDate; // 삭제일

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "delete_flag", nullable = false)
        private DeleteFlagEnum deleteFlag; // 삭제여부

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "board_type", nullable = false)
        private BoardTypeEnum boardType; // 보드 타입

        @Column(name="user_id", nullable = false)
        private Long userId; // 사용자 식별자

        @Override
        public String toString() {
            return "Board{" +
                    "boardId=" + boardId +
                    ", title='" + title + '\'' +
                    ", field='" + field + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", updateDate='" + updateDate + '\'' +
                    ", deleteDate='" + deleteDate + '\'' +
                    ", deleteFlag=" + deleteFlag +
                    ", boardType=" + boardType +
                    ", userId=" + userId +
                    '}';
        }
    }



