/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { OperatorDetailComponent } from 'app/entities/operator/operator-detail.component';
import { Operator } from 'app/shared/model/operator.model';

describe('Component Tests', () => {
    describe('Operator Management Detail Component', () => {
        let comp: OperatorDetailComponent;
        let fixture: ComponentFixture<OperatorDetailComponent>;
        const route = ({ data: of({ operator: new Operator(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [OperatorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OperatorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OperatorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.operator).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
