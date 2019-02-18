package com.compsis.repository;

import com.compsis.domain.InformativeOperation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InformativeOperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InformativeOperationRepository extends JpaRepository<InformativeOperation, Long> {

}
