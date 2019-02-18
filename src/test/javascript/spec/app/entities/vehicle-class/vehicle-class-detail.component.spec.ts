/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { VehicleClassDetailComponent } from 'app/entities/vehicle-class/vehicle-class-detail.component';
import { VehicleClass } from 'app/shared/model/vehicle-class.model';

describe('Component Tests', () => {
    describe('VehicleClass Management Detail Component', () => {
        let comp: VehicleClassDetailComponent;
        let fixture: ComponentFixture<VehicleClassDetailComponent>;
        const route = ({ data: of({ vehicleClass: new VehicleClass(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [VehicleClassDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(VehicleClassDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VehicleClassDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.vehicleClass).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
