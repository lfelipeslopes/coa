/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { AutomaticOperationComponent } from 'app/entities/automatic-operation/automatic-operation.component';
import { AutomaticOperationService } from 'app/entities/automatic-operation/automatic-operation.service';
import { AutomaticOperation } from 'app/shared/model/automatic-operation.model';

describe('Component Tests', () => {
    describe('AutomaticOperation Management Component', () => {
        let comp: AutomaticOperationComponent;
        let fixture: ComponentFixture<AutomaticOperationComponent>;
        let service: AutomaticOperationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [AutomaticOperationComponent],
                providers: []
            })
                .overrideTemplate(AutomaticOperationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AutomaticOperationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AutomaticOperationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AutomaticOperation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.automaticOperations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
