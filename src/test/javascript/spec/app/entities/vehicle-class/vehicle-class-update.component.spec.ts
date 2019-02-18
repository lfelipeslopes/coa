/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { VehicleClassUpdateComponent } from 'app/entities/vehicle-class/vehicle-class-update.component';
import { VehicleClassService } from 'app/entities/vehicle-class/vehicle-class.service';
import { VehicleClass } from 'app/shared/model/vehicle-class.model';

describe('Component Tests', () => {
    describe('VehicleClass Management Update Component', () => {
        let comp: VehicleClassUpdateComponent;
        let fixture: ComponentFixture<VehicleClassUpdateComponent>;
        let service: VehicleClassService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [VehicleClassUpdateComponent]
            })
                .overrideTemplate(VehicleClassUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VehicleClassUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehicleClassService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VehicleClass(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vehicleClass = entity;
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
                    const entity = new VehicleClass();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vehicleClass = entity;
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
