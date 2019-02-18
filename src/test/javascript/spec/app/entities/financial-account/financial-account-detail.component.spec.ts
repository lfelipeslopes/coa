/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { FinancialAccountDetailComponent } from 'app/entities/financial-account/financial-account-detail.component';
import { FinancialAccount } from 'app/shared/model/financial-account.model';

describe('Component Tests', () => {
    describe('FinancialAccount Management Detail Component', () => {
        let comp: FinancialAccountDetailComponent;
        let fixture: ComponentFixture<FinancialAccountDetailComponent>;
        const route = ({ data: of({ financialAccount: new FinancialAccount(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [FinancialAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FinancialAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FinancialAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.financialAccount).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
