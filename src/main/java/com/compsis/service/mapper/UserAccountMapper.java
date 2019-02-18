package com.compsis.service.mapper;

import com.compsis.domain.*;
import com.compsis.service.dto.UserAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAccount and its DTO UserAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialAccountMapper.class, PersonMapper.class})
public interface UserAccountMapper extends EntityMapper<UserAccountDTO, UserAccount> {

    @Mapping(source = "financialAccount.id", target = "financialAccountId")
    @Mapping(source = "accountable.id", target = "accountableId")
    @Mapping(source = "contacts.id", target = "contactsId")
    UserAccountDTO toDto(UserAccount userAccount);

    @Mapping(source = "financialAccountId", target = "financialAccount")
    @Mapping(source = "accountableId", target = "accountable")
    @Mapping(source = "contactsId", target = "contacts")
    UserAccount toEntity(UserAccountDTO userAccountDTO);

    default UserAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        return userAccount;
    }
}
