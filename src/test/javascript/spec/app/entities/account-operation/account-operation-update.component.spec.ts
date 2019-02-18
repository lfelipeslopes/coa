/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { AccountOperationUpdateComponent } from 'app/entities/account-operation/account-operation-update.component';
import { AccountOperationService } from 'app/entities/account-operation/account-operation.service';
import { AccountOperation } from 'app/shared/model/account-operation.model';

describe('Component Tests', () => {
    describe('AccountOperation Management Update Component', () => {
        let comp: AccountOperationUpdateComponent;
        let fixture: ComponentFixture<AccountOperationUpdateComponent>;
        let service: AccountOperationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [AccountOperationUpdateComponent]
            })
                .overrideTemplate(AccountOperationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccountOperationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountOperationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AccountOperation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.accountOperation = entity;
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
                    const entity = new AccountOperation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.accountOperation = entity;
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
