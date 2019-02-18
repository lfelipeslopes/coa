/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { AutomaticOperationDetailComponent } from 'app/entities/automatic-operation/automatic-operation-detail.component';
import { AutomaticOperation } from 'app/shared/model/automatic-operation.model';

describe('Component Tests', () => {
    describe('AutomaticOperation Management Detail Component', () => {
        let comp: AutomaticOperationDetailComponent;
        let fixture: ComponentFixture<AutomaticOperationDetailComponent>;
        const route = ({ data: of({ automaticOperation: new AutomaticOperation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [AutomaticOperationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AutomaticOperationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AutomaticOperationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.automaticOperation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
