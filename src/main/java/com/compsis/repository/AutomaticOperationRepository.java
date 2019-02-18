package com.compsis.repository;

import com.compsis.domain.AutomaticOperation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AutomaticOperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutomaticOperationRepository extends JpaRepository<AutomaticOperation, Long> {

}
