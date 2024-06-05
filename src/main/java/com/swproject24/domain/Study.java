package com.swproject24.domain;

import com.swproject24.constant.DeleteFlagEnum;
import com.swproject24.constant.StudyFeeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


    @Entity
    @Getter
    @Setter
    @Table(name = "study")
    public class Study {
        @Id
        @Column(name = "study_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long studyId;  // 스터디 게시글 식별자

        @Column(name = "title", nullable = false)
        private String title;   // 제목

        @Column(name = "field", nullable = false)
        private String field;   // 내용

        @Column(name = "location", nullable = true)
        private String location;    // 장소

        @Column(name = "create_date", nullable = false)
        private String createDate; // 게시일

        @Column(name = "update_date", nullable = false)
        private String updateDate; // 업데이트일

        @Column(name = "delete_date", nullable = true)
        private String deleteDate; // 삭제일

        @Column(name = "group_size", nullable = false)
        private int groupSize;  // 모집인원

        @Column(name = "current_applicants", nullable = false)
        private int currentApplicants;  // 현재까지 신청자 수

        @Column(name = "study_rating", nullable = true)
        private Float studyRating; // 스터디 평점

        @Column(name = "fee_type", nullable = false)
        private StudyFeeEnum feeType; // 유/무료 여부

        @Column(name = "study_start_date", nullable = true)
        private String studyStartDate; // 스터디 시작일

        @Column(name = "study_end_date", nullable = true)
        private String studyEndDate; // 스터디 종료일

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "delete_flag", nullable = false)
        private DeleteFlagEnum deleteFlag; // 삭제여부

        @Column(name = "topic_id", nullable = false)
        private Long topicId; // 주제 식별자

        @Column(name="user_id", nullable = false)
        private Long userId; // 사용자 식별자

        @Override
        public String toString() {
            return "Study{" +
                    "studyId=" + studyId +
                    ", title='" + title + '\'' +
                    ", field='" + field + '\'' +
                    ", location='" + location + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", updateDate='" + updateDate + '\'' +
                    ", deleteDate='" + deleteDate + '\'' +
                    ", groupSize=" + groupSize +
                    ", currentApplicants=" + currentApplicants +
                    ", studyRating=" + studyRating +
                    ", feeType=" + feeType +
                    ", studyStartDate='" + studyStartDate + '\'' +
                    ", studyEndDate='" + studyEndDate + '\'' +
                    ", deleteFlag=" + deleteFlag +
                    ", topicId=" + topicId +
                    ", userId=" + userId +
                    '}';
        }

    }

