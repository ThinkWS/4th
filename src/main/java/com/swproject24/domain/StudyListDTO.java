package com.swproject24.domain;

import lombok.Getter;


    @Getter
    public class StudyListDTO {
        private Long userId;
        private String title;
        private String field;   // 내용

        public StudyListDTO(Study study) {
            this.userId = study.getUserId();
            this.title = study.getTitle();
            this.field = study.getField();
        }

        public static StudyListDTO formEntity(Study study) {
            return new StudyListDTO(study);
        }

        public Study toEntity() {
            Study study = new Study();
            study.setUserId(this.userId);
            study.setTitle(this.title);
            study.setField(this.field);
            return study;
        }
    }


