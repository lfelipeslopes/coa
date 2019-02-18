/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { BillingTariffComponent } from 'app/entities/billing-tariff/billing-tariff.component';
import { BillingTariffService } from 'app/entities/billing-tariff/billing-tariff.service';
import { BillingTariff } from 'app/shared/model/billing-tariff.model';

describe('Component Tests', () => {
    describe('BillingTariff Management Component', () => {
        let comp: BillingTariffComponent;
        let fixture: ComponentFixture<BillingTariffComponent>;
        let service: BillingTariffService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BillingTariffComponent],
                providers: []
            })
                .overrideTemplate(BillingTariffComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillingTariffComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingTariffService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BillingTariff(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.billingTariffs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
