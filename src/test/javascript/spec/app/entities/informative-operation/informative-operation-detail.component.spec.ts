/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { InformativeOperationDetailComponent } from 'app/entities/informative-operation/informative-operation-detail.component';
import { InformativeOperation } from 'app/shared/model/informative-operation.model';

describe('Component Tests', () => {
    describe('InformativeOperation Management Detail Component', () => {
        let comp: InformativeOperationDetailComponent;
        let fixture: ComponentFixture<InformativeOperationDetailComponent>;
        const route = ({ data: of({ informativeOperation: new InformativeOperation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [InformativeOperationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InformativeOperationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InformativeOperationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.informativeOperation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
