package com.compsis.repository;

import com.compsis.domain.AccountOperation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountOperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

}
