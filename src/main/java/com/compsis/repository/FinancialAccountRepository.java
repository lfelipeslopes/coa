package com.compsis.repository;

import com.compsis.domain.FinancialAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FinancialAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long> {

}
