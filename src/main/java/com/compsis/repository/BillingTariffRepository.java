package com.compsis.repository;

import com.compsis.domain.BillingTariff;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BillingTariff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingTariffRepository extends JpaRepository<BillingTariff, Long> {

}
