/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CoaTestModule } from '../../../test.module';
import { VehicleClassDeleteDialogComponent } from 'app/entities/vehicle-class/vehicle-class-delete-dialog.component';
import { VehicleClassService } from 'app/entities/vehicle-class/vehicle-class.service';

describe('Component Tests', () => {
    describe('VehicleClass Management Delete Component', () => {
        let comp: VehicleClassDeleteDialogComponent;
        let fixture: ComponentFixture<VehicleClassDeleteDialogComponent>;
        let service: VehicleClassService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [VehicleClassDeleteDialogComponent]
            })
                .overrideTemplate(VehicleClassDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VehicleClassDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VehicleClassService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
