/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { VehicleAccountUpdateComponent } from 'app/entities/vehicle-account/vehicle-account-update.component';
import { VehicleAccountService } from 'app/entities/vehicle-account/vehicle-account.service';
import { VehicleAccount } from 'app/shared/model/vehicle-account.model';

describe('Component Tests', () => {
    describe('VehicleAccount Management Update Component', () => {
        let comp: VehicleAccountUpdateComponent;
        let fixture: ComponentFixture<VehicleAccountUpdateComponent>;
        let service: VehicleAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [VehicleAccountUpdateComponent]
            })
                .overrideTemplate(VehicleAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(VehicleAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehicleAccountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new VehicleAccount(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vehicleAccount = entity;
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
                    const entity = new VehicleAccount();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.vehicleAccount = entity;
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
