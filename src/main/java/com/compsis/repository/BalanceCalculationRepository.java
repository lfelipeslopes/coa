package com.compsis.repository;

import com.compsis.domain.BalanceCalculation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BalanceCalculation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BalanceCalculationRepository extends JpaRepository<BalanceCalculation, Long> {

}
