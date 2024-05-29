package com.swproject24.account;

import com.swproject24.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph; // JPA 엔티티 그래프를 사용하기 위한 import 문입니다.
import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository를 상속하기 위한 import 문입니다.
import org.springframework.data.querydsl.QuerydslPredicateExecutor; // QuerydslPredicateExecutor를 상속하기 위한 import 문입니다.
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관련 어노테이션을 사용하기 위한 import 문입니다.

@Transactional(readOnly = true) // 이 인터페이스의 모든 메소드는 읽기 전용으로 트랜잭션을 수행
public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {

    boolean existsByEmail(String email); // 주어진 이메일로 계정이 존재하는지 확인하는 메소드

    boolean existsByNickname(String nickname); // 주어진 닉네임으로 계정이 존재하는지 확인하는 메소드

    Account findByEmail(String email); // 주어진 이메일로 계정을 찾는 메소드

    Account findByNickname(String nickname); // 주어진 닉네임으로 계정을 찾는 메소드

    @EntityGraph(attributePaths = {"tags", "zones"}) // 지연 로딩을 위한 엔티티 그래프를 설정
    Account findAccountWithTagsAndZonesById(Long id); // 주어진 ID로 계정을 검색하고, 연관된 태그와 지역 정보를 함께 로딩하는 메소드