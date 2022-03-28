package com.thirdlife.thirddonation.db.repository;

import com.thirdlife.thirddonation.db.entity.charity.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 자선단체 리포지토리입니다.
 */
@Transactional(readOnly = true)
public interface CharityRepository extends JpaRepository<Charity, String> {

}
