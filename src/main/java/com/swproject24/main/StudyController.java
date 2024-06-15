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

@Controller
@RequestMapping("/studies")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    // 더미데이터 생성용 (REST API)
    @GetMapping("/api/make-dummy")
    @ResponseBody
    public ResponseEntity<List<Study>> makeDummyData(@RequestParam("count") int count) {
        List<Study> studies = studyService.makeDummyData(count);
        return ResponseEntity.ok(studies);
    }

    // 글 전체 불러오기 (REST API)
    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<Study>> getAllActiveStudies() {
        List<Study> studies = studyService.getAllActiveStudies();
        return ResponseEntity.ok(studies);
    }

    // 글 상세페이지 (REST API)
    @GetMapping("/api/{studyId}")
    @ResponseBody
    public ResponseEntity<Study> getStudyDetail(@PathVariable Long studyId) {
        return studyService.getStudyById(studyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 글 작성 (REST API)
    @PostMapping("/api/write")
    @ResponseBody
    public ResponseEntity<Study> writeStudy(@RequestBody @Valid StudyForm studyForm) {
        Study createdStudy = studyService.createStudy(studyForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudy);
    }

    // 글 수정 (REST API)
    @PutMapping("/api/{studyId}/edit")
    @ResponseBody
    public ResponseEntity<Study> editStudy(@PathVariable Long studyId, @RequestBody @Valid Study updatedStudy) {
        Study editedStudy = studyService.updateStudy(studyId, updatedStudy);
        return ResponseEntity.ok(editedStudy);
    }

    // 글 삭제 (soft-delete 방식) (REST API)
    @PatchMapping("/api/{studyId}")
    @ResponseBody
    public ResponseEntity<Study> partialDeleteStudy(@PathVariable Long studyId) {
        Study deleteStudy = studyService.partialDeleteStudy(studyId);
        return ResponseEntity.ok(deleteStudy);
    }

    // 페이징 (REST API)
    @GetMapping("/api/paginated")
    @ResponseBody
    public ResponseEntity<List<Study>> getStudyPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<Study> studies = studyService.getStudyPage(page, size);
        return ResponseEntity.ok(studies);
    }

    // 글 검색 (제목으로 검색) (REST API)
    @GetMapping(value = "/api", params = "title")
    @ResponseBody
    public ResponseEntity<List<Study>> searchStudies(@RequestParam String title) {
        List<Study> studies = studyService.searchStudies(title);
        return ResponseEntity.ok(studies);
    }

    // 페이지 렌더링
    @GetMapping("/new-study")
    public String showCreateStudyForm(Model model) {
        model.addAttribute("studyForm", new StudyForm());
        return "create_study"; // create_study.html 파일과 매핑
    }

    // 추가된 경로 매핑: /study/write
    @GetMapping("/write")
    public String showCreateStudyFormForWrite(Model model) {
        model.addAttribute("studyForm", new StudyForm());
        return "create_study"; // create_study.html 파일과 매핑
    }

    @GetMapping
    public String showStudyList(@RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                Model model) {
        List<Study> studies;
        long totalStudies;
        if (title != null) {
            studies = studyService.searchStudies(title);
            totalStudies = studyService.countStudiesByTitle(title);
        } else {
            studies = studyService.getStudyPage(page, size);
            totalStudies = studyService.countAllStudies();
        }
        model.addAttribute("studies", studies);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalStudies / size));
        return "study_list"; // study_list.html 파일과 매핑
    }

    @PostMapping("/write")
    public String createStudy(@Valid @ModelAttribute StudyForm studyForm, Errors errors) {
        if (errors.hasErrors()) {
            return "create_study";
        }
        studyService.createStudy(studyForm);
        return "redirect:/studies"; // 스터디 목록 페이지로 리디렉션
    }

    // 특정 URL에 대한 매핑 추가
    @GetMapping("/list")
    public String showStudyListPage(Model model) {
        List<Study> studies = studyService.getAllActiveStudies();
        model.addAttribute("studies", studies);
        return "study_list"; // study_list.html 파일과 매핑
    }
}