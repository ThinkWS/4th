package com.swproject24.domain;


import com.swproject24.constant.StudyFeeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudyForm {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    @NotBlank(message = "내용을 입력하세요.")
    private String field;

    @NotBlank(message = "장소를 입력하세요.")
    private String location;

    @NotNull(message = "모집 인원을 입력하세요.")
    private Integer groupSize;

    @NotNull(message = "유/무료 여부를 선택하세요.")
    private StudyFeeEnum feeType;

    @NotBlank(message = "스터디 시작일을 입력하세요.")
    private String studyStartDate;

    @NotBlank(message = "스터디 종료일을 입력하세요.")
    private String studyEndDate;
}