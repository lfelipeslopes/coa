package com.compsis.repository;

import com.compsis.domain.VehicleClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VehicleClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleClassRepository extends JpaRepository<VehicleClass, Long> {

}
