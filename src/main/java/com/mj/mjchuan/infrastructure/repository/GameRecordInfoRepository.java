package com.mj.mjchuan.infrastructure.repository;

import com.mj.mjchuan.domain.record.RecordInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xinruifan
 * @create 2025-01-07 17:40
 */
@Repository
public interface GameRecordInfoRepository extends JpaRepository<RecordInfo, Long> {

}
