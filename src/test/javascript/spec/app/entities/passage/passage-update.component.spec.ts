/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { PassageUpdateComponent } from 'app/entities/passage/passage-update.component';
import { PassageService } from 'app/entities/passage/passage.service';
import { Passage } from 'app/shared/model/passage.model';

describe('Component Tests', () => {
    describe('Passage Management Update Component', () => {
        let comp: PassageUpdateComponent;
        let fixture: ComponentFixture<PassageUpdateComponent>;
        let service: PassageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [PassageUpdateComponent]
            })
                .overrideTemplate(PassageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PassageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PassageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Passage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.passage = entity;
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
                    const entity = new Passage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.passage = entity;
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
