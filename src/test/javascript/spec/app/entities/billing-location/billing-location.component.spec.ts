/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { BillingLocationComponent } from 'app/entities/billing-location/billing-location.component';
import { BillingLocationService } from 'app/entities/billing-location/billing-location.service';
import { BillingLocation } from 'app/shared/model/billing-location.model';

describe('Component Tests', () => {
    describe('BillingLocation Management Component', () => {
        let comp: BillingLocationComponent;
        let fixture: ComponentFixture<BillingLocationComponent>;
        let service: BillingLocationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BillingLocationComponent],
                providers: []
            })
                .overrideTemplate(BillingLocationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BillingLocationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingLocationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BillingLocation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.billingLocations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
