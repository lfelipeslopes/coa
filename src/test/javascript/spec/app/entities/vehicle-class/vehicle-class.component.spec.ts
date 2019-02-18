/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { VehicleClassComponent } from 'app/entities/vehicle-class/vehicle-class.component';
import { VehicleClassService } from 'app/entities/vehicle-class/vehicle-class.service';
import { VehicleClass } from 'app/shared/model/vehicle-class.model';

describe('Component Tests', () => {
    describe('VehicleClass Management Component', () => {
        let comp: VehicleClassComponent;
        let fixture: ComponentFixture<VehicleClassComponent>;
        let service: VehicleClassService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [VehicleClassComponent],
                providers: []
            })
                .overrideTemplate(VehicleClassComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VehicleClassComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehicleClassService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new VehicleClass(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.vehicleClasses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
