/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CoaTestModule } from '../../../test.module';
import { PassageComponent } from 'app/entities/passage/passage.component';
import { PassageService } from 'app/entities/passage/passage.service';
import { Passage } from 'app/shared/model/passage.model';

describe('Component Tests', () => {
    describe('Passage Management Component', () => {
        let comp: PassageComponent;
        let fixture: ComponentFixture<PassageComponent>;
        let service: PassageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [PassageComponent],
                providers: []
            })
                .overrideTemplate(PassageComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PassageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PassageService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Passage(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.passages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
