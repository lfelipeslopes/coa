/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { AccountTransctionComponent } from 'app/entities/account-transction/account-transction.component';
import { AccountTransctionService } from 'app/entities/account-transction/account-transction.service';
import { AccountTransction } from 'app/shared/model/account-transction.model';

describe('Component Tests', () => {
    describe('AccountTransction Management Component', () => {
        let comp: AccountTransctionComponent;
        let fixture: ComponentFixture<AccountTransctionComponent>;
        let service: AccountTransctionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [AccountTransctionComponent],
                providers: []
            })
                .overrideTemplate(AccountTransctionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AccountTransctionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AccountTransctionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AccountTransction(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.accountTransctions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
