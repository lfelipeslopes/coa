/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { BalanceCalculationUpdateComponent } from 'app/entities/balance-calculation/balance-calculation-update.component';
import { BalanceCalculationService } from 'app/entities/balance-calculation/balance-calculation.service';
import { BalanceCalculation } from 'app/shared/model/balance-calculation.model';

describe('Component Tests', () => {
    describe('BalanceCalculation Management Update Component', () => {
        let comp: BalanceCalculationUpdateComponent;
        let fixture: ComponentFixture<BalanceCalculationUpdateComponent>;
        let service: BalanceCalculationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BalanceCalculationUpdateComponent]
            })
                .overrideTemplate(BalanceCalculationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BalanceCalculationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceCalculationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BalanceCalculation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.balanceCalculation = entity;
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
                    const entity = new BalanceCalculation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.balanceCalculation = entity;
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
