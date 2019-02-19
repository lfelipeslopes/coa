import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'financial-account',
                loadChildren: './financial-account/financial-account.module#CoaFinancialAccountModule'
            },
            {
                path: 'person',
                loadChildren: './person/person.module#CoaPersonModule'
            },
            {
                path: 'user-account',
                loadChildren: './user-account/user-account.module#CoaUserAccountModule'
            },
            {
                path: 'vehicle-account',
                loadChildren: './vehicle-account/vehicle-account.module#CoaVehicleAccountModule'
            },
            {
                path: 'vehicle',
                loadChildren: './vehicle/vehicle.module#CoaVehicleModule'
            },
            {
                path: 'media',
                loadChildren: './media/media.module#CoaMediaModule'
            },
            {
                path: 'vehicle-class',
                loadChildren: './vehicle-class/vehicle-class.module#CoaVehicleClassModule'
            },
            {
                path: 'billing-tariff',
                loadChildren: './billing-tariff/billing-tariff.module#CoaBillingTariffModule'
            },
            {
                path: 'billing-location',
                loadChildren: './billing-location/billing-location.module#CoaBillingLocationModule'
            },
            {
                path: 'passage',
                loadChildren: './passage/passage.module#CoaPassageModule'
            },
            {
                path: 'account-operation',
                loadChildren: './account-operation/account-operation.module#CoaAccountOperationModule'
            },
            {
                path: 'data-change',
                loadChildren: './data-change/data-change.module#CoaDataChangeModule'
            },
            {
                path: 'informative-operation',
                loadChildren: './informative-operation/informative-operation.module#CoaInformativeOperationModule'
            },
            {
                path: 'balance-calculation',
                loadChildren: './balance-calculation/balance-calculation.module#CoaBalanceCalculationModule'
            },
            {
                path: 'operator',
                loadChildren: './operator/operator.module#CoaOperatorModule'
            },
            {
                path: 'account-transction',
                loadChildren: './account-transction/account-transction.module#CoaAccountTransctionModule'
            },
            {
                path: 'automatic-operation',
                loadChildren: './automatic-operation/automatic-operation.module#CoaAutomaticOperationModule'
            },
            {
                path: 'financial-account',
                loadChildren: './financial-account/financial-account.module#CoaFinancialAccountModule'
            },
            {
                path: 'person',
                loadChildren: './person/person.module#CoaPersonModule'
            },
            {
                path: 'user-account',
                loadChildren: './user-account/user-account.module#CoaUserAccountModule'
            },
            {
                path: 'vehicle-account',
                loadChildren: './vehicle-account/vehicle-account.module#CoaVehicleAccountModule'
            },
            {
                path: 'vehicle',
                loadChildren: './vehicle/vehicle.module#CoaVehicleModule'
            },
            {
                path: 'media',
                loadChildren: './media/media.module#CoaMediaModule'
            },
            {
                path: 'vehicle-class',
                loadChildren: './vehicle-class/vehicle-class.module#CoaVehicleClassModule'
            },
            {
                path: 'billing-tariff',
                loadChildren: './billing-tariff/billing-tariff.module#CoaBillingTariffModule'
            },
            {
                path: 'billing-location',
                loadChildren: './billing-location/billing-location.module#CoaBillingLocationModule'
            },
            {
                path: 'passage',
                loadChildren: './passage/passage.module#CoaPassageModule'
            },
            {
                path: 'account-operation',
                loadChildren: './account-operation/account-operation.module#CoaAccountOperationModule'
            },
            {
                path: 'data-change',
                loadChildren: './data-change/data-change.module#CoaDataChangeModule'
            },
            {
                path: 'informative-operation',
                loadChildren: './informative-operation/informative-operation.module#CoaInformativeOperationModule'
            },
            {
                path: 'balance-calculation',
                loadChildren: './balance-calculation/balance-calculation.module#CoaBalanceCalculationModule'
            },
            {
                path: 'operator',
                loadChildren: './operator/operator.module#CoaOperatorModule'
            },
            {
                path: 'account-transction',
                loadChildren: './account-transction/account-transction.module#CoaAccountTransctionModule'
            },
            {
                path: 'automatic-operation',
                loadChildren: './automatic-operation/automatic-operation.module#CoaAutomaticOperationModule'
            },
            {
                path: 'financial-account',
                loadChildren: './financial-account/financial-account.module#CoaFinancialAccountModule'
            },
            {
                path: 'person',
                loadChildren: './person/person.module#CoaPersonModule'
            },
            {
                path: 'user-account',
                loadChildren: './user-account/user-account.module#CoaUserAccountModule'
            },
            {
                path: 'vehicle-account',
                loadChildren: './vehicle-account/vehicle-account.module#CoaVehicleAccountModule'
            },
            {
                path: 'vehicle',
                loadChildren: './vehicle/vehicle.module#CoaVehicleModule'
            },
            {
                path: 'media',
                loadChildren: './media/media.module#CoaMediaModule'
            },
            {
                path: 'vehicle-class',
                loadChildren: './vehicle-class/vehicle-class.module#CoaVehicleClassModule'
            },
            {
                path: 'billing-tariff',
                loadChildren: './billing-tariff/billing-tariff.module#CoaBillingTariffModule'
            },
            {
                path: 'billing-location',
                loadChildren: './billing-location/billing-location.module#CoaBillingLocationModule'
            },
            {
                path: 'passage',
                loadChildren: './passage/passage.module#CoaPassageModule'
            },
            {
                path: 'account-operation',
                loadChildren: './account-operation/account-operation.module#CoaAccountOperationModule'
            },
            {
                path: 'data-change',
                loadChildren: './data-change/data-change.module#CoaDataChangeModule'
            },
            {
                path: 'informative-operation',
                loadChildren: './informative-operation/informative-operation.module#CoaInformativeOperationModule'
            },
            {
                path: 'balance-calculation',
                loadChildren: './balance-calculation/balance-calculation.module#CoaBalanceCalculationModule'
            },
            {
                path: 'operator',
                loadChildren: './operator/operator.module#CoaOperatorModule'
            },
            {
                path: 'account-transction',
                loadChildren: './account-transction/account-transction.module#CoaAccountTransctionModule'
            },
            {
                path: 'automatic-operation',
                loadChildren: './automatic-operation/automatic-operation.module#CoaAutomaticOperationModule'
            },
            {
                path: 'financial-account',
                loadChildren: './financial-account/financial-account.module#CoaFinancialAccountModule'
            },
            {
                path: 'person',
                loadChildren: './person/person.module#CoaPersonModule'
            },
            {
                path: 'user-account',
                loadChildren: './user-account/user-account.module#CoaUserAccountModule'
            },
            {
                path: 'vehicle-account',
                loadChildren: './vehicle-account/vehicle-account.module#CoaVehicleAccountModule'
            },
            {
                path: 'vehicle',
                loadChildren: './vehicle/vehicle.module#CoaVehicleModule'
            },
            {
                path: 'media',
                loadChildren: './media/media.module#CoaMediaModule'
            },
            {
                path: 'vehicle-class',
                loadChildren: './vehicle-class/vehicle-class.module#CoaVehicleClassModule'
            },
            {
                path: 'billing-tariff',
                loadChildren: './billing-tariff/billing-tariff.module#CoaBillingTariffModule'
            },
            {
                path: 'billing-location',
                loadChildren: './billing-location/billing-location.module#CoaBillingLocationModule'
            },
            {
                path: 'passage',
                loadChildren: './passage/passage.module#CoaPassageModule'
            },
            {
                path: 'account-operation',
                loadChildren: './account-operation/account-operation.module#CoaAccountOperationModule'
            },
            {
                path: 'data-change',
                loadChildren: './data-change/data-change.module#CoaDataChangeModule'
            },
            {
                path: 'informative-operation',
                loadChildren: './informative-operation/informative-operation.module#CoaInformativeOperationModule'
            },
            {
                path: 'balance-calculation',
                loadChildren: './balance-calculation/balance-calculation.module#CoaBalanceCalculationModule'
            },
            {
                path: 'operator',
                loadChildren: './operator/operator.module#CoaOperatorModule'
            },
            {
                path: 'account-transction',
                loadChildren: './account-transction/account-transction.module#CoaAccountTransctionModule'
            },
            {
                path: 'automatic-operation',
                loadChildren: './automatic-operation/automatic-operation.module#CoaAutomaticOperationModule'
            },
            {
                path: 'financial-account',
                loadChildren: './financial-account/financial-account.module#CoaFinancialAccountModule'
            },
            {
                path: 'person',
                loadChildren: './person/person.module#CoaPersonModule'
            },
            {
                path: 'user-account',
                loadChildren: './user-account/user-account.module#CoaUserAccountModule'
            },
            {
                path: 'vehicle-account',
                loadChildren: './vehicle-account/vehicle-account.module#CoaVehicleAccountModule'
            },
            {
                path: 'vehicle',
                loadChildren: './vehicle/vehicle.module#CoaVehicleModule'
            },
            {
                path: 'media',
                loadChildren: './media/media.module#CoaMediaModule'
            },
            {
                path: 'vehicle-class',
                loadChildren: './vehicle-class/vehicle-class.module#CoaVehicleClassModule'
            },
            {
                path: 'billing-tariff',
                loadChildren: './billing-tariff/billing-tariff.module#CoaBillingTariffModule'
            },
            {
                path: 'billing-location',
                loadChildren: './billing-location/billing-location.module#CoaBillingLocationModule'
            },
            {
                path: 'passage',
                loadChildren: './passage/passage.module#CoaPassageModule'
            },
            {
                path: 'account-operation',
                loadChildren: './account-operation/account-operation.module#CoaAccountOperationModule'
            },
            {
                path: 'data-change',
                loadChildren: './data-change/data-change.module#CoaDataChangeModule'
            },
            {
                path: 'informative-operation',
                loadChildren: './informative-operation/informative-operation.module#CoaInformativeOperationModule'
            },
            {
                path: 'balance-calculation',
                loadChildren: './balance-calculation/balance-calculation.module#CoaBalanceCalculationModule'
            },
            {
                path: 'operator',
                loadChildren: './operator/operator.module#CoaOperatorModule'
            },
            {
                path: 'account-transction',
                loadChildren: './account-transction/account-transction.module#CoaAccountTransctionModule'
            },
            {
                path: 'automatic-operation',
                loadChildren: './automatic-operation/automatic-operation.module#CoaAutomaticOperationModule'
            },
            {
                path: 'financial-account',
                loadChildren: './financial-account/financial-account.module#CoaFinancialAccountModule'
            },
            {
                path: 'person',
                loadChildren: './person/person.module#CoaPersonModule'
            },
            {
                path: 'user-account',
                loadChildren: './user-account/user-account.module#CoaUserAccountModule'
            },
            {
                path: 'vehicle-account',
                loadChildren: './vehicle-account/vehicle-account.module#CoaVehicleAccountModule'
            },
            {
                path: 'vehicle',
                loadChildren: './vehicle/vehicle.module#CoaVehicleModule'
            },
            {
                path: 'media',
                loadChildren: './media/media.module#CoaMediaModule'
            },
            {
                path: 'vehicle-class',
                loadChildren: './vehicle-class/vehicle-class.module#CoaVehicleClassModule'
            },
            {
                path: 'billing-tariff',
                loadChildren: './billing-tariff/billing-tariff.module#CoaBillingTariffModule'
            },
            {
                path: 'billing-location',
                loadChildren: './billing-location/billing-location.module#CoaBillingLocationModule'
            },
            {
                path: 'passage',
                loadChildren: './passage/passage.module#CoaPassageModule'
            },
            {
                path: 'account-operation',
                loadChildren: './account-operation/account-operation.module#CoaAccountOperationModule'
            },
            {
                path: 'data-change',
                loadChildren: './data-change/data-change.module#CoaDataChangeModule'
            },
            {
                path: 'informative-operation',
                loadChildren: './informative-operation/informative-operation.module#CoaInformativeOperationModule'
            },
            {
                path: 'balance-calculation',
                loadChildren: './balance-calculation/balance-calculation.module#CoaBalanceCalculationModule'
            },
            {
                path: 'operator',
                loadChildren: './operator/operator.module#CoaOperatorModule'
            },
            {
                path: 'account-transction',
                loadChildren: './account-transction/account-transction.module#CoaAccountTransctionModule'
            },
            {
                path: 'automatic-operation',
                loadChildren: './automatic-operation/automatic-operation.module#CoaAutomaticOperationModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoaEntityModule {}
