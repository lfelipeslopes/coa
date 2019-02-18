/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { DataChangeUpdateComponent } from 'app/entities/data-change/data-change-update.component';
import { DataChangeService } from 'app/entities/data-change/data-change.service';
import { DataChange } from 'app/shared/model/data-change.model';

describe('Component Tests', () => {
    describe('DataChange Management Update Component', () => {
        let comp: DataChangeUpdateComponent;
        let fixture: ComponentFixture<DataChangeUpdateComponent>;
        let service: DataChangeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [DataChangeUpdateComponent]
            })
                .overrideTemplate(DataChangeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataChangeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataChangeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataChange(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataChange = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataChange();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataChange = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
