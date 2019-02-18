/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { AutomaticOperationUpdateComponent } from 'app/entities/automatic-operation/automatic-operation-update.component';
import { AutomaticOperationService } from 'app/entities/automatic-operation/automatic-operation.service';
import { AutomaticOperation } from 'app/shared/model/automatic-operation.model';

describe('Component Tests', () => {
    describe('AutomaticOperation Management Update Component', () => {
        let comp: AutomaticOperationUpdateComponent;
        let fixture: ComponentFixture<AutomaticOperationUpdateComponent>;
        let service: AutomaticOperationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [AutomaticOperationUpdateComponent]
            })
                .overrideTemplate(AutomaticOperationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AutomaticOperationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AutomaticOperationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AutomaticOperation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.automaticOperation = entity;
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
                    const entity = new AutomaticOperation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.automaticOperation = entity;
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
