/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { BalanceCalculationComponent } from 'app/entities/balance-calculation/balance-calculation.component';
import { BalanceCalculationService } from 'app/entities/balance-calculation/balance-calculation.service';
import { BalanceCalculation } from 'app/shared/model/balance-calculation.model';

describe('Component Tests', () => {
    describe('BalanceCalculation Management Component', () => {
        let comp: BalanceCalculationComponent;
        let fixture: ComponentFixture<BalanceCalculationComponent>;
        let service: BalanceCalculationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BalanceCalculationComponent],
                providers: []
            })
                .overrideTemplate(BalanceCalculationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BalanceCalculationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceCalculationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BalanceCalculation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.balanceCalculations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
