/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { DataChangeDetailComponent } from 'app/entities/data-change/data-change-detail.component';
import { DataChange } from 'app/shared/model/data-change.model';

describe('Component Tests', () => {
    describe('DataChange Management Detail Component', () => {
        let comp: DataChangeDetailComponent;
        let fixture: ComponentFixture<DataChangeDetailComponent>;
        const route = ({ data: of({ dataChange: new DataChange(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [DataChangeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DataChangeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DataChangeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dataChange).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
