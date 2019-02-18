package com.compsis.repository;

import com.compsis.domain.AccountTransction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AccountTransction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountTransctionRepository extends JpaRepository<AccountTransction, Long> {

}
