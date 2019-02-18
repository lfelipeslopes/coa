/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { BillingTariffUpdateComponent } from 'app/entities/billing-tariff/billing-tariff-update.component';
import { BillingTariffService } from 'app/entities/billing-tariff/billing-tariff.service';
import { BillingTariff } from 'app/shared/model/billing-tariff.model';

describe('Component Tests', () => {
    describe('BillingTariff Management Update Component', () => {
        let comp: BillingTariffUpdateComponent;
        let fixture: ComponentFixture<BillingTariffUpdateComponent>;
        let service: BillingTariffService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BillingTariffUpdateComponent]
            })
                .overrideTemplate(BillingTariffUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillingTariffUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingTariffService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BillingTariff(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.billingTariff = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BillingTariff();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.billingTariff = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
