/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { DataChangeComponent } from 'app/entities/data-change/data-change.component';
import { DataChangeService } from 'app/entities/data-change/data-change.service';
import { DataChange } from 'app/shared/model/data-change.model';

describe('Component Tests', () => {
    describe('DataChange Management Component', () => {
        let comp: DataChangeComponent;
        let fixture: ComponentFixture<DataChangeComponent>;
        let service: DataChangeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [DataChangeComponent],
                providers: []
            })
                .overrideTemplate(DataChangeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataChangeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataChangeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DataChange(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dataChanges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
