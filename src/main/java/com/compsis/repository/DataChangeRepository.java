package com.compsis.repository;

import com.compsis.domain.DataChange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataChange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataChangeRepository extends JpaRepository<DataChange, Long> {

}
