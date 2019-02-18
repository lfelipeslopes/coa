/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CoaTestModule } from '../../../test.module';
import { BillingTariffDeleteDialogComponent } from 'app/entities/billing-tariff/billing-tariff-delete-dialog.component';
import { BillingTariffService } from 'app/entities/billing-tariff/billing-tariff.service';

describe('Component Tests', () => {
    describe('BillingTariff Management Delete Component', () => {
        let comp: BillingTariffDeleteDialogComponent;
        let fixture: ComponentFixture<BillingTariffDeleteDialogComponent>;
        let service: BillingTariffService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [BillingTariffDeleteDialogComponent]
            })
                .overrideTemplate(BillingTariffDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BillingTariffDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BillingTariffService);
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
