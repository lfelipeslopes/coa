/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { InformativeOperationComponent } from 'app/entities/informative-operation/informative-operation.component';
import { InformativeOperationService } from 'app/entities/informative-operation/informative-operation.service';
import { InformativeOperation } from 'app/shared/model/informative-operation.model';

describe('Component Tests', () => {
    describe('InformativeOperation Management Component', () => {
        let comp: InformativeOperationComponent;
        let fixture: ComponentFixture<InformativeOperationComponent>;
        let service: InformativeOperationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [InformativeOperationComponent],
                providers: []
            })
                .overrideTemplate(InformativeOperationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InformativeOperationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InformativeOperationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new InformativeOperation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.informativeOperations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
