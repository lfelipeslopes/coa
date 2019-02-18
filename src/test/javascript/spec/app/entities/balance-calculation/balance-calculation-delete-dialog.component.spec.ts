/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CoaTestModule } from '../../../test.module';
import { BalanceCalculationDeleteDialogComponent } from 'app/entities/balance-calculation/balance-calculation-delete-dialog.component';
import { BalanceCalculationService } from 'app/entities/balance-calculation/balance-calculation.service';

describe('Component Tests', () => {
    describe('BalanceCalculation Management Delete Component', () => {
        let comp: BalanceCalculationDeleteDialogComponent;
        let fixture: ComponentFixture<BalanceCalculationDeleteDialogComponent>;
        let service: BalanceCalculationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BalanceCalculationDeleteDialogComponent]
            })
                .overrideTemplate(BalanceCalculationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BalanceCalculationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceCalculationService);
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
