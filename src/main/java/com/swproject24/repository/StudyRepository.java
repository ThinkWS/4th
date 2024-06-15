package com.swproject24.repository;

import com.swproject24.constant.DeleteFlagEnum;
import com.swproject24.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


    @Repository
    public interface StudyRepository extends JpaRepository<Study, Long> {
        // 구현부를 작성하지 않아도 JPA 에서 약속된 이름의 메서드를 선언하면 사용가능
        List<Study> findByDeleteFlag(DeleteFlagEnum deleteFlag);

        List<Study> findByTitleContainingIgnoreCase(String title);
        long countByDeleteFlag(DeleteFlagEnum deleteFlag);
        long countByTitleContainingIgnoreCaseAndDeleteFlag(String title, DeleteFlagEnum deleteFlag);

    }



