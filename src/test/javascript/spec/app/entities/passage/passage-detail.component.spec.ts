/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { PassageDetailComponent } from 'app/entities/passage/passage-detail.component';
import { Passage } from 'app/shared/model/passage.model';

describe('Component Tests', () => {
    describe('Passage Management Detail Component', () => {
        let comp: PassageDetailComponent;
        let fixture: ComponentFixture<PassageDetailComponent>;
        const route = ({ data: of({ passage: new Passage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [PassageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PassageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PassageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.passage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
