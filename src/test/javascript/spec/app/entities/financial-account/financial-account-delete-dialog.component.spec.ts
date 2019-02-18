/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CoaTestModule } from '../../../test.module';
import { FinancialAccountDeleteDialogComponent } from 'app/entities/financial-account/financial-account-delete-dialog.component';
import { FinancialAccountService } from 'app/entities/financial-account/financial-account.service';

describe('Component Tests', () => {
    describe('FinancialAccount Management Delete Component', () => {
        let comp: FinancialAccountDeleteDialogComponent;
        let fixture: ComponentFixture<FinancialAccountDeleteDialogComponent>;
        let service: FinancialAccountService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [FinancialAccountDeleteDialogComponent]
            })
                .overrideTemplate(FinancialAccountDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FinancialAccountDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialAccountService);
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
