/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { AccountOperationDetailComponent } from 'app/entities/account-operation/account-operation-detail.component';
import { AccountOperation } from 'app/shared/model/account-operation.model';

describe('Component Tests', () => {
    describe('AccountOperation Management Detail Component', () => {
        let comp: AccountOperationDetailComponent;
        let fixture: ComponentFixture<AccountOperationDetailComponent>;
        const route = ({ data: of({ accountOperation: new AccountOperation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [AccountOperationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AccountOperationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccountOperationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.accountOperation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
