package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.AccountOperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AccountOperation and its DTO AccountOperationDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialAccountMapper.class})
public interface AccountOperationMapper extends EntityMapper<AccountOperationDTO, AccountOperation> {

    @Mapping(source = "financialAccount.id", target = "financialAccountId")
    AccountOperationDTO toDto(AccountOperation accountOperation);

    @Mapping(source = "financialAccountId", target = "financialAccount")
    @Mapping(target = "idDataChanges", ignore = true)
    @Mapping(target = "idInformativeOperations", ignore = true)
    @Mapping(target = "idBalanceCalculations", ignore = true)
    @Mapping(target = "idOperators", ignore = true)
    @Mapping(target = "idAccountTransctions", ignore = true)
    @Mapping(target = "idAutomaticOperations", ignore = true)
    AccountOperation toEntity(AccountOperationDTO accountOperationDTO);

    default AccountOperation fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setId(id);
        return accountOperation;
    }
}
