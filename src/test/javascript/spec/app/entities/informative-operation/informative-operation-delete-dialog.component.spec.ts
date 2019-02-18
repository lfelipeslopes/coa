/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CoaTestModule } from '../../../test.module';
import { InformativeOperationDeleteDialogComponent } from 'app/entities/informative-operation/informative-operation-delete-dialog.component';
import { InformativeOperationService } from 'app/entities/informative-operation/informative-operation.service';

describe('Component Tests', () => {
    describe('InformativeOperation Management Delete Component', () => {
        let comp: InformativeOperationDeleteDialogComponent;
        let fixture: ComponentFixture<InformativeOperationDeleteDialogComponent>;
        let service: InformativeOperationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [InformativeOperationDeleteDialogComponent]
            })
                .overrideTemplate(InformativeOperationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InformativeOperationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InformativeOperationService);
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
