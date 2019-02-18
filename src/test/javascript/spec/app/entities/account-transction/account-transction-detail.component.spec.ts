/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { AccountTransctionDetailComponent } from 'app/entities/account-transction/account-transction-detail.component';
import { AccountTransction } from 'app/shared/model/account-transction.model';

describe('Component Tests', () => {
    describe('AccountTransction Management Detail Component', () => {
        let comp: AccountTransctionDetailComponent;
        let fixture: ComponentFixture<AccountTransctionDetailComponent>;
        const route = ({ data: of({ accountTransction: new AccountTransction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [AccountTransctionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AccountTransctionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AccountTransctionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.accountTransction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
