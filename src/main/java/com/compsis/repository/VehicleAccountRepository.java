package com.compsis.repository;

import com.compsis.domain.VehicleAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VehicleAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleAccountRepository extends JpaRepository<VehicleAccount, Long> {

}
