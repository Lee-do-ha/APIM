package com.itda.memberservice.notice.repository;

import com.itda.memberservice.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeQueryRepository {
}
