package com.swproject24.domain;

import com.swproject24.constant.BoardTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    @NotEmpty(message = "제목을 입력하세요.")
    private String title;

    @NotEmpty(message = "내용을 입력하세요.")
    private String content;

    @NotNull(message = "주제를 선택하세요.")
    private BoardTypeEnum category;

    @NotNull(message = "사용자 ID를 입력하세요.")
    private Long userId;
}