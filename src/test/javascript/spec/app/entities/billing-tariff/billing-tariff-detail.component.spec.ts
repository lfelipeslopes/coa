/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { BillingTariffDetailComponent } from 'app/entities/billing-tariff/billing-tariff-detail.component';
import { BillingTariff } from 'app/shared/model/billing-tariff.model';

describe('Component Tests', () => {
    describe('BillingTariff Management Detail Component', () => {
        let comp: BillingTariffDetailComponent;
        let fixture: ComponentFixture<BillingTariffDetailComponent>;
        const route = ({ data: of({ billingTariff: new BillingTariff(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BillingTariffDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BillingTariffDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BillingTariffDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.billingTariff).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
