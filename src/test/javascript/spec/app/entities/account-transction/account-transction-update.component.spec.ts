/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { AccountTransctionUpdateComponent } from 'app/entities/account-transction/account-transction-update.component';
import { AccountTransctionService } from 'app/entities/account-transction/account-transction.service';
import { AccountTransction } from 'app/shared/model/account-transction.model';

describe('Component Tests', () => {
    describe('AccountTransction Management Update Component', () => {
        let comp: AccountTransctionUpdateComponent;
        let fixture: ComponentFixture<AccountTransctionUpdateComponent>;
        let service: AccountTransctionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [AccountTransctionUpdateComponent]
            })
                .overrideTemplate(AccountTransctionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccountTransctionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountTransctionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AccountTransction(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.accountTransction = entity;
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
                    const entity = new AccountTransction();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.accountTransction = entity;
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
