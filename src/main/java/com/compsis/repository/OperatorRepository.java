package com.compsis.repository;

import com.compsis.domain.Operator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Operator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

}
