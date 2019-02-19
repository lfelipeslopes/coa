package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.AccountTransctionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AccountTransction and its DTO AccountTransctionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AccountTransctionMapper extends EntityMapper<AccountTransctionDTO, AccountTransction> {



    default AccountTransction fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountTransction accountTransction = new AccountTransction();
        accountTransction.setId(id);
        return accountTransction;
    }
}
