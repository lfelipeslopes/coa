/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CoaTestModule } from '../../../test.module';
import { DataChangeDeleteDialogComponent } from 'app/entities/data-change/data-change-delete-dialog.component';
import { DataChangeService } from 'app/entities/data-change/data-change.service';

describe('Component Tests', () => {
    describe('DataChange Management Delete Component', () => {
        let comp: DataChangeDeleteDialogComponent;
        let fixture: ComponentFixture<DataChangeDeleteDialogComponent>;
        let service: DataChangeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [DataChangeDeleteDialogComponent]
            })
                .overrideTemplate(DataChangeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataChangeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataChangeService);
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
