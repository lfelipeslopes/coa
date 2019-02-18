package com.compsis.repository;

import com.compsis.domain.Passage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Passage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PassageRepository extends JpaRepository<Passage, Long> {

}
