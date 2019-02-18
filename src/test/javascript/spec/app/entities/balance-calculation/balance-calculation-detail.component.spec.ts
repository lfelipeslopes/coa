/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { BalanceCalculationDetailComponent } from 'app/entities/balance-calculation/balance-calculation-detail.component';
import { BalanceCalculation } from 'app/shared/model/balance-calculation.model';

describe('Component Tests', () => {
    describe('BalanceCalculation Management Detail Component', () => {
        let comp: BalanceCalculationDetailComponent;
        let fixture: ComponentFixture<BalanceCalculationDetailComponent>;
        const route = ({ data: of({ balanceCalculation: new BalanceCalculation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BalanceCalculationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BalanceCalculationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BalanceCalculationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.balanceCalculation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
