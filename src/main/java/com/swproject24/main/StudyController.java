package com.swproject24.main;

import com.swproject24.domain.Study;
import com.swproject24.service.StudyService;
import com.swproject24.domain.StudyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    // 더미데이터 생성용
    @GetMapping("/make-dummy")
    public ResponseEntity<List<Study>> makeDummyData(@RequestParam("count") int count) {
        List<Study> studies = studyService.makeDummyData(count);
        return ResponseEntity.ok(studies);
    }

    // 글 전체 불러오기
    @GetMapping
    public ResponseEntity<List<Study>> getAllActiveStudies() {
        List<Study> studies = studyService.getAllActiveStudies();
        return ResponseEntity.ok(studies);
    }

    // 글 상세페이지
    @GetMapping("/{studyId}")
    public ResponseEntity<Study> getStudyDetail(@PathVariable Long studyId) {
        return studyService.getStudyById(studyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 글 작성
    @PostMapping("/write")
    public ResponseEntity<Study> writeStudy(@RequestBody @Valid Study study) {
        Study createdStudy = studyService.createStudy(new StudyForm());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudy);
    }

    // 글 수정
    @PutMapping("/{studyId}/edit")
    public ResponseEntity<Study> editStudy(@PathVariable Long studyId, @RequestBody @Valid Study updatedStudy) {
        Study editedStudy = studyService.updateStudy(studyId, updatedStudy);
        return ResponseEntity.ok(editedStudy);
    }

    // 글 삭제 (soft-delete 방식)
    @PatchMapping("/{studyId}")
    public ResponseEntity<Study> partialDeleteStudy(@PathVariable Long studyId) {
        Study deleteStudy = studyService.partialDeleteStudy(studyId);
        return ResponseEntity.ok(deleteStudy);
    }

    // 페이징
    @GetMapping("/paginated")
    public ResponseEntity<List<Study>> getStudyPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<Study> studies = studyService.getStudyPage(page, size);
        return ResponseEntity.ok(studies);
    }

    // 글 검색 (제목으로 검색)
    @GetMapping(params = "title")
    public ResponseEntity<List<Study>> searchStudies(@RequestParam String title) {
        List<Study> studies = studyService.searchStudies(title);
        return ResponseEntity.ok(studies);
    }

    @Controller
    @RequestMapping("/studies")
    @RequiredArgsConstructor
    public static class StudyPageController {

        private final StudyService studyService;

        @GetMapping("/new-study")
        public String showCreateStudyForm(Model model) {
            model.addAttribute("studyForm", new StudyForm());
            return "create_study"; // create-study.html 파일과 매핑
        }

        @PostMapping
        public String createStudy(@Valid @ModelAttribute StudyForm studyForm, Errors errors) {
            if (errors.hasErrors()) {
                return "create_study";
            }
            studyService.createStudy(studyForm);
            return "redirect:/"; // 스터디 목록 페이지로 리디렉션
        }
    }
}