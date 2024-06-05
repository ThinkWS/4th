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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRepository studyRepository;

    public List<Study> makeDummyData(int count) {
        List<Study> studyList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Study study = new Study();
            study.setTitle("테스트 게시글 " + i);
            study.setField("안녕하세요");
            study.setLocation("");
            study.setCreateDate(String.valueOf(LocalDateTime.now()));
            study.setUpdateDate(String.valueOf(LocalDateTime.now()));
            study.setDeleteDate(null);
            study.setGroupSize((int) (Math.random() * 50));
            study.setCurrentApplicants((int) (Math.random() * 50));
            study.setStudyRating((float) Math.random() * 5);
            study.setFeeType(Math.random() < 0.5 ? StudyFeeEnum.FREE : StudyFeeEnum.PAID);
            study.setStudyStartDate(null);
            study.setStudyEndDate(null);
            study.setTopicId((long) i);
            study.setUserId((long) i);
            study.setDeleteFlag(Math.random() < 0.5 ? DeleteFlagEnum.ACTIVE : DeleteFlagEnum.DELETED);
            studyList.add(study);
        }
        return studyRepository.saveAll(studyList);
    }

    public List<Study> getAllStudies() {
        return studyRepository.findAll();
    }

    public List<Study> getAllActiveStudies() {
        return studyRepository.findByDeleteFlagEquals(DeleteFlagEnum.ACTIVE);
    }

    public Optional<Study> getStudyById(Long studyId) {
        return studyRepository.findById(studyId);
    }

    @Transactional
    public Study createStudy(StudyForm studyForm) {
        Study study = new Study();
        study.setTitle(studyForm.getTitle());
        study.setField(studyForm.getField());
        study.setLocation(studyForm.getLocation());
        study.setCreateDate(String.valueOf(LocalDateTime.now()));
        study.setUpdateDate(String.valueOf(LocalDateTime.now()));
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
        Study existingStudy = studyRepository.findById(studyId).orElse(null);
        if (existingStudy == null) {
            return null;
        }
        existingStudy.setTitle(updatedStudy.getTitle());
        existingStudy.setField(updatedStudy.getField());
        existingStudy.setLocation(updatedStudy.getLocation());
        existingStudy.setCreateDate(updatedStudy.getCreateDate());
        existingStudy.setUpdateDate(String.valueOf(LocalDateTime.now()));
        existingStudy.setDeleteDate(updatedStudy.getDeleteDate());
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

    public Study partialDeleteStudy(Long studyId) {
        Study existingStudy = studyRepository.findById(studyId).orElse(null);
        if (existingStudy == null) {
            return null;
        }
        existingStudy.setDeleteFlag(DeleteFlagEnum.DELETED);
        existingStudy.setDeleteDate(String.valueOf(LocalDateTime.now()));
        return studyRepository.save(existingStudy);
    }

    public List<Study> getStudyPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Study> studyPage = studyRepository.findAll(pageable);
        return studyPage.getContent();
    }

    public List<Study> searchStudies(String title) {
        return studyRepository.findByTitleContainingIgnoreCase(title);
    }
}
