/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { VehicleAccountComponent } from 'app/entities/vehicle-account/vehicle-account.component';
import { VehicleAccountService } from 'app/entities/vehicle-account/vehicle-account.service';
import { VehicleAccount } from 'app/shared/model/vehicle-account.model';

describe('Component Tests', () => {
    describe('VehicleAccount Management Component', () => {
        let comp: VehicleAccountComponent;
        let fixture: ComponentFixture<VehicleAccountComponent>;
        let service: VehicleAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [VehicleAccountComponent],
                providers: []
            })
                .overrideTemplate(VehicleAccountComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VehicleAccountComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehicleAccountService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VehicleAccount(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.vehicleAccounts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
