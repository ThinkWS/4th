package com.swproject24.service;

import com.swproject24.constant.DeleteFlagEnum;
import com.swproject24.constant.StudyFeeEnum;
import com.swproject24.domain.Study;
import com.swproject24.repository.StudyRepository;
import com.swproject24.domain.StudyForm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    public List<Study> makeDummyData(int count) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 1; i <= count; i++) {
            Study study = new Study();
            study.setTitle("테스트 게시글 " + i);
            study.setField("안녕하세요");
            study.setLocation("");
            study.setCreateDate(LocalDateTime.now().format(formatter));
            study.setUpdateDate(LocalDateTime.now().format(formatter));
            study.setDeleteDate(null);
            study.setGroupSize((int) (Math.random() * 50));
            study.setCurrentApplicants((int) (Math.random() * 50));
            study.setStudyRating((float) Math.random() * 5);
            study.setFeeType(Math.random() < 0.5 ? StudyFeeEnum.FREE : StudyFeeEnum.PAID);
            study.setStudyStartDate(null);
            study.setStudyEndDate(null);
            study.setTopicId((long) i);
            study.setUserId((long) i);
            study.setDeleteFlag(DeleteFlagEnum.ACTIVE);
            studyRepository.save(study);
        }
        return studyRepository.findAll();
    }

    public List<Study> getAllActiveStudies() {
        return studyRepository.findByDeleteFlag(DeleteFlagEnum.ACTIVE);
    }

    public Optional<Study> getStudyById(Long studyId) {
        return studyRepository.findById(studyId);
    }

    @Transactional
    public Study createStudy(StudyForm studyForm) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Study study = new Study();
        study.setTitle(studyForm.getTitle());
        study.setField(studyForm.getField());
        study.setLocation(studyForm.getLocation());
        study.setCreateDate(LocalDateTime.now().format(formatter));
        study.setUpdateDate(LocalDateTime.now().format(formatter));
        study.setDeleteDate(null);
        study.setGroupSize(studyForm.getGroupSize());
        study.setCurrentApplicants(0);
        study.setStudyRating(0.0f);
        study.setFeeType(studyForm.getFeeType());
        study.setStudyStartDate(studyForm.getStudyStartDate());
        study.setStudyEndDate(studyForm.getStudyEndDate());
        study.setDeleteFlag(DeleteFlagEnum.ACTIVE);
        study.setTopicId(1L); // 임시 값
        study.setUserId(1L); // 임시 값

        return studyRepository.save(study);
    }

    @Transactional
    public Study updateStudy(Long studyId, Study updatedStudy) {
        Optional<Study> existingStudyOptional = studyRepository.findById(studyId);
        if (existingStudyOptional.isEmpty()) {
            return null;
        }

        Study existingStudy = existingStudyOptional.get();
        existingStudy.setTitle(updatedStudy.getTitle());
        existingStudy.setField(updatedStudy.getField());
        existingStudy.setLocation(updatedStudy.getLocation());
        existingStudy.setUpdateDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        existingStudy.setGroupSize(updatedStudy.getGroupSize());
        existingStudy.setCurrentApplicants(updatedStudy.getCurrentApplicants());
        existingStudy.setStudyRating(updatedStudy.getStudyRating());
        existingStudy.setFeeType(updatedStudy.getFeeType());
        existingStudy.setStudyStartDate(updatedStudy.getStudyStartDate());
        existingStudy.setStudyEndDate(updatedStudy.getStudyEndDate());
        existingStudy.setDeleteFlag(updatedStudy.getDeleteFlag());
        existingStudy.setTopicId(updatedStudy.getTopicId());
        existingStudy.setUserId(updatedStudy.getUserId());
        return studyRepository.save(existingStudy);
    }

    public void hardDeleteStudy(Long studyId) {
        if (studyRepository.existsById(studyId)) {
            studyRepository.deleteById(studyId);
        }
    }

    @Transactional
    public Study partialDeleteStudy(Long studyId) {
        Optional<Study> existingStudyOptional = studyRepository.findById(studyId);
        if (existingStudyOptional.isEmpty()) {
            return null;
        }

        Study existingStudy = existingStudyOptional.get();
        existingStudy.setDeleteFlag(DeleteFlagEnum.DELETED);
        existingStudy.setDeleteDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return studyRepository.save(existingStudy);
    }

    public List<Study> getStudyPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Study> studyPage = studyRepository.findAll(pageable);
        return studyPage.getContent();
    }

    public int getTotalPages(int size) {
        long totalStudies = studyRepository.count();
        return (int) Math.ceil((double) totalStudies / size);
    }

    public List<Study> searchStudies(String title) {
        return studyRepository.findByTitleContainingIgnoreCase(title);
    }

    public long countAllStudies() {
        return studyRepository.countByDeleteFlag(DeleteFlagEnum.ACTIVE);
    }

    public long countStudiesByTitle(String title) {
        return studyRepository.countByTitleContainingIgnoreCaseAndDeleteFlag(title, DeleteFlagEnum.ACTIVE);
    }
}